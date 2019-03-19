package uk.jixun.project.Program.Simulator;

public interface IResourceUsage {
  boolean reads();
  boolean writes();

  int getReadAddress();
  void setReadAddress(int address);

  int getWriteAddress();
  void setWriteAddress(int address);
}
