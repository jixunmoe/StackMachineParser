package uk.jixun.project.Program;

import uk.jixun.project.Exceptions.LabelNotFoundException;
import uk.jixun.project.Instruction.ISmInstruction;

import java.util.List;
import java.util.Map;

public interface ISmProgram {
  List<ISmInstruction> getInstructions();
  void setInstructions(List<ISmInstruction> instructions);

  Map<String, Long> getLabelMapping();
  void registerLabel(String name, long address);
  long resolveLabel(String label) throws LabelNotFoundException;
}
