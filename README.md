# IN3040_Oblig1

I chose to solve the assignment using java because I’m quite comfortable doing so. By looking at the ROBOL grammar I though it smart to make all non-terminals into classes. 
I made statement an abstract class since statement is either a stop, turn, step, assignment or loop. The class Expression is also abstract since an expression either is an identifier, a number, an arithmetic expression or a boolean expression. From the ROBOL grammar I got the impression that boolean expressions have the same functionality as arithmetic expressions, so I made BoolExp inherit from ArithmeticExp.
Turn takes an argument “direction” that is either clockwise or counter-clockwise.  I chose to make this an enum since the action varies on what direction is chosen. I also created an enum for the binary operations, to make it easy for myself. For the assignment class I made the enum AssignEnum, since the action would vary depending on whether assignment = identifier++ or assignment = identifier--.
My Robot class takes in a list of statements, instead of individual statements, so the list of statements have to be defined before the robot is defined. However when a loop is introduced, the loop.interpret method takes in the robot as argument, so the way I’ve set it up in the testCode is that I first define the list of statements, then put the list into the robot for interpretation, let the robot finish running the list of statements ,and then introduce the loop, this loop does not get sent into Robot as a statement. Whether or not this is the way to do it, I’m not sure, but it works!

Instructions on how to run the program

Typing the following in the terminal:

>javac *.java 

>java Oblig1 1
will run program 1.

>java Oblig1 2
will run program 2.

>java Oblig1 3
will run program 3. 

>java Oblig1 4
will run program 4. 

>java Oblig1 all
will run all 4 programs.
