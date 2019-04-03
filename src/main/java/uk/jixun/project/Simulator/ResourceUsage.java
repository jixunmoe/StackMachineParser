package uk.jixun.project.Simulator;

public class ResourceUsage implements IResourceUsage {
  private int readAddress = 0;
  private int writeAddress = 0;

  @Override
  public int getReadAddress() {
    return readAddress;
  }

  @Override
  public void setReadAddress(int address) {
    readAddress = address;
  }

  @Override
  public int getWriteAddress() {
    return writeAddress;
  }

  @Override
  public void setWriteAddress(int address) {
    writeAddress = address;
  }
}
