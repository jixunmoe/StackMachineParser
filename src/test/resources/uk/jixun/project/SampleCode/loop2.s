; A simple loop code extracted from paper by Shi, Huibin, appendix C.1
; Program: loop2
; Author:  Huibin Shi
; Version: 0.1
; Date:    2006.03.??
;
; Create two arrays with value 0~11 and 50~61,
; then swap the content of the arrays.
;
; Test Cycles:
; A M D  Cycles
; 1 1 5  2461
; 2 2 5  1406
; 5 5 5  1068
;

main:
  ; uses 4 variables
  lit 4
  fp-

  lei 12
  cl Allocate
  !loc 0

  lei 12
  cl Allocate
  !loc 1


  ; *(fp+2) = 0
  lei 0
  !loc 2

; loop 1
L1:
  ; if [fp+2] < 12
  @loc 2
  lei 12
  tlt

  bcl L2
  bl L3

L4:
  ; i += 1
  @loc 2
  lei 1
  add
  !loc 2
  bl L1

L2:
  ; push i
  @loc 2
  ; get X[i]
  @loc 2
  @loc 0
  add
  ; X[i] = i
  !

  ; push (i + 50)
  @loc 2
  lei 50
  add

  ; get Y[i]
  @loc 2
  @loc 1
  add
  ; Y[i] = i + 50
  !

  bl L4

; loop 2
L3:
  ; i = 0
  lei 0
  !loc 2

L5:
  @loc 2
  lei 12
  tlt
  bcl L6
  bl L7

L8:
  @loc 2
  lei 1
  add
  !loc 2
  bl L5

L6:
  @loc 2
  @loc 0
  add
  @
  !loc 3
  @loc 2
  @loc 1
  add
  @
  @loc 2
  @loc 0
  add
  !
  @loc 3
  @loc 2
  @loc 1
  add
  !
  bl L8

L7:
  lit 4

  fp+
  exit
