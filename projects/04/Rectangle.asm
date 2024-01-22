// addr = SCREEN

@SCREEN
D=A

@addr
M=D

// n = RAM[0]
@R0
D=M

@n
M=D

// i = 0
@i
M=0

(LOOP)
    @i
    D=M
    @n
    D=D-M
    @END
    D;JGT

    @addr //RAM[addr] 구현할 때 주의하기 주소값이 addr(A=@addr인 곳에 접근해야 한다.)
    A=M
    M=-1

    @32
    D=A
    @addr
    M=D+M

    @i
    M=M+1

    @LOOP
    0;JMP

(END)
    @END
    0;JMP


