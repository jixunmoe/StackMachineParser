package uk.jixun.project.Simulator;

import com.google.common.base.Throwables;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.Program.ISmProgram;
import uk.jixun.project.SimulatorConfig.ISimulatorConfig;
import uk.jixun.project.Util.FifoList;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SmSimulator implements ISmSimulator, ISmHistory {
  private static Logger logger = Logger.getLogger(SmSimulator.class.getName());

  private int aluLimit;
  private int ramLimit;
  private int searchDepth;

  /**
   * Number of items pushed to stack *minus* the number of items popped from the stack.
   */
  private int stackBalance = 0;

  private ISmProgram program;
  private List<IDispatchRecord> history = new LinkedList<>();
  private AtomicInteger exeId = new AtomicInteger(0);

  /**
   * Instruction held in the "to be processed" queue.
   */
  private FifoList<IDispatchRecord> queuedInst = new FifoList<>();

  private SimulatorContext context;

  public SmSimulator() {
    // Initialise context.
    context = new SimulatorContext();
    context.setHistory(this);
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
    Stream<IDispatchRecord> executeStream = history.stream()
      .filter(record -> record.executesAt(cycle));

    // Get the available resource counts
    int aluCount = aluLimit - (int) executeStream
      .filter(IDispatchRecord::usesAlu)
      .count();
    int ramCount = ramLimit - (int) executeStream
      .filter(IDispatchRecord::readOrWrite)
      .count();

    while (queuedInst.size() < searchDepth) {
      // If the last item is a branch, don't continue.
      if (queuedInst.last().getInstruction().isBranch()) {
        break;
      }

      int eip = ctx.getEip();
      ISmInstruction inst = program.getInstruction(eip);
      stackBalance += inst.getOpCode().getProduce() - inst.getOpCode().getConsume();
      DispatchRecord record = new DispatchRecord();

      record.setInst(inst);
      record.setContext(ctx);

      // Execution id if the program is executed without any optimisation.
      record.setExecutionId(exeId.getAndIncrement());
      queuedInst.push(record);

      // EIP read from the record must be the same as the instruction eip
      assert record.getEip() == eip;

      ctx.incEip();
    }

    // Only try to dispatch if we still have some resource to use.
    if (aluCount > 0 || ramCount > 0) {
      // Try to dispatch instructions, and remove if they are dispatched.
      Iterator<IDispatchRecord> iter = queuedInst.iterator();
      for (int peekIndex = -1; iter.hasNext(); peekIndex++) {
        // Ensure they are legal in debug build
        assert aluCount >= 0;
        assert ramCount >= 0;

        IDispatchRecord record = iter.next();
        ISmInstruction inst = record.getInstruction();
        if (inst.usesAlu() && aluCount > 0) {
          // Not enough ALU available
          continue;
        }

        if (inst.readOrWriteRam() && ramCount > 0) {
          continue;
        }

        // Due to the nature of instruction queue,
        // all inserted elements are only dependent on the following condition:
        // (1) - instructions queued before executing instruction;
        // (2) - instructions not yet finished (dispatched but not complete)
        boolean canFulfill = true;

        // Checking for (1), instructions queued before current instruction.
        for (int i = 0; i < peekIndex; i++) {
          if (inst.depends(queuedInst.get(i).getInstruction())) {
            canFulfill = false;
            break;
          }
        }

        // Can only fulfill if none of executing instructions
        // were depended by current instruction.
        canFulfill = canFulfill && history.stream()
          // Only keep the one executes in this cycle.
          .filter(x -> !x.isFinished(ctx))
          .noneMatch(x -> inst.depends(x.getInstruction()));

        // If the instruction can't be fulfilled, don't schedule it (yet).
        if (!canFulfill) {
          continue;
        }

        // Decrease available resource counter.
        if (inst.usesAlu()) aluCount--;
        if (inst.readOrWriteRam()) ramCount--;

        if (record instanceof DispatchRecord) {
          DispatchRecord r = (DispatchRecord) record;
          r.setCycleStart(cycle);

          // FIXME: Assume each instruction uses 1 cycle.
          r.setCycleEnd(cycle);
        } else {
          System.out.println("record is not an instance of DispatchRecord!");
        }

        // Remove from the queue, and decrease the index to sync index.
        iter.remove();
        peekIndex--;

        history.add(record);
        results.add(record);
      }
    }

    // Balance stack when nothing is queued.
    if (queuedInst.isEmpty() && stackBalance != 0) {
      while (stackBalance < 0) {
        stackBalance++;
        ctx.pop();
      }

      while (stackBalance > 0) {
        stackBalance--;

        // FIXME: Fetch the correct value to push.
        ctx.push(1);
      }
    }

    history.stream()
      .filter(record -> record.endAtCycle(cycle) && !record.executed())
      .forEach(record -> record.executeAndRecord(ctx));

    ctx.nextCycle();

    return results;
  }

  @Override
  public List<IDispatchRecord> getDispatchHistory() {
    return history;
  }

  @Override
  public List<IDispatchRecord> getSortedHistoryBetween(int start, int end) {
    if (start > end) {
      logger.info("getSortedHistoryBetween: start is later then end, swap two around.");
      if (logger.isLoggable(Level.FINER)) {
        logger.fine(Throwables.getStackTraceAsString(new Exception()));
      }
      return getSortedHistoryBetween(end, start);
    }

    return Stream
      .concat(history.stream(), queuedInst.stream())
      .filter(x -> x.getExecutionId() >= start && x.getExecutionId() <= end)
      .sorted(Comparator.comparing(IDispatchRecord::getExecutionId))
      .collect(Collectors.toList());
  }

  @Override
  public List<IDispatchRecord> getHistoryBetween(IDispatchRecord a, IDispatchRecord b) {
    if (a == null || b == null) {
      logger.log(Level.WARNING, "param for getHistoryBetween() contains null.");
      if (logger.isLoggable(Level.FINER)) {
        logger.fine(Throwables.getStackTraceAsString(new Exception()));
      }
      return null;
    }

    int start = a.getExecutionId();
    int end = b.getExecutionId();

    return getSortedHistoryBetween(start, end);
  }

  private IDispatchRecord fromInstruction(ISmInstruction inst) {
    return Stream
      .concat(history.stream(), queuedInst.stream())
      .filter(x -> x.getInstruction() == inst)
      .findFirst()
      .orElse(null);
  }

  @Override
  public List<IDispatchRecord> getHistoryBetween(ISmInstruction a, ISmInstruction b) {
    return getHistoryBetween(fromInstruction(a), fromInstruction(b));
  }

  @Override
  public IDispatchRecord getRecordAt(int exeId) {
    return history
      .stream()
      .filter(x -> x.getExecutionId() == exeId)
      .findFirst()
      .orElse(null);
  }
}
