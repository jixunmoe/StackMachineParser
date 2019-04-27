package uk.jixun.project.OpCode;

public enum SmOpCodeEnum {
  // Special OpCode, it is not an opcode.
  NONE,

  ADD, SUB, MUL, DIV,
  // hmul & hdiv
  HMUL, HDIV,

  // increment / decrement (defined later)
  // INC, DEC,

  AND, OR, NOT, XOR,
  LSL, LSR, ROL, ROR,

  // Extract Right most byte (& 0xFF)
  ROTATE_RIGHT_BYTE,
  // Extract Right most word (& 0xFFFF)
  ROTATE_RIGHT_WORD,

  // xbyte_n [OPERAND:n];
  // [a][b][c][d] >>> [0][0][0][n]
  EXTRACT_RIGHT_BYTE,

  // xwrd_n [OPERAND:nn];
  // [aa][bb] >>> [00][nn]
  EXTRACT_RIGHT_WORD,

  // ibyte_n [OPERAND:x];
  // [a][b][c][d] >>> [a][x][b][c]
  // (n & 0xFF00FFFF) | (x << 16)
  INSERT_BYTE,

  // iword_n [OPERAND:xx];
  // [aa][bb] >>> [xx][bb]
  // (n & 0xFF00FFFF) | (x << 16)
  INSERT_WORD,

  DUP,
  COPY,
  DROP,
  RSU,
  RSD,

  // TODO: Are those required for this project?
  // >r
  // Push value of [TOS] to Return Stack
  PUSH_TO_RS,
  // <r
  POP_FROM_RS,

  // TODO: Some other push/pop to/from return stack here

  // @Register; @
  // e.g. @xp
  // x86 equivalent: pop xp
  POP,

  // !Register; !
  // e.g. !xp
  // x86 equivalent: push xp
  PUSH,

  // Test: Check for TOS and NOS; Write flag (True/False) to indicate test result.
  // TOS == NOS; TOS != NOS
  TEST_EQ, TEST_NEQ,
  // TOS > NOS; TOS < NOS
  TEST_GT, TEST_LT,
  // TOS < 0; TOS >= 0
  TEST_NEGATIVE, TEST_POSITIVE,
  // TOS != 0
  TEST_NOT_ZERO,

  // Test against XP/YP
  TEST_XP_ZERO,
  TEST_YP_ZERO,

  // Un-conditional jump / call
  // BP: Relative Jump (current page)
  RELATIVE_JUMP,
  // BL: Absolute Jump
  ABSOLUTE_JUMP,
  // CP: Relative Call (current page) (addr = 17 bit)
  RELATIVE_CALL,
  // CL: Absolute Call (addr = 24 bit)
  ABSOLUTE_CALL,
  // EXIT: Unconditional Return from CALL
  RETURN,

  // Conditional jump / call if flag is set to True (1)
  // BCR: Jump to address relative to PC
  COND_RELATIVE_JUMP,
  // BCP: Jump to address relative to current page
  COND_PAGE_JUMP,
  // BCL: Jump to an absolute address.
  COND_ABSOLUTE_JUMP,
  // CCP: Call to address relative to current page.
  COND_RELATIVE_CALL,
  // CCL: Call to an absolute address.
  COND_ABSOLUTE_CALL,
  // ?EXIT: Conditional Return
  COND_RETURN,

  // Memory / Data Access
  // LIT: Load 8 bit literal
  PUSH_BYTE,
  // LSI: Load short immediate (17 bits)
  PUSH_SHORT,
  // LEI: Load Extended immediate
  PUSH_EXTEND,

  // @LOC [n]: Fetch Local, at address fp+n
  PUSH_FRAME_ADDR,
  // !LOC [n]: Store local at address fp+n
  POP_FRAME_ADDR,

  // LDP: LoaD Page relative address
  PUSH_INDIRECT_RELATIVE,
  // STP: STore Page relative address
  POP_INDIRECT_RELATIVE,

  // LDL: LoaD from absoLute address
  PUSH_INDIRECT_ABSOLUTE,
  // STL: Store to absoLute address
  POP_INDIRECT_ABSOLUTE,

  // ![TOS]: Store data (tos = address; nos = data); mov dword[TOS], data
  // !: Store data (tos = data; nos = address); mov dword[NOS], data
  // Store mem contents addressed by register
  // Register can be TOS/NOS(omitted)/XP/YP
  PUSH_REGISTER,

  // @[reg]: Fetch
  POP_REGISTER,

  // Push register content and then perform register increment / decrement
  // ![REG++] / ![REG--]
  PUSH_REGISTER_INC, PUSH_REGISTER_DEC,

  // Pop register content and then perform register increment / decrement
  // @[REG++] / @[REG--]
  POP_REGISTER_INC, POP_REGISTER_DEC,

  // General
  // reg+; reg-; reg++; reg--
  REG_ADD, REG_SUB,
  REG_INC, REG_DEC,

  SAVE_TO_REG, PUSH_REG_VALUE,

  SYS_CALL,
}
