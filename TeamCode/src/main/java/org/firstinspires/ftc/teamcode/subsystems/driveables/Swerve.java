package org.firstinspires.ftc.teamcode.subsystems.driveables;

import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.degreePercentageToDegrees;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.ticksPerRev;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Map;
import java.util.function.Supplier;

public abstract class Swerve extends SubsystemBase implements Driveable {

    private final Supplier<Vector> driverVectorSupplier;
    private final Supplier<Double> currentIMUOrientationSupplier;
    private final Telemetry telemetry;

    public Swerve(Supplier<Vector> driverVectorSupplier, Supplier<Double> currentIMUOrientationSupplier, Telemetry telemetry) {
        this.driverVectorSupplier = driverVectorSupplier;
        this.currentIMUOrientationSupplier = currentIMUOrientationSupplier;
        this.telemetry = telemetry;
    }

    @Override
    public void drive() {
        Vector driverVector = driverVectorSupplier.get();
        double currentIMUOrientation = currentIMUOrientationSupplier.get();
        boolean rotating = driverVector.getZ() >= 0.1;
        Map<Motor, Double> calculatedWheelHeadings = calculateSwerveHeadings(driverVector, currentIMUOrientation, rotating, true);
        double magnitudeDivisor = Math.max(Constants.SwerveConstants.motorsAndTheirRotatedAndTranslatedVectors.values().stream().mapToDouble(Vector::getMagnitude).max().orElse(1), 1);
        for(Motor motor : Constants.SwerveConstants.turningMotors) {
            double currentPositionDegrees = (double) motor.getCurrentPosition() / ticksPerRev * degreePercentageToDegrees;
            double calculatedWheelHeading = calculatedWheelHeadings.get(motor);
            motor.setTargetPosition((int) (((currentPositionDegrees + calculatedWheelHeading) / degreePercentageToDegrees) * ticksPerRev));
            motor.set(0.7);
        }
        if(!Constants.SwerveConstants.drivingMotors.isEmpty()) {
            for(Map.Entry<Motor, Motor> entry : Constants.SwerveConstants.turningDrivingMotorsMap.entrySet()) {
                Motor turningMotor = entry.getKey();
                Motor drivingMotor = entry.getValue();
                Vector translatedAndRotatedVector = Constants.SwerveConstants.motorsAndTheirRotatedAndTranslatedVectors.get(turningMotor);
                if (magnitudeDivisor > 1) {
                    drivingMotor.set(translatedAndRotatedVector.getMagnitude() / magnitudeDivisor);
                } else {
                    drivingMotor.set(translatedAndRotatedVector.getMagnitude());
                }
            }
        }
        periodic();
    }

    @Override
    public void stop() {
        stopMotors();
    }
    public double normalizeHeading(double targetPos, double currentPos) {
        return (targetPos - currentPos + 540) % degreePercentageToDegrees - 180;
    }
    public double reverseHeading(double targetPos, double totalPos, double originalPos) {
        return Math.abs(totalPos) > 180 ? normalizeHeading(
                totalPos < 0 ? totalPos + 180 : totalPos - 180, originalPos
        ) : targetPos;
    }
    public abstract Map<Motor, Double> calculateSwerveHeadings(Vector driverVector, double originalPose, boolean rotating, boolean clockwise);
    public abstract void stopMotors();

    @Override
    public void periodic() {
        telemetry.addData("X: ", driverVectorSupplier.get().getX());
        telemetry.addData("Y: ", driverVectorSupplier.get().getY());
        telemetry.update();
    }
}
