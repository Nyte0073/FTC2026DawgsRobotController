package org.firstinspires.ftc.teamcode.subsystem_math;

import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.deadZoneTolerance;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.degreePercentageToDegrees;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.ticksPerRev;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

/**Class that contains all static utility methods for conducting all the calculations that need to happen
 * in order for the swerve drive to set specific powers to its turning and driving motors. Import this class's static methods
 * to be able to perform standard swerve kinematics calculations in your drive control code.*/
public final class SwerveMath {

    /**Calculates the shortest angular difference between the target and current orientation. This method is typically
     * used for determining how much a turning motor should rotate to get to a target as efficiently and as quickly as possible.
     *
     * @return The shortest angular difference between the target and current orientation (in degrees).*/
    public static double normalizeHeading(double target, double current) {
        return (target - current + 540) % 360 - 180;
    }

    /**Calculates whether the values returned by the inputted driver vector are too small to actually use in calculating
     * angles for the turning motors to travel to and for calculating powers to set the drive motors too.
     * If the x, y and z values on the driver vector are too small, the driving motor magnitudes will be close to zero, but because
     * those are still positive numbers greater than zero, the {@code calculateSwerveHeadings()} method will still calculate an angular difference
     * between the current orientation of the turning motors and its calculated target position. Thus, the robot will always try to move to an infinitely small
     * intervaled position because of that calculation, which is why this method checks for those very small values. If the method itself returns
     * true, then that means the x, y and z values are all too small to have an effect on driving, and thus other control code can then set the driver
     * vector to 0 before it is used to calculate module turn angles.*/
    public static boolean driverVectorIsTooSmall(Vector driverVector) {
        return Math.abs(driverVector.getX()) <= deadZoneTolerance &&
                Math.abs(driverVector.getY()) <= deadZoneTolerance &&
                Math.abs(driverVector.getZ()) <= deadZoneTolerance;
    }

    /**Calculates the translated and rotated vector that is the result of combining a rotated position vector mapped to any one of the
     * turning motors with the driverVector to create a new vector that is the result of combining rotation and translation together.
     * What this new {@code translatedAndRotatedVector} represents is the magnitude and direction that should be applied to the turning
     * and driving motors in order to travel and/or rotate in a certain direction.*/
    public static Vector calculateTranslatedAndRotatedVector(Vector positionVector, boolean rotating, boolean clockwise, Vector driverVector) {
        Vector rotatedPositionVector, translatedAndRotatedVector;
        if(rotating && clockwise) {
            rotatedPositionVector = Vector.rotate90DegreesClockwise.apply(positionVector.getX(), positionVector.getY()).times(driverVector.getZ());
        } else if(rotating) {
            rotatedPositionVector = Vector.rotate90DegreesCounterclockwise.apply(positionVector.getX(), positionVector.getY()).times(driverVector.getZ());
        } else {
            rotatedPositionVector = new Vector(0, 0, 0);
        }
        translatedAndRotatedVector = driverVector.plus(rotatedPositionVector);
        return translatedAndRotatedVector;
    }

    /**Based off a translated and rotated vector calculated in the {@code calculateTranslatedAndRotatedVector()} method above,
     * this method will then calculate the heading delta that the turning motors must follow when turning to a certain angle that is a certain
     * angular distance away. This method will either calculate the normalized heading based off simply the shortest angular distance between the turning
     * motor and the target position ({@code normalizeHeading()}) */
    public static double calculateFinalNormalizedHeading(Vector driverVector, Vector translatedAndRotatedVector, Motor turningMotor, double currentPosition, double absolutePosition) {
        double targetAngle = driverVector.getMagnitude() <= 0.03 ? (double) turningMotor.getCurrentPosition() / ticksPerRev * degreePercentageToDegrees :
                Math.toDegrees(Math.atan2(translatedAndRotatedVector.getY(), translatedAndRotatedVector.getX())) - 90;
        double normalizedHeading = normalizeHeading(targetAngle, absolutePosition);
        double totalHeading = currentPosition + normalizedHeading;
        return reverseHeading(targetAngle, currentPosition, totalHeading);
    }

    /**Calculates, possibly, the reverse target heading delta for the turning motor to turn to, depending on if the total rotation of
     * the turning motor is beyond an absolute value of 180 degrees. If that is the case, then this method reverse that total heading value
     * to calculate and the angle for the motors to turn to make the robot drive along a specific slope, with reversing the motor power of course,
     * because the turning motor has the right orientation just in the opposite direction. But if the total rotation is less than 180, then
     * no reversing needs to happen this method will just return the target heading as normal.*/
    public static double reverseHeading(double target, double current, double totalPos) {
        return Math.abs(totalPos) > 180 ?  normalizeHeading(totalPos < 0 ? totalPos + 180 : totalPos - 180, current) :
                target;
    }
}
