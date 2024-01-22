// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// Assumes that R0 >= 0, R1 >= 0, and R0 * R1 < 32768.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

//// Replace this comment with your code.

@sum
M=0
@R2
M=0

// LOOP
(LOOP)
    // if (R1 = 0) goto STOP
    @R1
    D=M
    
    // goto STOP
    @STOP
    D;JEQ

    // sum=sum+R0
    @R0
    D=M
    @sum
    M=M+D

    // R1=R1-1
    @R1
    M=M-1

    @LOOP
    0;JMP

(STOP)
    @sum
    D=M
    @R2
    M=D

(END)
@END
0;JMP