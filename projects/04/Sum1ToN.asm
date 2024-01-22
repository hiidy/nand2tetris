// i = 1
@i
M=1

// sum = 0
@sum
M=0

// LOOP
(LOOP)
// i-r0 > 0
@i
D=M

@R0
D=D-M

@STOP
D;JGT

// sum=sum+i
@i
D=M
@sum
D=D+M
M=D

// i=i+1
@i
M=M+1

@LOOP
0;JMP

// STOP
(STOP)
@sum
D=M

@R1
M=D

// END
(END)
@END
0;JMP
