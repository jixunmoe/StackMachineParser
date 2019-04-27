; Sample Program (2) for simulator correctness test
; Program: Factorial(10)
; Author:  Jixun
; Version: 0.1
; Date:    2019.04.21
;
; Calculates factorial(10) using recursive function calls.
;
; Test Cycles:
; A M D  Cycles
; 1 1 5  672
; 2 2 5  454
; 5 5 5  454
;


cl __main

; keep return value
RSU2
EXIT

__main:
  lit 10
  cl fn_factorial

  ; nothing to clear
  ; however, return value needs to be respected.
  RSU2
  EXIT

fn_factorial:
  lit 1
  fp-

  ; swap TOS/NOS, top of stack => param
  RSU2

  ; Make a copy of the top item, and test for zero
  COPY1
  tnz
  BCL __next

  ; ZERO? => ret exp1
  drop1
  lit 1

__finish:
  ; Stack: [return addr, result]
  RSU2

  ; Clear stack frame
  lit 1
  fp+

  ; return :p
  EXIT

__next:
  ; keep a copy of current value
  COPY1

  ; subtract by 1 and call fn_factorial again.
  lit 1
  sub
  cl fn_factorial

  ; multiply result against copied value.
  mul
  bl __finish

