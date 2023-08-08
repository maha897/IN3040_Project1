import java.util.ArrayList;

public interface Robol{
    public void interpret(Robot robot);
}

class Program implements Robol{
    Grid grid;
    Robot robot;

    public Program(Grid grid, Robot robot){
        this.grid = grid;
        this.robot = robot;
    }

    @Override
    public void interpret(Robot robot){
        robot.interpret(robot);
    }
}

class Grid{
    NumberExp size;
    NumberExp dimX;
    NumberExp dimY;

    Grid(NumberExp dimX, NumberExp dimY){
        size = new NumberExp(dimX.number*dimY.number);
        this.dimX = dimX;
        this.dimY = dimY;
    }
}

class Robot implements Robol{
    Start start;
    ArrayList <Statement> statements;
    Current current;
    Grid grid;
    int dir = 0;

    Robot(Start start, ArrayList <Statement> statements, Grid grid){
        this.start = start;
        this.statements = statements;
        this.grid = grid;
        current = new Current(start.X.number, start.Y.number);
    }

    @Override
    public void interpret(Robot robot) throws RobotOffTheGridError{
        for (Statement statement: statements){
            statement.interpret(robot);
        }
    }

    class Current{
        class Coordinates{
            int X;
            int Y;
    
            Coordinates(int X, int Y){
                this.X = X;
                this.Y = Y;
            }

            void stepInDirection(int degrees, int numbSteps){
                X += numbSteps*Math.round(Math.cos(((float) degrees)/180*Math.PI));
                Y -= numbSteps*Math.round(Math.sin(((float) degrees)/180*Math.PI));
            }

            public String toString(){
                return "("+X+","+Y+")";
            }
        }

        Coordinates coordinates;
    
        Current(int X, int Y){
            coordinates = new Coordinates(X, Y);
        }

        void stepInDirection(int degrees, int numbSteps){
            coordinates.stepInDirection(degrees, numbSteps);
            if (current.coordinates.X > grid.dimX.number || current.coordinates.X < 0 ||
                    current.coordinates.Y > grid.dimY.number || current.coordinates.Y < 0){
                throw new RobotOffTheGridError(current.coordinates.X, current.coordinates.Y);
            }
        }

        public String toString(){
            return coordinates.toString();
        }
    }
}

class Binding{
    Binding(Identifier variable, Expression value){
        variable.value = value.evaluate();
    }
}

class Start{
    NumberExp X;
    NumberExp Y;

    Start(NumberExp X, NumberExp Y){
        this.X = X;
        this.Y = Y;
    }
}

abstract class Statement implements Robol{
    public abstract void interpret(Robot robot);
}

class Stop extends Statement{
    @Override
    public void interpret(Robot robot) {
        System.out.println("\nStopped on " + robot.current.coordinates + "\n");
    }
}

class Turn extends Statement{
    Direction direction;

    Turn(Direction direction){
        this.direction = direction;
    }

    @Override
    public void interpret(Robot robot){
        switch(direction){
            case clockwise:
                robot.dir += 90;
                System.out.println("Turning "+direction);
                break;
            case counterclockwise:
                robot.dir -= 90;
                System.out.println("Turning "+direction);
                break;
        }
    }
}

class Step extends Statement{
    Expression steps;

    public Step(Expression n){
        steps = n;
    }

    @Override
    public void interpret(Robot robot){
        robot.current.stepInDirection(robot.dir, steps.evaluate());
        System.out.println("Moved to " + robot.current);
    }
}

class Assignment extends Statement{
    Identifier exp;
    AssignEnum assign;

    Assignment(Identifier exp, AssignEnum assign){
        this.exp = exp;
        this.assign = assign;
    }

    @Override
    public void interpret(Robot robot){
        switch (assign){
            case INCR:
                exp.value++;
                break;
            case DECR:
                exp.value--;
                break;       
        }
    }
}

class Loop extends Statement{
    BoolExp condition;
    ArrayList<Statement> statements;

    Loop(BoolExp condition, ArrayList<Statement> statements){
        this.condition = condition;
        this.statements = statements;
    }
    
    @Override
    public void interpret(Robot robot){
        while (condition.evaluate() == 1){
            for (Statement statement: statements){
                statement.interpret(robot);
            }
        }
    }
}

abstract class Expression{
    abstract int evaluate();
}

class Identifier extends Expression{
    int value;

    @Override
    int evaluate() {
        return value;
    }
}

class NumberExp extends Expression{
    int number;

    NumberExp(int n){
        number = n;
    }

    @Override
    int evaluate() {
        return number;
    }
}

class ArithmeticExp extends Expression{
    BinaryOperation binOp;
    Expression exp1, exp2;

    ArithmeticExp(BinaryOperation binOp, Expression exp1, Expression exp2){
        this.binOp = binOp;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    int evaluate(){
        switch (binOp){
            case ADD:
                return exp1.evaluate() + exp2.evaluate();
            case SUB:
                return exp1.evaluate() - exp2.evaluate();
            case MUL:
                return exp1.evaluate()*exp2.evaluate();
            case LESS:
                if (exp1.evaluate() < exp2.evaluate()) return 1;
                else return 0;
            case GREATER:
                if (exp1.evaluate() > exp2.evaluate()) return 1;
                else return 0;
            case EQ:
                if (exp1.evaluate() == exp2.evaluate()) return 1;
                else return 0;
            default :
                return 0;
        }
    }
}

class BoolExp extends ArithmeticExp{
    BoolExp(BinaryOperation binOp, Expression exp1, Expression exp2){
        super(binOp, exp1, exp2);
    }
}