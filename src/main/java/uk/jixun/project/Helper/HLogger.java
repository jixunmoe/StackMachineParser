package uk.jixun.project.Helper;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HLogger {
  public static Logger getLogger(String name, Level level) {
    Logger logger = Logger.getLogger(name);
    Handler handler = new ConsoleHandler();
    logger.setLevel(level);
    handler.setLevel(level);
    return logger;
  }
}
