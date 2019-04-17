package uk.jixun.project.Simulator;


import java.util.List;
import java.util.Stack;

public class MockExecutionContext extends AbstractExecutionContext {
  private Stack<Integer> stack;

  public MockExecutionContext() {
    stack = new Stack<>();
    for (int i = 0; i < 10; i++) {
      stack.add(0);
    }
  }

  public void setStack(Stack<Integer> stack) {
    this.stack = stack;
  }

  @Override
  public void setStack(int offset, int value) {
    stack.set(offset, value);
  }

  @Override
  public int getStack(int offset) {
    return 0;
  }

  @Override
  public int resolveStack(int offset, int exeId) {
    return 0;
  }

  @Override
  public List<Integer> resolveStack(int offset, int exeId, int size) {
    return null;
  }

  @Override
  public void push(int value) {

  }

  @Override
  public void push(List<Integer> value) {

  }

  @Override
  public int pop() {
    return 0;
  }

  @Override
  public int read(int address) {
    return 0;
  }

  @Override
  public void write(int address, int value) {

  }
}
