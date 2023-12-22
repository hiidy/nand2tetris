# Project 02

## Objectives
- HalfAdder부터 ALU까지 구현

## TODO
- [x] HalfAdder
- [x] FullAdder
- [x] Add16
- [x] Inc16
- [x] ALU


## 구현 과정
- Xor과 And게이트를 이용해서 HalfAdder를 구현.
- HalfAdder와 Or을 이용해서 FullAdder를 구현.
- FullAdder를 16개 이용해서 16비트 가산기를 만들었고 다시 이를 이용해서 16비트 증가기를 구현.
- 
 

## 배운 점
- 인자를 입력하지 않으면 default 값으로 false가 부여된다.
- FullAdder의 진리표에서 Sum과 Carry에 대한 불리언식을 구하고(참인 경우만 표현) 이를 간소화해서 FullAdder를 더욱 간단하게 구현할 수 있다.
- ALU의 ng를 구현할 때 2의 보수법의 특징을 이용하면 쉽게 음수인지 확인할 수 있다.
- zr를 확인할 때는 16비트를 LSB, MSB로 각각 8비트씩 나눠서 Or8Way를 이용하면 쉽게 구현할 수 있다.