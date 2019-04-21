; Sample Program (4) for simulator correctness test
; Program: Fibonacci(20)
; Author:  Jixun
; Version: 0.1
; Date:    2019.04.21
;
; Calculates fibonacci(20) using recursive function calls,
; With cache enabled to speed up.
;
; Test Cycles:
; A M D  Cycles
; 1 1 5  2924
; 2 2 5  2067
; 5 5 5  1991
;


cl __main

; keep return value
RSU2
EXIT

__main:
  lit 20
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

__use_cache:
  ; back up xp
  @xp

  ; xp = 1 + value
  RSU2
  lit 1
  add
  !xp
  ; push *(1 + value)
  @[xp]

  ; restore xp
  RSU2
  !xp
  bl __finish

__next:
  ; Check if we have cache (at address 0)
  ; if "cache > n" => "cache - n >= 0"
  ldl 0
  COPY2
  SUB
  TPV
  BCL __use_cache

  ; Value not registered in the cache.
  ; copy "n"
  COPY1
  COPY2

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

  ; write to cache
  COPY1
  RSU3
  cl __write_cache

  bl __finish


__read_cache:
  RSU2

  lit 1
  fp-

  ; back up xp
  @xp
  !loc 1

  ; &cache = 1
  ; &cache[value]
  lit 1
  add
  !xp
  ; push cache[value]
  @[xp]

  ; restore xp
  @loc 1
  !xp

  ; clean up
  lit 1
  fp+
  RSU2
  EXIT

__write_cache:
  ; push value
  ; push n
  ; call __write_cache
  RSD3

  lit 1
  fp-

  ; back up xp
  @xp
  !loc 1

  ; copy address
  COPY1

  ; value -> n -> n
  RSD3
  ; n -> value -> n

  ; xp = &cache[n]
  lit 1
  add
  !xp

  ; mov cache[n], value
  ![xp]

  ; if __write_cache is called, previous cache must've called as well already
  stl 0

  ; restore xp
  @loc 1
  !xp

  ; clean up
  lit 1
  fp+
  EXIT
