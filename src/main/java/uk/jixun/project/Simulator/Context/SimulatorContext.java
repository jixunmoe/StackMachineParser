package uk.jixun.project.Simulator.Context;

import uk.jixun.project.OpCode.IExecutable;
import uk.jixun.project.Simulator.DispatchRecord.IDispatchRecord;

import java.util.*;

public class SimulatorContext extends AbstractExecutionContext {
  private Stack<Integer> stack = new Stack<>();

  @Override
  public void setStack(int offset, int value) {
    stack.set(stack.size() - 1 - offset, value);
  }

  @Override
  public int getStack(int offset) {
    return stack.get(stack.size() - 1 - offset);
  }

  @Override
  public List<Integer> resolveStack(int offset, int exeId, int size) {
    LinkedList<Integer> stack = new LinkedList<>();

    int paramSkips = offset;
    int nextId = exeId;

    IDispatchRecord resolveRecord = getHistory().getRecordAt(nextId);
    assert resolveRecord != null;

    while (size > 0) {
      nextId--;
      IDispatchRecord record = getHistory().getRecordAt(nextId);
      if (record == null) {
        // No more items on the chain, break.
        break;
      }

      IExecutable opcode = record.getExecutable();
      int consumes = opcode.getConsume();
      int produces = opcode.getProduce();

      // Skip if required.
      int skipThisTime = Math.min(paramSkips, produces);
      if (paramSkips > 0) {
        paramSkips -= skipThisTime;
        produces -= skipThisTime;
      }

      // If previous instruction produces any result, use it.
      if (produces > 0) {
        if (!record.executed()) {
          logger.warning(String.format(
            "!! resolve stack for \n %s \n requires \n %s \n, but was not executed before.",
            resolveRecord.toString(),
            record.toString()
          ));
          assert false;
        }
        List<Integer> prevStack = record.executeAndGetStack();
        if (prevStack == null) {
          logger.warning(
            "Could not resolve stack: " +
            "execution of instruction " + record.getExecutable().toString() + " failed."
          );
          return Collections.emptyList();
        }

        // It should satisfy "produces" number of params.
        size -= produces;

        int end = prevStack.size() - skipThisTime;
        int start = end - produces;

        stack.addAll(0, prevStack.subList(start, end));
      }

      // Increase the number of items to skip next round.
      paramSkips += consumes;
    }

    if (size > 0) {
      logger.info(String.format("fetch item from the stack (local stack: #%d, required=%d)", this.stack.size(), size));
      int stackSize = this.stack.size();
      stack.addAll(0, this.stack.subList(stackSize - size, stackSize));
    }

    return stack;
  }

  @Override
  public void push(int value) {
    stack.push(value);
  }

  @Override
  public void push(List<Integer> value) {
    stack.addAll(value);
  }

  @Override
  public int pop() {
    return stack.pop();
  }
}
