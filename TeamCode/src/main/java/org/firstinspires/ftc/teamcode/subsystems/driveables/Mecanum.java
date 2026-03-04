package org.firstinspires.ftc.teamcode.subsystems.driveables;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.Supplier;

public abstract class Mecanum extends SubsystemBase implements Driveable {

    private final Supplier<Vector> driverVectorSupplier;
    private final Supplier<Double> currentRobotOrientationSupplier;
    private final Telemetry telemetry;

    public Mecanum(Supplier<Vector> driverVectorSupplier, Supplier<Double> currentRobotOrientationSupplier, Telemetry telemetry) {
        this.driverVectorSupplier = driverVectorSupplier;
        this.currentRobotOrientationSupplier = currentRobotOrientationSupplier;
        this.telemetry = telemetry;
    }

    @Override
    public void periodic() {
        Vector driverVector = driverVectorSupplier.get();
        telemetry.addData("Current Robot Orientation: ", currentRobotOrientationSupplier.get());
        telemetry.addData("X: ", driverVector.getX());
        telemetry.addData("Y: ", driverVector.getY());
    }

    @Override
    public void drive() {
        Vector driverVector = driverVectorSupplier.get();
        calculateMotorPowers(currentRobotOrientationSupplier.get(), driverVector);
    }

    @Override
    public void stop() {
        stopMotors();
        stopThreads();
    }

    public abstract void stopMotors();
    public abstract void stopThreads();
    public abstract void calculateMotorPowers(double originalRobotAngle, Vector driverVector);
}
