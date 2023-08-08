import java.util.ArrayList;

public class TestCode {
    void runProgram1(){
        System.out.println("======================================");
        System.out.println("    Running program 1");
        System.out.println("======================================\n");

        Grid grid = new Grid(new NumberExp(64), new NumberExp(64));
        Start start = new Start(new NumberExp(23), new NumberExp(30));
        ArrayList <Statement> statements = new ArrayList<>();

        statements.add(new Turn(Direction.clockwise));
        statements.add(new Turn(Direction.clockwise));
        statements.add(new Step(new NumberExp(15)));
        statements.add(new Turn(Direction.counterclockwise));
        statements.add(new Step(new NumberExp(15)));
        statements.add(new Turn(Direction.counterclockwise));
        statements.add(new Step(new ArithmeticExp(BinaryOperation.ADD, new NumberExp(2), new NumberExp(3))));
        statements.add(new Turn(Direction.counterclockwise));
        statements.add(new Step(new ArithmeticExp(BinaryOperation.ADD, new NumberExp(17), new NumberExp(20))));
        statements.add(new Stop());

        Robot robot = new Robot(start, statements, grid);
        Program program = new Program(grid, robot);
        program.interpret(robot);

        // Result should be (13,52)
    }

    void runProgram2(){
        System.out.println("======================================");
        System.out.println("    Running program 2");
        System.out.println("======================================\n");
    
        Grid grid = new Grid(new NumberExp(64), new NumberExp(64));
        Start start = new Start(new NumberExp(23), new NumberExp(6));
        ArrayList <Statement> statements = new ArrayList<>();

        Identifier i = new Identifier();
        Identifier j = new Identifier();
        new Binding(i, new NumberExp(5));
        new Binding(j, new NumberExp(3));

        statements.add(new Turn(Direction.counterclockwise));
        statements.add(new Step(new ArithmeticExp(BinaryOperation.MUL, new NumberExp(3), i))); 
        statements.add(new Turn(Direction.clockwise));
        statements.add(new Step(new NumberExp(15)));
        statements.add(new Turn(Direction.clockwise));
        statements.add(new Step(new ArithmeticExp(BinaryOperation.SUB, new ArithmeticExp(BinaryOperation.SUB, new NumberExp(12), i), j)));
        statements.add(new Turn(Direction.clockwise));
        statements.add(new Step(new ArithmeticExp(BinaryOperation.ADD, new ArithmeticExp(BinaryOperation.ADD, new ArithmeticExp(BinaryOperation.MUL, new NumberExp(2), i), new ArithmeticExp(BinaryOperation.MUL, new NumberExp(3), j)), new NumberExp(1))));
        statements.add(new Stop());

        Robot robot = new Robot(start, statements, grid);
        Program program = new Program(grid, robot);
        program.interpret(robot);

        // Result should be (18,17)
    }

    void runProgram3(){
        System.out.println("======================================");
        System.out.println("    Running program 3");
        System.out.println("======================================\n");
    
        Grid grid = new Grid(new NumberExp(64), new NumberExp(64));
        Start start = new Start(new NumberExp(23), new NumberExp(6));
        ArrayList <Statement> statements = new ArrayList<>();
        
        Identifier i = new Identifier();
        Identifier j = new Identifier();
        new Binding(i, new NumberExp(5));
        new Binding(j, new NumberExp(3));

        statements.add(new Turn(Direction.counterclockwise));
        statements.add(new Step(new ArithmeticExp(BinaryOperation.MUL, new NumberExp(3), i)));
        statements.add(new Turn(Direction.counterclockwise));
        statements.add(new Step(new NumberExp(15)));
        statements.add(new Turn(Direction.clockwise));
        statements.add(new Turn(Direction.clockwise));
        statements.add(new Step(new NumberExp(4)));
        statements.add(new Turn(Direction.clockwise));
        
        ArrayList<Statement> loopStatements = new ArrayList<>();
        loopStatements.add(new Step(j));
        loopStatements.add(new Assignment(j, AssignEnum.DECR));
        statements.add(new Loop(new BoolExp(BinaryOperation.GREATER, j, new NumberExp(1)), loopStatements));
        statements.add(new Stop());

        Robot robot = new Robot(start, statements, grid);
        Program program = new Program(grid, robot);
        program.interpret(robot);

        // Result should be (12,16)
    }

    void runProgram4(){
        System.out.println("======================================");
        System.out.println("    Running program 4");
        System.out.println("======================================\n");

        Grid grid = new Grid(new NumberExp(64), new NumberExp(64));
        Start start = new Start(new NumberExp(1), new NumberExp(1));
        ArrayList <Statement> statements = new ArrayList<>();

        Identifier i = new Identifier();
        new Binding(i, new NumberExp(8));

        ArrayList<Statement> loopStatements = new ArrayList<>();
        loopStatements.add(new Step(i));
        statements.add(new Loop(new BoolExp(BinaryOperation.LESS, i, new NumberExp(100)), loopStatements));
        statements.add(new Stop());

        Robot robot = new Robot(start, statements, grid);
        Program program = new Program(grid, robot);
        program.interpret(robot);

        // Result should be an error
    }

    void runAll(){
        runProgram1();
        runProgram2();
        runProgram3();
        runProgram4();
    }
}
