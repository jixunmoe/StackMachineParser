; A simple loop code extracted from paper by Shi, Huibin, appendix C.1
; Program: loop1
; Author:  Huibin Shi
; Version: 0.1
; Date:    2006.03.??
;
; Calculates the square of values between 0 to 11 (inclusive),
; Using both add, mul and div.
;
; Test Cycles:
; A M D  Cycles
; 1 1 5  2087
; 2 2 5  1366
; 5 5 5  1092
;


; Setup stack frame
main:
	lit 6
	fp-

	lei 12
	cl Allocate
	!loc 0

	lei 12
	cl Allocate
	!loc 1

	lei 12
	cl Allocate
	!loc 2

	lei 12
	cl Allocate
	!loc 3

	lei 12
	Cl Allocate
	!loc 4

	; i = 0
	lei 0
	!loc 5

L1:
	@loc 5
	lei 12
	tlt
	; Check if is less than 12

	; L2: Loop body
	bcl L2
	; L3: End of loop 1
	bl L3

L4:
	; Increase i by 1
	@loc 5
	lei 1
	add
	!loc 5

	bl L1

L2:
	@loc 5	; push dword[5]
	@loc 5	; push dword[5]
	@loc 0	; push pVariableA
	add		; add			  -->	pVariableA + index
	!		; store			  -->	*(pVariableA + index) = index

	@loc 5
	@loc 5
	@loc 1
	add
	!

	@loc 5
	@loc 5
	@loc 2
	add
	!

	@loc 5
	@loc 5
	@loc 3
	Add
	!

	bl L4

; End of loop 1 - second loop
L3:
	; i = 0
	lei 0
	!loc 5

L5:
	; cmp i LES 12
	@loc 5
	lei 12
	tlt

	; Goto Main body or quit
	bcl L6
	bl L7

L8:
	; inc dword[5]
	@loc 5
	lei 1
	add
	!loc 5

	; goto loop condition
	bl L5

L6:
	@loc 5
	@loc 0
	add		;	=>  pVariableA + i
	@		;	=>  push [pVariableA + i]

	@loc 5
	@loc 1
	add		;	=>	pVariableB + i
	@

	mul		;   =>  A[i] * B[i]

	@loc 5
	@loc 2
	add
	@

	@loc 5
	@loc 3
	add
	@

	mul		;	=>	C[i] * D[i]

	add		;	=>  A[i] * B[i] + C[i] * D[i]

	lei 2
	div		;	=>	(A[i] * B[i] + C[i] * D[i]) / 2

	@loc 5
	@loc 4
	add		;	=>  pVariableK + i

	!		;	=>  K[i] = (A[i] * B[i] + C[i] * D[i]) / 2

	bl L8

L7:
	; Restore Stack frame
	lit 6
	fp+
	exit

; K = [ 0, 1, 4, 9, 16, 25, 36, 49, 64, 81, 100, 121 ]
