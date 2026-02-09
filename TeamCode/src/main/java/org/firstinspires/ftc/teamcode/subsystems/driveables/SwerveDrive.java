package org.firstinspires.ftc.teamcode.subsystems.driveables;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SwerveDrive extends Swerve {

    public SwerveDrive(Supplier<Vector> driverVectorSupplier, Supplier<Double> currentOrientationSupplier) {
        super(driverVectorSupplier, currentOrientationSupplier);
    }

    @Override
    public void periodic() {
        System.out.println("Something is right with this code.");
    }

    @Override
    public Map<Motor, Double> calculateSwerveHeadings(Vector driverVector, double originalPose, boolean rotating, boolean clockwise) {
        Map<Motor, Double> returnedMap = new HashMap<>();
        for(Motor turningMotor : Constants.turningMotors) {
            double currentPosition = turningMotor.getCurrentPosition();
            double absolutePosition = originalPose + currentPosition;
            Vector rotatedPositionVector, translatedAndRotatedVector;
            Vector positionVector = Vector.motorsToVectorPositions.get(turningMotor);
            if(rotating) {
                if(clockwise) {
                    rotatedPositionVector = Vector.rotate90DegreesClockwise.apply(positionVector.getX(), positionVector.getY()).times(driverVector.getZ());
                } else {
                    rotatedPositionVector = Vector.rotate90DegreesCounterclockwise.apply(positionVector.getX(), positionVector.getY()).times(driverVector.getZ());
                }
            } else {
                rotatedPositionVector = new Vector(0, 0, 0);
            }
            translatedAndRotatedVector = driverVector.plus(rotatedPositionVector);
            Constants.motorsAndTheirRotatedAndTranslatedVectors.put(turningMotor, translatedAndRotatedVector);
            double targetAngle = Math.toDegrees(Math.atan2(translatedAndRotatedVector.getY(), translatedAndRotatedVector.getX())) - 90;
            double normalizedHeading = normalizeHeading(targetAngle, absolutePosition);
            double totalHeading = currentPosition + normalizedHeading;
            double reversedHeading = reverseHeading(targetAngle, totalHeading, currentPosition);
            returnedMap.put(turningMotor, reversedHeading);
        }
        return returnedMap;
    }

    @Override
    public void stopMotors() {

    }
}
