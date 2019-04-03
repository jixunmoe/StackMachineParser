package uk.jixun.project.Gui;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class GuiManager {
  private static AffineTransform affinetransform;
  private static FontRenderContext frc;
  private static Font fontMono;
  private static Font fontDialog;

  private static Color nodeBorderColour = Color.BLACK;
  private static Color nodeBackgroundColour = new Color(255, 247, 229);
  private static Color nodeTextColour = Color.BLACK;

  static {
    affinetransform = new AffineTransform();
    frc = new FontRenderContext(affinetransform, true, true);
    fontMono = Font.getFont(Font.MONOSPACED);
    fontDialog = Font.getFont(Font.DIALOG);
  }

  public static Font getFontMono() {
    return fontMono;
  }

  public static Font getFontDialog() {
    return fontDialog;
  }

  public static Rectangle2D getFontBound(Font font, String text) {
    return font.getStringBounds(text, getFontRenderContext());
  }

  public static Rectangle2D getMonoBound(String text) {
    return getFontMono().getStringBounds(text, getFontRenderContext());
  }

  public static Rectangle2D getDialogBound(String text) {
    return getFontDialog().getStringBounds(text, getFontRenderContext());
  }

  private static FontRenderContext getFontRenderContext() {
    return frc;
  }

  public static Color getNodeBorderColour() {
    return nodeBorderColour;
  }

  public static Color getNodeBackgroundColour() {
    return nodeBackgroundColour;
  }

  public static Color getNodeTextColour() {
    return nodeTextColour;
  }
}
