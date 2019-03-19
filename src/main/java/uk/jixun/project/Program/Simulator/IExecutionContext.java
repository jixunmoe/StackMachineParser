package uk.jixun.project.Program.Simulator;

public interface IExecutionContext {
  // Simulator needs to have ram and stack.
  int read(int address);
  void write(int address, int value);

  // Simulator should have a stack
  void setStack(int offset, int value);
  int getStack(int offset);
  void push(int value);
  int pop();

  // Simulator should know the address is executing
  int getEip();
  void setEip(int eip);

  // Simulator should know how many cycles has passed.
  int getCycles();
  void addCycles(int cycles);
}
