package org.firstinspires.ftc.teamcode.subsystem_math;

public final class TankMath {

    /**Normalizes motor power input so that is within the range -1 to 1.
     *
     * @return An array containing the left and right motor power values normalized within the range
     * -1 to 1.*/
    public static double[] clamp(double v1, double v2) {
        double biggerNum = Math.max(1, Math.max(v1, v2));
        return new double[] {v1 / biggerNum, v2 / biggerNum};
    }

    /**Calculates the motor power that will be set directly to the back left and right tank drive motors.
     * This method calculates the motor powers based on what the wheels have to do in order for proper driving to happen.
     * <br><br>
     * For example, when the robot is taking a left turn, the y is going to be close to zero and the x is going to negative,
     * so that means the left drive has to add the negative x to its motor power in order to travel backwards to make the robot
     * rotate counterclockwise. And the opposite for the right drive motor. It has to subtract the negative x to become a
     * positive motor, in order for it to spin in a positive motion to help the robot rotate left, because the direction of
     * rotation for each drive motor has to be opposite to each other.*/
    public static double[] calculateMotorPower(double x, double y) {
        return new double[] {y + x, y - x};
    }
}
