; A simple loop code extracted from paper by Shi, Huibin, appendix C.1
; Program: loop1 improved
; Author:  Huibin Shi
; Version: 0.1
; Date:    2006.03.??
;
; Calculates the square of values between 0 to 11 (inclusive),
; Using both add, mul and div.
; (with loop unrolling applied)
;
; Test Cycles:
; A M D  Cycles
; 1 1 5  3817
; 2 2 5  2642
; 5 5 5  2454
;


main:
	lit 6
	fp-

	lit 12
	cp Allocate
	!loc 0

	lit 12
	cp Allocate
	!loc 1

	lit 12
	cp Allocate
	!loc 2

	lit 12
	cp Allocate
	!loc 3

	lit 12
	cp Allocate
	!loc 4

	lit 0
	!loc 5

L1:
	@loc 5
	lit 12
	tlt
	bcr L2
	bp L3

L4:
	@loc 5
	tos++
	!loc 5
	bp L1

L2:
  ; loop1 body
  ; A[i] = B[i] = C[i] = D[i] = i;
  ; duplicate value "i" 4 times
	@loc 5
	COPY1
	COPY2
	COPY3

	; stack = [i, i, i, i]

  ; A[i] = i
	COPY4
	@loc 0
	add
	!

	; stack = [i, i, i]

  ; B[i] = i
	COPY3
  @loc 1
  add
  !

	; stack = [i, i]

  ; C[i] = i
	COPY2
  @loc 2
  add
  !

	; stack = [i]

  ; D[i] = i
	COPY1
	@loc 3
	add
	!

	bp L4

L3:
	lit 0
	!loc 5

L5:
  @loc 5
  lit 12
  tlt

  ; L6: loop body
  ; L7: Quit
  bcr L6
  bp L7

L8:
  @loc 5
  tos++
  !loc 5
  bp L5

L6:
  ; 4 copies of "i"
  @loc 5
	COPY1
	COPY2

  ; stack: [i, i, i]

  ; push A[i]
  @loc 0
  add
  @

  ; stack: [i, i, a]

  ; push B[i]
  rsd2
  @loc 1
  add
  @

  ; stack: [i, a, b]

  ; A[i] * B[i]
  mul

  ; stack: [i, a * b]
  COPY2
  ; stack: [i, a * b, i]

  ; push C[i]
  @loc 2
  add
  @
  ; stack: [i, a*b, c]

  COPY3

  ; stack: [i, a*b, c, i]
  @loc 3
  add
  @
  ; stack: [i, a*b, c, d]


  mul
  ; stack: [i, a*b, c*d]

  add
  ; stack: [i, mul_AB + mul_CD]
  lit 2
  div

  ; stack: [i, result]
  rsd2

  ; stack: [result, i]
  @loc 4
  add
  !

  ; stack: []
  bp L8

L7:
  lit 6
  fp+
  exit
