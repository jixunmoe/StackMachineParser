; Sample Program (3) for simulator correctness test
; Program: Fibonacci(10)
; Author:  Jixun
; Version: 0.1
; Date:    2019.04.21
;
; Calculates fibonacci(10) using recursive function calls,
; No cache implemented.
;
; Test Cycles:
; A M D  Cycles
; 1 1 5  15815
; 2 2 5  9773
; 5 5 5  9773
;


cl __main

; keep return value
RSU2
EXIT

__main:
  lit 10
  cl fn_fibonacci

  ; nothing to clear
  ; however, return value needs to be respected.
  RSU2
  EXIT

fn_fibonacci:
  lit 1
  fp-

  ; swap TOS/NOS, top of stack => param
  RSU2

  ; Make a copy of the top item
  COPY1

  ; Check "n >= 2", or "(n - 2) >= 0"
  lit 2
  sub
  tpv
  BCL __next

  ; Keep as it is.

__finish:
  ; Stack: [return addr, result]
  RSU2

  ; Clear stack frame
  lit 1
  fp+

  ; return :p
  EXIT

__next:
  ; keep two copies of the value
  COPY1

  ; subtract by 1 and call fn_fibonacci again.
  lit 1
  sub
  cl fn_fibonacci

  ; Move previous copy to TOS
  RSU2

  ; subtract by 2 and call fn_fibonacci again.
  lit 2
  sub
  cl fn_fibonacci

  ; add result against copied value.
  add
  bl __finish

