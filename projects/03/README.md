# Project 03

## Objectives
Bit부터 PC까지 구현

## TODO
- [x] Bit
- [x] Register
- [x] RAM8
- [x] RAM64
- [x] RAM512
- [x] RAM4K
- [x] RAM16K
- [x] PC

## 구현 과정
- Bit 구현 : load는 레지스터의 잠금상태를 표현하고 이를 입력받기 위해서 Mux를 사용했다.
- 16개의 Bit로 레지스터를 구현.
- RAM 구현 : load의 정보를 8개의 레지스터로 전달하기 위해서 DMux8way를 사용했다. 이렇게 전달된 입력값의 결과를 Mux8way16을 이용해서 RAM8출력에 전달.
- RAM8부터 RAM16K까지 이전 RAM을 이용해서 구현. (주의 : RAM4k에서 16k로 넘어갈 때는 이전과 다르게 address의 길이가 2bit가 늘어났다. -> 레지스터가 4배 증가)
- PC 구현 : inc, load, reset에 대한 정보를 Mux8Way16의 sel로 처리한다. 총 8개의 경우의 수가 나오기 때문에 sel[3]으로 처리할 수 있다.

 

## 배운 점
- 칩의 연산 같은 시간지연은 클럭주기보다 같거나 작아야 한다.
- RAM 구현에서 중요한 점은 임의로 레지스터를 선택하고 접근하는 시간이 즉각적이고 RAM의 크기나 레지스터의 주소와 무관해야 한다는 점이다. <br> 임의로 레지스터에 접근하기 위해서 사용한 것이 DMux8way이다. 이 칩은 3개의 입력을 받고 8개의 출력을 내보낸다. 이를 이용해서 8개의 레지스터에 접근하고 그 후 각 레지스터에 접근해서 RAM8칩의 결과를 Mux8Way16으로 출력한다.