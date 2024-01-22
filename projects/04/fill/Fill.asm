// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen
// by writing 'black' in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen by writing
// 'white' in every pixel;
// the screen should remain fully clear as long as no key is pressed.

//// Replace this comment with your code.

@color
M=0

(LOOP)
    @SCREEN
    D=A

    @pixel //start
    M=D

    @KBD
    D=M

    @black
    D;JGT

    @white
    D;JEQ

    (black)
        @color
        M=-1
        @fill
        0;JMP

    (white)
        @color
        M=0
        @fill
        0;JMP
    
    (fill)
        @color
        D=M
        @pixel // M = start address
        A=M
        M=D

        @pixel
        M=M+1
        D=M

        @24576
        D=A-D

        @fill
        D;JGT

@LOOP
0;JMP





    