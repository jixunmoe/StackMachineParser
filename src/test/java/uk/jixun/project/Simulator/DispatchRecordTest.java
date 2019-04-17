package uk.jixun.project.Simulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.jixun.project.Instruction.MockInstruction;
import uk.jixun.project.Program.MockProgram;

import static uk.jixun.project.Instruction.MockInstruction.createFromOpCode;
import static uk.jixun.project.Simulator.MockOpCode.createDependencyTestOpCode;

class DispatchRecordTest {
  MockExecutionContext context;

  @BeforeEach
  void setUp() {
    context = new MockExecutionContext();
  }

  @Test
  void emptyDependencyTest() {

    MockInstruction push1 = createFromOpCode(createDependencyTestOpCode(0, 1));
    MockInstruction push2 = createFromOpCode(createDependencyTestOpCode(0, 1));
    MockInstruction push3 = createFromOpCode(createDependencyTestOpCode(0, 1));
    MockInstruction add = createFromOpCode(createDependencyTestOpCode(2, 1));

    MockProgram prog = new MockProgram();
    prog.addInstruction(push1);
    prog.addInstruction(push2);
    prog.addInstruction(push3);
    prog.addInstruction(add);

    context.setHistory();

    DispatchRecord record = new DispatchRecord();
    record.setContext(context);
    record.getDependencies();
  }
}
