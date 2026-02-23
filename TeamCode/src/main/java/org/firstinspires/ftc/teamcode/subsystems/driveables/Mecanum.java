package org.firstinspires.ftc.teamcode.subsystems.driveables;

import com.arcrobotics.ftclib.command.SubsystemBase;
import java.util.function.Supplier;

public abstract class Mecanum extends SubsystemBase implements Driveable {

    private final Supplier<Vector> driverVectorSupplier;
    private final Supplier<Double> currentRobotOrientationSupplier;

    public Mecanum(Supplier<Vector> driverVectorSupplier, Supplier<Double> currentRobotOrientationSupplier) {
        this.driverVectorSupplier = driverVectorSupplier;
        this.currentRobotOrientationSupplier = currentRobotOrientationSupplier;
    }

    @Override
    public void periodic() {
        super.periodic();
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
