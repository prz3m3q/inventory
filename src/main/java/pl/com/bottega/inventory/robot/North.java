package pl.com.bottega.inventory.robot;

public class North extends RobotState {

    public North(Robot robot) {
        super(robot);
    }

    @Override
    void move() {
        Position position = robot.getPosition();
        position.setY(position.getY() + 1);
        robot.setPosition(position);
    }

    @Override
    void rotate() {
        robot.setRobotState(new East(robot));
    }
}
