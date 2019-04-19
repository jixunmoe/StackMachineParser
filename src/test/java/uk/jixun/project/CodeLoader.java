package uk.jixun.project;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.net.URL;

public class CodeLoader {
  @SuppressWarnings("UnstableApiUsage")
  public static String loadSampleCode(String name) throws Exception {
    URL url = Resources.getResource(StackMachineInstParserTest.class, "SampleCode/" + name + ".s");
    return Resources.toString(url, Charsets.UTF_8);
  }
}
