package uk.jixun.project.Program.Simulator;

public interface IExecutionContext extends IMemoryModel {
  // Simulator should have a stack
  void setStack(int offset, int value);
  int getStack(int offset);
  void push(int value);
  int pop();

  // Simulator should know the address is executing
  int getEip();
  void setEip(int eip);
  void incEip();

  // Simulator should know how many cycles has passed.
  int getCycles();
  void nextCycle();
  void addCycles(int cycles);
}
