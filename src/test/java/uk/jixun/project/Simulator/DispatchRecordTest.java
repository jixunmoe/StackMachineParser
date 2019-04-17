package uk.jixun.project.Simulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.jixun.project.Instruction.MockInstruction;

import java.util.List;

import static uk.jixun.project.Instruction.MockInstruction.createFromOpCode;
import static uk.jixun.project.Simulator.MockOpCode.createDependencyTestOpCode;

import static org.junit.Assert.*;

@DisplayName("DispatchRecord implementation test")
class DispatchRecordTest {
  private MockExecutionContext mockContext = null;

  @BeforeEach
  void setUp() {
    mockContext = new MockExecutionContext();
  }

  @Test
  @DisplayName("Simple Dependency Resolve")
  void dependencyTest() {
    MockHistory history = MockHistory.create();

    MockInstruction push1 = createFromOpCode(createDependencyTestOpCode(0, 1));
    MockInstruction push2 = createFromOpCode(createDependencyTestOpCode(0, 1));
    MockInstruction push3 = createFromOpCode(createDependencyTestOpCode(0, 1));
    MockInstruction add = createFromOpCode(createDependencyTestOpCode(2, 1));

    MockDispatchRecord push1Record = MockDispatchRecord.createFromInstruction(push1);
    MockDispatchRecord push2Record = MockDispatchRecord.createFromInstruction(push2);
    MockDispatchRecord push3Record = MockDispatchRecord.createFromInstruction(push3);

    history.add(push1Record);
    history.add(push2Record);
    history.add(push3Record);

    mockContext.setHistory(history);

    DispatchRecord addRecord = new DispatchRecord();
    addRecord.setContext(mockContext);
    addRecord.setInst(add);
    history.add(addRecord);
    List<IDispatchRecord> dependencies = addRecord.getDependencies();

    assertArrayEquals(
      "Should contains push2 and push3 as dependency",
      new MockDispatchRecord[]{push2Record, push3Record},
      dependencies.toArray()
    );
  }

  @Test
  @DisplayName("Indirect Dependency Resolve")
  void dependencyTestIndirect() {
    MockHistory history = MockHistory.create();

    MockInstruction push1 = createFromOpCode(createDependencyTestOpCode(0, 1));
    MockInstruction push2 = createFromOpCode(createDependencyTestOpCode(0, 1));
    MockInstruction push3 = createFromOpCode(createDependencyTestOpCode(0, 1));
    MockInstruction inc = createFromOpCode(createDependencyTestOpCode(1, 1));
    MockInstruction add = createFromOpCode(createDependencyTestOpCode(2, 1));

    MockDispatchRecord push1Record = MockDispatchRecord.createFromInstruction(push1);
    MockDispatchRecord push2Record = MockDispatchRecord.createFromInstruction(push2);
    MockDispatchRecord push3Record = MockDispatchRecord.createFromInstruction(push3);
    MockDispatchRecord incRecord = MockDispatchRecord.createFromInstruction(inc);

    history.add(push1Record);
    history.add(push2Record);
    history.add(push3Record);
    history.add(incRecord);

    mockContext.setHistory(history);

    DispatchRecord addRecord = new DispatchRecord();
    addRecord.setContext(mockContext);
    addRecord.setInst(add);
    history.add(addRecord);
    List<IDispatchRecord> dependencies = addRecord.getDependencies();

    assertArrayEquals(
      "Should contains 'push2' and 'inc' as dependency",
      new MockDispatchRecord[]{push2Record, incRecord},
      dependencies.toArray()
    );
  }
}
