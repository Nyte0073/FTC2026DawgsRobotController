package org.firstinspires.ftc.teamcode.subsystems.driveables;

import static org.firstinspires.ftc.teamcode.subsystem_math.TankMath.calculateMotorPower;
import static org.firstinspires.ftc.teamcode.subsystem_math.TankMath.clamp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.Supplier;

/***/
public final class TankDrive extends Tank {

    public TankDrive(Supplier<Double> driverYSupplier, Supplier<Double> driverXSupplier, Telemetry telemetry) {
        super(driverYSupplier, driverXSupplier, telemetry);
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
        double[] motorPowers = calculateMotorPower(x, y);
        return clamp(motorPowers[0], motorPowers[1]);
    }
}
