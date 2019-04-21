package uk.jixun.project.Gui;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class WrapFileFilter extends FileFilter {
  private FileFilter filter;
  private String description;

  public WrapFileFilter(FileFilter filter, String description) {
    this.filter = filter;
    this.description = description;
  }

  @Override
  public boolean accept(File file) {
    return filter.accept(file);
  }

  @Override
  public String getDescription() {
    return description;
  }
}
