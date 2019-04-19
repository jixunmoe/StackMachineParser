; Test for read-after-write errors in the simulator.

main:
  lit 1
  !loc 0

  ; Use branch to force simulator sync.
  bl __test

__test:
  lit 2

  !loc 0  ; expected to write value < 2 >  to  <fp+0>
  @loc 0  ; expected to read value  < 2 > from <fp+0>

  ; potential error:
  ;  - When dependency not properly resolved,
  ;    it could've stored a value of < 1 > instead to the stack. [read after write]

  exit

