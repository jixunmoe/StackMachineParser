package uk.jixun.project.Simulator;

import org.jetbrains.annotations.Nullable;
import uk.jixun.project.Exceptions.LabelNotFoundException;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.OpCode.IExecutable;
import uk.jixun.project.OpCode.SysCall.ISysCall;
import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.Simulator.DispatchRecord.IDispatchRecord;
import uk.jixun.project.Simulator.DispatchRecord.InstructionDispatchRecord;
import uk.jixun.project.Simulator.DispatchRecord.SysCallDispatchRecord;
import uk.jixun.project.Simulator.Context.IExecutionContext;
import uk.jixun.project.Simulator.Context.SimulatorContext;
import uk.jixun.project.SimulatorConfig.ISimulatorConfig;
import uk.jixun.project.Util.FifoList;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SmSimulator implements ISmSimulator {
  private static Logger logger = Logger.getLogger(SmSimulator.class.getName());
  private final SmHistory history;

  private int aluLimit;
  private int ramLimit;
  private int searchDepth;

  /**
   * Number of items pushed to stack *minus* the number of items popped from the stack.
   */
  private int stackBalance = 0;

  private ISmProgram program;
  private AtomicInteger exeId = new AtomicInteger(0);

  /**
   * Instruction held in the "to be processed" queue.
   */
  // private FifoList<IDispatchRecord> queuedInst = new FifoList<>();

  private SimulatorContext context;

  public SmSimulator() {
    // Initialise context.
    context = new SimulatorContext();
    history = new SmHistory();
    context.setHistory(history);
  }

  @Override
  public void setConfig(ISimulatorConfig config) {
    aluLimit = config.getAluCount();
    ramLimit = config.getMemoryPortCount();
    searchDepth = config.getSearchDepth();
  }

  @Override
  public void setProgram(ISmProgram program) {
    this.program = program;
    try {
      context.push((int) program.resolveLabel("Halt"));
      logger.info("halt set as last item.");
    } catch (LabelNotFoundException e) {
      logger.info("can't push Halt as return address.");
    }
    getContext().halt(false);
  }

  @Override
  public IExecutionContext getContext() {
    return context;
  }

  @Override
  public List<IDispatchRecord> dispatch() {
    // Get current executing instruction.
    final IExecutionContext ctx = getContext();

    List<IDispatchRecord> results = new ArrayList<>();

    // Calculate current available ALU and ram port.
    int cycle = ctx.getCurrentCycle();

    // Get the available resource counts
    int aluCount = aluLimit - (int) history
      .filter(record -> record.executesAt(cycle))
      .filter(IDispatchRecord::usesAlu)
      .count();
    int ramCount = ramLimit - (int) history
      .filter(record -> record.executesAt(cycle))
      .filter(IDispatchRecord::readOrWrite)
      .count();

    boolean needSync = history
      .filter(record -> record.executesAt(cycle))
      .anyMatch(IDispatchRecord::needSync);

    needSync = needSync || history.getNotExecuted().anyMatch(IDispatchRecord::needSync);

    if (needSync) {
      logger.finer("skip schedule, need to sync state.");
    } else {
      while (history.filter(r -> !r.executed()).count() < searchDepth) {
        int eip = ctx.getEip();
        // sys call requires sync
        if (program.isSysCall(eip)) {
          break;
        }

        if (eip >= program.getInstructions().size()) {
          logger.warning("Reached eof unexpectedly.");
          ctx.halt();
          return Collections.emptyList();
        }

        ISmInstruction inst = program.getInstruction(eip);
        InstructionDispatchRecord record = new InstructionDispatchRecord();

        record.setInst(inst);
        record.setContext(ctx);
        record.setEip(eip);
        record.setCycleStart(-1);

        // Execution id if the program is executed without any optimisation.
        record.setExecutionId(exeId.getAndIncrement());
        history.add(record);
        logger.info("Add inst: " + inst.toAssembly());

        // EIP read from the record must be the same as the instruction eip
        assert record.getEip() == eip;

        ctx.incEip();
        if (record.needSync()) {
          logger.info("added a record requires sync.");
          break;
        }
      }
    }

    // Only try to dispatch if we still have some resource to use.
    if (aluCount > 0 || ramCount > 0) {
      // Try to dispatch instructions, and remove if they are dispatched.
      List<IDispatchRecord> notExecuted = history.getNotStarted().collect(Collectors.toList());
      for (IDispatchRecord record : notExecuted) {
        // Ensure they are legal in debug build
        assert aluCount >= 0;
        assert ramCount >= 0;

        IExecutable exe = record.getExecutable();
        if (exe.usesAlu() && aluCount == 0) {
          // Not enough ALU available
          continue;
        }

        if ((exe.readRam() || exe.writeRam()) && ramCount == 0) {
          continue;
        }

        List<IDispatchRecord> deps = record.getDependencies(true);
        // Could not resolve.
        if (deps == null) {
          continue;
        }

        // If any dependency not done, don't start it.
        if (deps.size() > 0) {
          if (!deps.stream().allMatch(IDispatchRecord::executed)) {
            continue;
          }
        }

        // Decrease available resource counter.
        if (exe.usesAlu()) aluCount--;
        if (exe.readRam() || exe.writeRam()) ramCount--;

        record.setCycleStart(cycle);
        record.setCycleEnd(cycle + record.getExecutable().getCycleTime() - 1);

        // history.add(record);
        // results.add(record);
      }
    }

    history
      .filter(record -> record.endAtCycle(ctx.getCurrentCycle()))
      .forEach(record -> {
        IExecutable exe = record.getExecutable();
        int produce = exe.getProduce();
        int consume = exe.getConsume();
        stackBalance += produce - consume;
        logger.info(String.format(
          "(stack) +%d -%d ==> %d\n >> %s",
          produce,
          consume,
          stackBalance,
          exe.toString()
        ));
        record.executeAndGetStack();
        results.add(record);
      });

    boolean allDone = history.filter(x -> !x.isFinished()).count() == 0;
    if (allDone && program.isSysCall(getContext().getEip())) {
      SysCallDispatchRecord sysCallRecord = new SysCallDispatchRecord();

      sysCallRecord.setContext(getContext());
      // FIXME: Assume all syscall finish in two cycles
      sysCallRecord.setCycleStart(ctx.getCurrentCycle());
      sysCallRecord.setCycleEnd(ctx.getCurrentCycle() + 1);
      // Get EIP because we are in sync.
      sysCallRecord.setEip(getContext().getEip());
      sysCallRecord.setExecutionId(exeId.getAndIncrement());
      sysCallRecord.setSysCall(program.getSysCall(getContext().getEip()));
      history.add(sysCallRecord);
      // sysCallRecord.executeAndGetStack();
      allDone = false;
    }
    ctx.nextCycle();

      // Balance stack when nothing is queued.
//    if (queuedInst.isEmpty() && allDone && stackBalance != 0) {
//      while (stackBalance < 0) {
//        stackBalance++;
//        ctx.pop();
//      }
//
//      // Sync with stack.
//      IDispatchRecord record = history.getLastRecord();
//      assert record != null;
//
//      List<Integer> subStack = ctx.resolveStack(0, record.getExecutionId(), stackBalance);
//      assert subStack.size() == stackBalance;
//      ctx.push(subStack);
//    }

    return results;
  }

  @Override
  public boolean isHalt() {
    return getContext().isHalt();
  }
}
