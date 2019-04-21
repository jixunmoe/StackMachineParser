package uk.jixun.project;

import uk.jixun.project.Gui.MainForm;

import javax.swing.*;
import java.awt.*;

public class Entry {
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(javax.swing.plaf.nimbus.NimbusLookAndFeel.class.getName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    MainForm form = new MainForm();
    form.setVisible(true);
  }
}
