package pl.com.bottega.inventory.robot;

public class East extends RobotState {

    public East(Robot robot) {
        super(robot);
    }

    @Override
    void move() {
        Position position = robot.getPosition();
        position.setX(position.getX() + 1);
        robot.setPosition(position);
    }

    @Override
    void rotate() {
        robot.setRobotState(new South(robot));
    }
}
