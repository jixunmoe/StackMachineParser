; Sample Program (1) for simulator correctness test
; Program: Sum(100)
; Author:  Jixun
; Version: 0.1
; Date:    2019.04.18
;
; A simple test program to add number from 1 to 100.
; Then perform syscall PrintStackValue to get value.
;
; Test Cycles:
; A M D  Cycles
; 1 1 5  4844
; 2 2 5  3028
; 5 5 5  2628
;


main:
  lit 2
  fp-

__reset:
  lit 1
  !loc 0
  lit 0
  !loc 1

__begin:
  ; Check for loop
  @loc 0
  lei 100
  tgt
  bcl __quit

__body:
  @loc 1        ; push result
  @loc 0        ; push i
  add           ; add them
  !loc 1        ; save to result

__next:
  ; Increase Counter
  lit 1         ; push 1
  @loc 0        ; push [i]
  add           ; add
  !loc 0        ; pop [i]
  bl __begin

__quit:
  ; print last param
  ; cl PrintStackValue

  lit 2
  fp+
  exit
