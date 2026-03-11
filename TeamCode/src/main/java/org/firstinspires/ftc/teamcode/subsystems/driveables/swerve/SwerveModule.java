package org.firstinspires.ftc.teamcode.subsystems.driveables.swerve;

import android.util.Log;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.subsystem_math.SwerveMath;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

/**Class representing a drive motor-turn motor pair and the calculations necessary in order to
 * make module travel in the right direction to make the robot translate and/or rotate to where it wants.*/
public class SwerveModule {

    public final Motor rotatingMotor, drivingMotor;
    public final Vector positionVector;

    public SwerveModule(Motor rotatingMotor, Motor drivingMotor, Vector positionVector) {
        this.rotatingMotor = rotatingMotor;
        this.drivingMotor = drivingMotor;
        this.rotatingMotor.setDistancePerPulse(Constants.SwerveConstants.swerveDistancePerPulse);
        this.positionVector = positionVector.deepCopy();
    }

    public int getCurrentModuleHeading() {
        double position = rotatingMotor.getDistance();
        int angle = (int) (position * 360 / rotatingMotor.getCPR());
        return (angle % 360 + 360) % 360;
    }

    public Vector calculateTranslatedAndRotatedMotorVector(boolean rotating, boolean clockwise, Vector driverVector, double rotationPower) {
        Vector positionVector = new Vector(0, 0, 0);
        if(rotating && clockwise) {
            positionVector = Vector.rotate90DegreesClockwise.apply(this.positionVector.getX(), this.positionVector.getY()).times(rotationPower);
        } else if(rotating) {
            positionVector = Vector.rotate90DegreesCounterclockwise.apply(this.positionVector.getX(), this.positionVector.getY()).times(rotationPower);
        }
        return driverVector.plus(positionVector.times(0));
    }

    public void applyTransAndRotVectorToMotor(Vector translatedAndRotatedVector, int currentRobotOrientation) {
        int angle = translatedAndRotatedVector.getMagnitude() == 0 ? 0 : (int) (Math.toDegrees(Math.atan2(
                translatedAndRotatedVector.getY(), translatedAndRotatedVector.getX()
        )) - 90);
        int currentMotorPosition = getCurrentModuleHeading();
        int absoluteHeading = currentMotorPosition + currentRobotOrientation;
        double normalizedHeading = SwerveMath.normalizeHeading(angle, absoluteHeading);
        double finalNormalizedHeading = SwerveMath.reverseHeading(normalizedHeading, absoluteHeading, currentMotorPosition + normalizedHeading);
        rotatingMotor.setTargetDistance(finalNormalizedHeading);
        Log.i(getClass().getSimpleName(), "Final Normalized Heading: " + finalNormalizedHeading);
        Log.i(getClass().getSimpleName(), "Translated and Rotated Vector Magnitude: " + translatedAndRotatedVector.getMagnitude());
//        rotatingMotor.set(0.1);
//        drivingMotor.set(Math.min(1, translatedAndRotatedVector.getMagnitude()));
    }
}
