package uk.jixun.project.Simulator;

import uk.jixun.project.OpCode.ISmOpCode;

import java.util.*;
import java.util.logging.Logger;

public class SimulatorContext extends AbstractExecutionContext {
  private final static Logger logger = Logger.getLogger(SimulatorContext.class.getName());
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
    int nextId = exeId - 1;

    while (size > 0) {
      IDispatchRecord record = getHistory().getRecordAt(nextId);
      if (record == null) {
        // No more items on the chain, break.
        break;
      }

      ISmOpCode opcode = record.getInstruction().getOpCode();
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
        List<Integer> prevStack = record.executeAndGetStack();
        if (prevStack == null) {
          logger.warning(
            "Could not resolve stack: " +
            "execution of instruction " + record.getInstruction().toAssembly() + " failed."
          );
          return Collections.emptyList();
        }

        // It should satisfy "produces" number of params.
        size -= produces;

        int end = prevStack.size() - skipThisTime;
        int start = end - produces;

        // Insert the stack list to the front
        stack.addAll(0, prevStack.subList(start, end));
      }

      // Increase the number of items to skip next round.
      paramSkips += consumes;
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
