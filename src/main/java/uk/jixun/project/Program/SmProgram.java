package uk.jixun.project.Program;

import uk.jixun.project.Exceptions.LabelDuplicationException;
import uk.jixun.project.Exceptions.LabelNotFoundException;
import uk.jixun.project.Instruction.ISmInstruction;
import uk.jixun.project.OpCode.SysCall.AllocateSysCall;
import uk.jixun.project.OpCode.SysCall.HaltSysCall;
import uk.jixun.project.OpCode.SysCall.ISysCall;
import uk.jixun.project.Program.NodeGraph.ISmProgramGraph;
import uk.jixun.project.Program.NodeGraph.SmDependencyGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SmProgram implements ISmProgram {
  private static final int sysCallBaseAddress = 0x10000;
  private final Object lock = new Object();
  private final AtomicInteger sysCallId = new AtomicInteger(1);
  private List<ISmInstruction> instructions = new ArrayList<>();
  private HashMap<String, Integer> labelMapping = new HashMap<>();
  private Map<Integer, ISysCall> sysCallMap = new HashMap<>();

  public SmProgram() {
    // FIXME: Register repository to register all syscall required.
    registerSysCall("Allocate", new AllocateSysCall());
    registerSysCall("Halt", new HaltSysCall());
  }

  @Override
  public List<ISmInstruction> getInstructions() {
    return instructions;
  }

  @Override
  public void setInstructions(List<ISmInstruction> instructions) {
    synchronized (lock) {
      this.instructions = instructions;

      AtomicInteger index = new AtomicInteger(0);
      // assign eip.
      this.instructions.forEach(inst -> {
        inst.setEip(index.getAndIncrement());
      });
    }
  }

  @Override
  public ISmInstruction getInstruction(int index) throws ArrayIndexOutOfBoundsException {
    return instructions.get(index);
  }

  @Override
  public void addInstruction(ISmInstruction instruction) {
    synchronized (lock) {
      int nextEip = instructions.size();

      instructions.add(instruction);
      instruction.setEip(nextEip);
    }
  }

  @Override
  public Map<String, Integer> getLabelMapping() {
    return labelMapping;
  }

  @Override
  public void registerLabel(String label, int address) {
    labelMapping.put(label, address);
  }

  @Override
  public long resolveLabel(String label) throws LabelNotFoundException {
    if (!labelMapping.containsKey(label)) {
      throw LabelNotFoundException.fromLabel(label);
    }

    return labelMapping.get(label);
  }

  @Override
  public ISmProgramGraph createGraph() {
    ISmProgramGraph graph = new SmDependencyGraph();
    graph.setProgram(this);
    return graph;
  }

  @Override
  public void registerSysCall(String name, ISysCall sysCall) {
    int id = sysCallId.getAndIncrement();
    int address = sysCallBaseAddress | id;
    registerLabel(name, address);
    sysCallMap.put(id, sysCall);
  }

  @Override
  public boolean isSysCall(int address) {
    return (sysCallBaseAddress & address) == sysCallBaseAddress;
  }

  @Override
  public ISysCall getSysCall(int address) {
    int id = ~sysCallBaseAddress & address;
    return sysCallMap.get(id);
  }
}
