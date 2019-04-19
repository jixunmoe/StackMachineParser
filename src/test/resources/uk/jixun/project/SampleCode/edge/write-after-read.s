; Test for read-after-write errors in the simulator.

main:
  lit 1
  !loc 0  ; mov [fp+0], 1

  ; Use branch to force simulator sync.
  bl __test

__test:
  lit 2   ; push 2

  ; push [fp + 0]
  ; pop [fp + 0]
  @loc 0  ; expected to read value  < 1 > from <fp+0>
  !loc 0  ; expected to write value < 2 >  to  <fp+0>

  ; potential error:
  ;  - When dependency not properly resolved,
  ;    "!loc 0" can write 2 before "@loc 0" tries to read.
  ; expected behaviour:
  ; stack: [  ]

  exit

