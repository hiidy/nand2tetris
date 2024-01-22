// n=0
@n
M=0

(LOOP)
// if (n R1) goto END
@n
D=M
@R1
D=D-M
@END
D;JEQ

//  *(R0 + n) = -1
@R0
D=M

@n
A=D+M  // A = D+A를 하면 안된다 A는 n을 뜻하고 M은 RAM[M]의 값을 뜻한다.
M=-1

// n = n + 1
@n
M=M+1
// goto LOOP
@LOOP
0;JMP

// END
(END)
@END
0;JMP