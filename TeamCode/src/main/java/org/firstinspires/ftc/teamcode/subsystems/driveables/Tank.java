package org.firstinspires.ftc.teamcode.subsystems.driveables;

import com.arcrobotics.ftclib.command.SubsystemBase;

import java.util.function.Supplier;

public abstract class Tank extends SubsystemBase implements Driveable {

    private final Supplier<Double> driverYSupplier, driverXSupplier;


    public Tank(Supplier<Double> driverYSupplier, Supplier<Double> driverXSupplier) {
        this.driverYSupplier = driverYSupplier;
        this.driverXSupplier = driverXSupplier;
    }

    @Override
    public void drive() {
        double y = driverYSupplier.get();
        double x = driverXSupplier.get();
        double[] motorPowers = calculateMotorPowers(y, x);
        Constants.TankConstants.leftMotor.set(motorPowers[0]);
        Constants.TankConstants.rightMotor.set(motorPowers[1]);
    }

    @Override
    public void stop() {
        stopMotors();
        stopThreads();
    }

    @Override
    public void periodic() {
        super.periodic();
    }

    public abstract void stopMotors();
    public abstract void stopThreads();
    public abstract double[] calculateMotorPowers(double y, double x);
}
