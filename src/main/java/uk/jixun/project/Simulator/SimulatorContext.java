package uk.jixun.project.Simulator;

import uk.jixun.project.OpCode.ISmOpCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class SimulatorContext extends AbstractExecutionContext {
  private ArrayList<Integer> memory;
  private Stack<Integer> stack;

  public SimulatorContext() {
    memory = new ArrayList<>(256);
    stack = new Stack<>();
  }

  @Override
  public int read(int address) {
    return memory.get(address);
  }

  @Override
  public void write(int address, int value) {
    memory.set(address, value);
  }

  @Override
  public void setStack(int offset, int value) {
    stack.set(stack.size() - 1 - offset, value);
  }

  @Override
  public int getStack(int offset) {
    return stack.get(stack.size() - 1 - offset);
  }

  @Override
  public int resolveStack(int offset, int exeId) {
    return resolveStack(offset, exeId, 1).get(0);
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
        List<Integer> prevStack = record.getInstructionStack();

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
