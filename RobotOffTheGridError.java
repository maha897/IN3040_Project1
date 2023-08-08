public class RobotOffTheGridError extends IndexOutOfBoundsException{
    public RobotOffTheGridError(int x, int y) {
        super("The bounds of the grid have overstepped, (position (" + x + ", " + y + ")).");
    }
}
