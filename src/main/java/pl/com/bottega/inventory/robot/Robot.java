package pl.com.bottega.inventory.robot;

public class Robot {
    private RobotState robotState;
    private Position position = new Position(0, 0);

    public Robot() {
        this.robotState = new North(this);
    }

    public void move() {
        robotState.move();
    }

    public void rotate() {
        robotState.rotate();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setRobotState(RobotState robotState) {
        this.robotState = robotState;
    }
}
