# Project 04

## Objectives
fill, multi 구현

## TODO
- [x] fill
- [x] multi

## 구현 과정
- fill : LOOP라벨 안에 black, white, fill 라벨을 각각 선언한다. @pixel로 현재 픽셀의 위치를 저장하고 @KBD로 키보드의 입력 여부를 확인한다. 키보드가 눌리면 black으로 아니면 white로 이동하고 color에 색을 저장. 그 후에 fill에서 @24576(마지막 픽셀)까지 루프를 돌면서 저장된 color를 각 픽셀에 저장한다.
- multi : 3 * 2는 3을 2번 더하는 것이기 때문에 R0를 R1번 계속 더하도록 구현했다. 한 번 더할 때마다 R1의 값을 1씩 빼주고 0일 경우 LOOP를 탈출 하게 한다.


## 배운 점
