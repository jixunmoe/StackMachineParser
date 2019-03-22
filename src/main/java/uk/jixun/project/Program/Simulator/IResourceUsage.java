package uk.jixun.project.Program.Simulator;

public interface IResourceUsage {
  int getReadAddress();
  void setReadAddress(int address);

  int getWriteAddress();
  void setWriteAddress(int address);
}
