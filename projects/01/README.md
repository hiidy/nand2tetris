# Project 01

## Objectives
Nand 게이트를 시작으로 기본 논리게이트를 만들어 나가기

## TODO
- [x] Not
- [x] And
- [x] Or
- [x] Xor
- [x] Mux
- [x] DMux
- [x] Not16
- [x] And16
- [x] Or16
- [x] Mux16
- [x] Or8Way
- [x] Mux4Way16
- [x] Mux8Way16
- [x] DMux4Way
- [x] DMux8Way


## 구현 과정
- Nand 게이트를 시작으로 기본 논리게이트 구현(Not, Or, And)
- Not, And, Or를 이용해서 Xor, Mux, Dmux 구현
- Multiplexor게이트는 기본 게이트를 이용해서 구현(ex: Mux4Way16은 Mux16을 이용)
 

## 배운 점
- Mux게이트를 만들 때 output이 참인 불 표현식을 찾고 불대수 법칙을 이용해서 최대한 간결하게 만들면 코드로 표현하기 쉽다.
- 비트는 오른쪽에서 왼쪽으로 인덱스가 부여되는 것을 잊지 말자. (ex: 0번째 비트는 2^0, 1번째 비트는 2^1, 2번째 비트는 2^2)