package uk.jixun.project.Simulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.jixun.project.Instruction.MockInstruction;
import uk.jixun.project.OpCode.MockOpCode;
import uk.jixun.project.Simulator.DispatchRecord.IDispatchRecord;
import uk.jixun.project.Simulator.DispatchRecord.InstructionDispatchRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static uk.jixun.project.Instruction.MockInstruction.createFromOpCode;
import static uk.jixun.project.OpCode.MockOpCode.createDependencyTestOpCode;

import static org.junit.Assert.*;

@DisplayName("InstructionDispatchRecord implementation test")
class InstructionDispatchRecordTest {
  private MockExecutionContext mockContext = null;
  private MockHistory mockHistory = null;

  @BeforeEach
  void setUp() {
    mockHistory = new MockHistory();
    mockContext = new MockExecutionContext();
    mockContext.setHistory(mockHistory);
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

    InstructionDispatchRecord addRecord = new InstructionDispatchRecord();
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
    MockInstruction push1 = createFromOpCode(createDependencyTestOpCode(0, 1));
    MockInstruction push2 = createFromOpCode(createDependencyTestOpCode(0, 1));
    MockInstruction push3 = createFromOpCode(createDependencyTestOpCode(0, 1));
    MockInstruction inc = createFromOpCode(createDependencyTestOpCode(1, 1));
    MockInstruction add = createFromOpCode(createDependencyTestOpCode(2, 1));

    MockDispatchRecord push1Record = MockDispatchRecord.createFromInstruction(push1);
    MockDispatchRecord push2Record = MockDispatchRecord.createFromInstruction(push2);
    MockDispatchRecord push3Record = MockDispatchRecord.createFromInstruction(push3);
    MockDispatchRecord incRecord = MockDispatchRecord.createFromInstruction(inc);

    mockHistory.add(push1Record, push2Record, push3Record, incRecord);

    InstructionDispatchRecord addRecord = new InstructionDispatchRecord();
    addRecord.setContext(mockContext);
    addRecord.setInst(add);
    mockHistory.add(addRecord);
    List<IDispatchRecord> dependencies = addRecord.getDependencies();

    assertArrayEquals(
      "Should contains 'push2' and 'inc' as dependency",
      new MockDispatchRecord[]{push2Record, incRecord},
      dependencies.toArray()
    );
  }

  @Test
  @DisplayName("Execute virtual instruction")
  void executeAndGetStack() {
    mockContext.setStackResolver((offset, exeId, size) -> {
      assertEquals("offset = 0", offset, 0);
      assertEquals("exeId = 0", exeId, 1);
      return new ArrayList<>(Arrays.asList(2, 4));
    });

    MockInstruction add = createFromOpCode(MockOpCode.createMockCalculation(2, 6));
    InstructionDispatchRecord record = new InstructionDispatchRecord();
    record.setContext(mockContext);
    record.setInst(add);
    record.setExecutionId(1);
    assertFalse("should not be marked as executed", record.executed());

    List<Integer> result = record.executeAndGetStack();
    assert result != null;
    assertArrayEquals("Result stack", new Integer[]{6}, result.toArray());
  }
}
