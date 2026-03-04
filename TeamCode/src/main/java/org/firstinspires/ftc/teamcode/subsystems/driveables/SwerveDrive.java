package org.firstinspires.ftc.teamcode.subsystems.driveables;

import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.deadZoneTolerance;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.degreePercentageToDegrees;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.ticksPerRev;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class SwerveDrive extends Swerve {

    public SwerveDrive(Supplier<Vector> driverVectorSupplier, Supplier<Double> currentOrientationSupplier, Telemetry telemetry) {
        super(driverVectorSupplier, currentOrientationSupplier, telemetry);
    }

    @Override
    public void periodic() {
        System.out.println("Something is right with this code.");
    }

    @Override
    public Map<Motor, Double> calculateSwerveHeadings(Vector driverVector, double originalPose, boolean rotating, boolean clockwise) {
        if(Math.abs(driverVector.getY()) <= deadZoneTolerance && Math.abs(driverVector.getX()) <= deadZoneTolerance && Math.abs(driverVector.getZ()) <= deadZoneTolerance) {
            driverVector = new Vector(0, 0, 0);
        }
        Map<Motor, Double> returnedMap = new HashMap<>();
        for(Motor turningMotor : Constants.SwerveConstants.turningMotors) {
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
            Constants.SwerveConstants.motorsAndTheirRotatedAndTranslatedVectors.put(turningMotor, translatedAndRotatedVector);
            double targetAngle = driverVector.getMagnitude() <= 0.03 ? (double) turningMotor.getCurrentPosition() / ticksPerRev * degreePercentageToDegrees :
                    Math.toDegrees(Math.atan2(translatedAndRotatedVector.getY(), translatedAndRotatedVector.getX())) - 90;
            double normalizedHeading = normalizeHeading(targetAngle, absolutePosition);
            double totalHeading = currentPosition + normalizedHeading;
            double reversedHeading = reverseHeading(targetAngle, totalHeading, currentPosition);
            returnedMap.put(turningMotor, reversedHeading);
        }
        return returnedMap;
    }

    @Override
    public void stopMotors() {
        System.out.println("Stopping motors.");
    }
}
