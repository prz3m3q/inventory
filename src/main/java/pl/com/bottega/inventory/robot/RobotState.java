package pl.com.bottega.inventory.robot;

abstract public class RobotState {
    protected Robot robot;

    public RobotState(Robot robot) {
        this.robot = robot;
    }

    abstract void move();
    abstract void rotate();
}
