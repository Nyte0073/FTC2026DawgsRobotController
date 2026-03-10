package org.firstinspires.ftc.teamcode.subsystems.driveables.swerve;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.subsystem_math.SwerveMath;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

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
            positionVector = Vector.rotate90DegreesClockwise.apply(this.positionVector.getX(), this.positionVector.getY());
        } else if(rotating) {
            positionVector = Vector.rotate90DegreesCounterclockwise.apply(this.positionVector.getX(), this.positionVector.getY());
        }
        return driverVector.plus(positionVector.times(rotationPower));
    }

    public void applyTransAndRotVectorToMotor(Vector translatedAndRotatedVector, int currentRobotOrientation) {
        int angle = translatedAndRotatedVector.getMagnitude() == 0 ? 0 : (int) (Math.toDegrees(Math.atan2(
                translatedAndRotatedVector.getY(), translatedAndRotatedVector.getX()
        )) - 90);
        int currentMotorPosition = getCurrentModuleHeading();
        int absoluteHeading = currentMotorPosition + currentRobotOrientation;
        double normalizedHeading = SwerveMath.normalizeHeading(angle, absoluteHeading);
        double finalNormalizedHeading = SwerveMath.reverseHeading(angle, absoluteHeading, currentMotorPosition + normalizedHeading);
        rotatingMotor.setTargetDistance(finalNormalizedHeading);
        rotatingMotor.set(0.1);
        drivingMotor.set(Math.min(1, translatedAndRotatedVector.getMagnitude()));
    }
}
