package uk.jixun.project.Simulator.DispatchRecord;

import uk.jixun.project.Helper.LazyCache;
import uk.jixun.project.Helper.LazyCacheResolver;
import uk.jixun.project.OpCode.IExecutable;
import uk.jixun.project.OpCode.SmRegStatus;
import uk.jixun.project.Register.SmRegister;
import uk.jixun.project.Util.FifoList;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DependencyResolver implements IDependencyResolver {
  private final AtomicBoolean stackResolved = new AtomicBoolean(false);
  private final AtomicBoolean ramResolved = new AtomicBoolean(false);
  private final AtomicBoolean regResolved = new AtomicBoolean(false);
  private final AtomicBoolean flagResolved = new AtomicBoolean(false);
  private final SmRegister regAccess;
  private final IDispatchRecord mainRecord;
  private int paramSkips = 0;
  private FifoList<IDispatchRecord> dependencies = FifoList.create();
  private int stackRequired;
  private SmRegStatus regStatus;

  DependencyResolver(IDispatchRecord record) {
    mainRecord = record;
    IExecutable exe = record.getExecutable();
    stackRequired = exe.getConsume();
    stackResolved.set(stackRequired == 0);

    // Only resolve ram if they read/write to it.
    boolean rwRam = record.readOrWrite();
    ramResolved.set(!rwRam);

    // Only resolve register dependency if they read/write to it.
    regAccess = exe.getRegisterAccess();
    regResolved.set(regAccess == SmRegister.NONE);
    regStatus = exe.getRegisterStatus();

    boolean readFlag = exe.isReadFlag();
    boolean writeFlag = exe.isWriteFlag();
    // noinspection PointlessBooleanExpression
    flagResolved.set(readFlag == false && writeFlag == false);
  }

  @Override
  public boolean resolveDependency(IDispatchRecord record) {
    assert record.getExecutionId() < mainRecord.getExecutionId();

    resolveStack(record);
    resolveRam(record);
    resolveReg(record);
    resolveFlag(record);

    return allResolved();
  }

  @Override
  public List<IDispatchRecord> getDependencies() {
    return dependencies;
  }

  private void resolveStack(IDispatchRecord record) {
    synchronized (stackResolved) {
      if (stackResolved.get()) {
        return;
      }

      IExecutable exe = record.getExecutable();
      int consumes = exe.getConsume();
      int produces = exe.getProduce();

      // Skip if required.
      int skipThisTime = Math.min(paramSkips, produces);
      if (paramSkips > 0) {
        paramSkips -= skipThisTime;
        produces -= skipThisTime;
      }

      // If previous instruction produces any useful result, count it.
      if (produces > 0) {
        int cost = Math.min(stackRequired, produces);
        stackRequired -= cost;
        dependencies.pushUnique(record);
      }

      paramSkips += consumes;
      stackResolved.set(stackRequired == 0);
    }
  }

  private void resolveRam(IDispatchRecord record) {
    synchronized (ramResolved) {
      if (ramResolved.get()) {
        return;
      }

      int address = 0;
      try {
        address = mainRecord.getExecutable().resolveRamAddress(mainRecord.getContext());
      } catch (Exception ex) {
        return;
      }

      IExecutable exe = record.getExecutable();

      boolean reads = mainRecord.getExecutable().readRam();
      boolean writes = mainRecord.getExecutable().writeRam();

      boolean exeReads = exe.readRam();
      boolean exeWrites = exe.writeRam();

      int exeAddress = -1;

      // If the record checking against does reference to the ram
      if (exeReads || exeWrites) {
        // Check if we can resolve the address
        try {
          exeAddress = exe.resolveRamAddress(mainRecord.getContext());
        } catch (Exception ex) {
          // Can't resolve it yet.
          return;
        }
      }

      if (exeAddress == address) {
        boolean depends = exeWrites;
        depends = depends || (writes && exeReads);

        if (depends) {
          ramResolved.set(true);
          dependencies.pushUnique(record);
        }
      }
    }
  }

  private void resolveReg(IDispatchRecord record) {
    synchronized (regResolved) {
      if (regResolved.get()) {
        return;
      }

      // Check if we have request to the same register.
      if (regAccess == record.getExecutable().getRegisterAccess()) {
        // The only case where we don't depend previous instruction was both read the same place.
        SmRegStatus exeRegStatus = record.getExecutable().getRegisterStatus();
        boolean depends = exeRegStatus == SmRegStatus.WRITE;
        // If the instruction writes and we read the same register, we need to wait for it to sync.
        depends = depends || (
          regStatus == SmRegStatus.WRITE
            && exeRegStatus == SmRegStatus.READ
        );
        if (depends) {
          regResolved.set(true);
          dependencies.pushUnique(record);
        }
      }
    }
  }

  private void resolveFlag(IDispatchRecord record) {
    synchronized (flagResolved) {
      if (flagResolved.get()) {
        return;
      }

      if (record.getExecutable().isWriteFlag()) {
        flagResolved.set(true);
        dependencies.pushUnique(record);
      }
    }
  }

  @Override
  public boolean allResolved() {
    synchronized (stackResolved) {
      synchronized (ramResolved) {
        synchronized (regResolved) {
          synchronized (flagResolved) {
            return
              stackResolved.get() &&
              ramResolved.get() &&
              regResolved.get() &&
              flagResolved.get();
          }
        }
      }
    }
  }
}
