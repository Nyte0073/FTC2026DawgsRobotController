package org.firstinspires.ftc.teamcode.subsystems.driveables;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import java.util.Map;
import java.util.function.Supplier;

public abstract class Swerve extends SubsystemBase implements Driveable {

    private final Supplier<Vector> driverVectorSupplier;
    private final Supplier<Double> currentIMUOrientationSupplier;

    public Swerve(Supplier<Vector> driverVectorSupplier, Supplier<Double> currentIMUOrientationSupplier) {
        this.driverVectorSupplier = driverVectorSupplier;
        this.currentIMUOrientationSupplier = currentIMUOrientationSupplier;
    }

    @Override
    public void drive() {
        Vector driverVector = driverVectorSupplier.get();
        double currentIMUOrientation = currentIMUOrientationSupplier.get();
        boolean rotating = driverVector.getZ() >= 0.1;
        Map<Motor, Double> calculatedWheelHeadings = calculateSwerveHeadings(driverVector, currentIMUOrientation, rotating, true);
        double magnitudeDivisor = Constants.motorsAndTheirRotatedAndTranslatedVectors.values().stream().mapToDouble(Vector::getMagnitude).max().orElse(1);
        for(Motor motor : Constants.turningMotors) {
            double calculatedWheelHeading = calculatedWheelHeadings.get(motor);
            motor.setTargetPosition((int) ((calculatedWheelHeading / 360) * 1440));
            motor.set(0.7);
        }
        for(Map.Entry<Motor, Motor> entry : Constants.turningDrivingMotorsMap.entrySet()) {
            Motor turningMotor = entry.getKey();
            Motor drivingMotor = entry.getValue();
            Vector translatedAndRotatedVector = Constants.motorsAndTheirRotatedAndTranslatedVectors.get(turningMotor);
            if (magnitudeDivisor > 1) {
                drivingMotor.set(translatedAndRotatedVector.getMagnitude() / magnitudeDivisor);
            } else {
                drivingMotor.set(translatedAndRotatedVector.getMagnitude());
            }
        }
    }

    @Override
    public void stop() {
        stopMotors();
    }
    public double normalizeHeading(double targetPos, double currentPos) {
        return (targetPos - currentPos + 540) % 360 - 180;
    }
    public double reverseHeading(double targetPos, double totalPos, double originalPos) {
        return Math.abs(totalPos) > 180 ? normalizeHeading(
                totalPos < 0 ? totalPos + 180 : totalPos - 180, originalPos
        ) : targetPos;
    }
    public abstract Map<Motor, Double> calculateSwerveHeadings(Vector driverVector, double originalPose, boolean rotating, boolean clockwise);
    public abstract void stopMotors();
}
