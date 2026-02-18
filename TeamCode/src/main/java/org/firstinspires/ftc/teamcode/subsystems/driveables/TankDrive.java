package org.firstinspires.ftc.teamcode.subsystems.driveables;

import java.util.function.Supplier;

public class TankDrive extends Tank {

    public TankDrive(Supplier<Double> driverYSupplier, Supplier<Double> driverXSupplier) {
        super(driverYSupplier, driverXSupplier);
    }

    @Override
    public void stopMotors() {
        System.out.println("Stopping motors.");
    }

    @Override
    public void stopThreads() {
        System.out.println("Stopping threads.");
    }

    @Override
    public double[] calculateMotorPowers(double y, double x) {
       return clamp(y + x, y - x);
    }

    public double[] clamp(double v1, double v2) {
        double biggerNumber = Math.max(1, Math.max(Math.abs(v1), Math.abs(v2)));
        return new double[] {v1 / biggerNumber, v2 / biggerNumber};
    }
}
