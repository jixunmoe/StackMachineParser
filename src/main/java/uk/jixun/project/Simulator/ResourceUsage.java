package uk.jixun.project.Simulator;

import uk.jixun.project.Instruction.ISmInstruction;

public class ResourceUsage implements IResourceUsage {
  private int readAddress = 0;
  private int writeAddress = 0;

  private boolean read = false;
  private boolean write = false;

  public static IResourceUsage fromInstruction(ISmInstruction inst) {
    return new ResourceUsage()
      .setWrite(inst.writeRam())
      .setRead(inst.readRam());
  }

  @Override
  public int getReadAddress() {
    return isRead() ? readAddress : 0;
  }

  @Override
  public void setReadAddress(int address) {
    if (isRead()) {
      readAddress = address;
    }
  }

  @Override
  public int getWriteAddress() {
    return isWrite() ? writeAddress : 0;
  }

  @Override
  public void setWriteAddress(int address) {
    if (isWrite()) {
      writeAddress = address;
    }
  }

  public boolean isRead() {
    return read;
  }

  public ResourceUsage setRead(boolean read) {
    this.read = read;
    return this;
  }

  public boolean isWrite() {
    return write;
  }

  public ResourceUsage setWrite(boolean write) {
    this.write = write;
    return this;
  }
}
