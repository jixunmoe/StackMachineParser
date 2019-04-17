package uk.jixun.project.Simulator;

import java.util.List;

public interface IExecutionContext extends IMemoryModel {
  /**
   * Set stack value.
   *
   * @param offset Offset from the stack, 0: top of stack, 1: second of stack, etc...
   * @param value  Stack item value.
   * @apiNote Stack may not be sync!
   * @deprecated Do not use this method.
   */
  void setStack(int offset, int value);

  /**
   * Get values from the stack. 0: top of stack, 1: second of stack, etc...
   *
   * @param offset Offset from the stack.
   * @return Stack value
   * @apiNote Stack may not be sync! Use {@link #resolveStack(int, int)} if you need the stack when executing an instruction.
   * @deprecated Do not use this method.
   */
  int getStack(int offset);

  /**
   * Resolve Stack Value at given execution id
   *
   * @param offset Offset. 0: top of stack, 1: second of stack, etc ...
   * @param exeId  Execution ID of the instruction specified.
   * @return Resolved stack value.
   */
  int resolveStack(int offset, int exeId);

  /**
   * Resolve Stack Value at given execution id
   *
   * @param offset Offset. 0: top of stack, 1: second of stack, etc ...
   * @param exeId  Execution ID of the instruction specified.
   * @param size   Number of items to resolve.
   * @return Resolved stack slice.
   */
  List<Integer> resolveStack(int offset, int exeId, int size);

  /**
   * Push an item to the stack.
   *
   * @param value Value to push to the stack.
   * @apiNote This method may not sync with the system.
   */
  void push(int value);

  /**
   * Push an item to the stack.
   *
   * @param value Values to push to the stack (end of the stack).
   * @apiNote This method may not sync with the system.
   */
  void push(List<Integer> value);

  /**
   * Pop a value from the stack (removing the top-of-stack value).
   *
   * @return Top of stack value.
   * @apiNote This method may not sync with the system.
   */
  int pop();

  /**
   * Get current execution pointer.
   *
   * @return Execution pointer.
   */
  int getEip();

  /**
   * Set a new execution pointer.
   *
   * @param eip the new execution pointer.
   */
  void setEip(int eip);

  /**
   * Increase execution pointer by 1.
   */
  void incEip();

  /**
   * @return Current execution cycle.
   * @implNote Simulator should know how many cycles has passed.
   */
  int getCurrentCycle();

  /**
   * Move to the next cycle.
   * Same as {@link #addCycles(int)}.
   */
  void nextCycle();

  /**
   * Add some cycles to cycle counter.
   *
   * @param cycles Number of cycles to add
   * @apiNote Do not call this method outside of simulator.
   */
  void addCycles(int cycles);

  /**
   * Get previous test result.
   *
   * @return Jump flag.
   */
  boolean getJumpFlag();

  /**
   * Set new jump flag (test result)
   */
  void setJumpFlag(boolean flag);

  /**
   * Get history object
   *
   * @return history object.
   */
  ISmHistory getHistory();

  /**
   * Provide method to track back.
   *
   * @param smHistory History object.
   */
  void setHistory(ISmHistory smHistory);
}
