package uk.jixun.project.Helper;

public class LogicalRotate {
  public static int rotateRight(int value, int shift) {
    long v = (long)value;
    return (int)((v >> shift) | (v << (32 - shift)));
  }

  public static int rotateRightByte(int value) {
    return rotateRight(value, 8);
  }

  public static int rotateRightShort(int value) {
    return rotateRight(value, 16);
  }

  public static int rotateLeft(int value, int shift) {
    return rotateRight(value, 32 - shift);
  }
}
