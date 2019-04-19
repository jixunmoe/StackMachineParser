; Test Program 1 by Jixun
; Version 0.1, 2019.04.18
;
; A simple test program to add number from 1 to 100.
; Then perform syscall PrintStackValue to get value.
;

; Main fn uses 2 variables:
; int i = 1         fp+0
; int result = 0    fp+1

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
