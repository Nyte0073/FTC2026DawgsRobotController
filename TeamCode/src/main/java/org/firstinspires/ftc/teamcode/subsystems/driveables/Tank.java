package org.firstinspires.ftc.teamcode.subsystems.driveables;

import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.TankConstants.leftMotor;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.TankConstants.rightMotor;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.Supplier;

public abstract class Tank extends SubsystemBase implements Driveable {

    private final Supplier<Double> driverYSupplier, driverXSupplier;
    private final Telemetry telemetry;

    public Tank(Supplier<Double> driverYSupplier, Supplier<Double> driverXSupplier, Telemetry telemetry) {
        this.driverYSupplier = driverYSupplier;
        this.driverXSupplier = driverXSupplier;
        this.telemetry = telemetry;
    }

    @Override
    public void drive() {
        double y = driverYSupplier.get();
        double x = driverXSupplier.get();
        double[] motorPowers = calculateMotorPowers(y, x);
        leftMotor.set(motorPowers[0]);
        Constants.TankConstants.rightMotor.set(motorPowers[1]);
        periodic();
    }

    @Override
    public void stop() {
        stopMotors();
        stopThreads();
    }

    @Override
    public void periodic() {
        telemetry.addData("Left Motor Power", leftMotor.get());
        telemetry.addData("Right Motor Power", rightMotor.get());
        telemetry.update();
    }

    public abstract void stopMotors();
    public abstract void stopThreads();
    public abstract double[] calculateMotorPowers(double y, double x);
}
