package org.firstinspires.ftc.teamcode.subsystems.driveables.swerve;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Driveable;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

import java.util.function.Supplier;

public abstract class Swerve extends SubsystemBase implements Driveable {

    private final Supplier<Vector> driverVectorSupplier;
    private final Supplier<Integer> currentRobotOrientationSupplier;
    private final Telemetry telemetry;

    public Swerve(Supplier<Vector> driverVectorSupplier, Supplier<Integer> currentRobotOrientationSupplier, Telemetry telemetry) {
        this.driverVectorSupplier = driverVectorSupplier;
        this.currentRobotOrientationSupplier = currentRobotOrientationSupplier;
        this.telemetry = telemetry;
    }

    @Override
    public void drive() {
        Vector driverVector = driverVectorSupplier.get();
        int currentRobotOrientation = currentRobotOrientationSupplier.get();
        boolean rotating = driverVector.getX() >= Constants.deadZoneTolerance;
        calculateSwerveModuleHeadingsAndDrive(rotating, true, driverVector, currentRobotOrientation);
    }

    @Override
    public void stop() {
        stopMotors();
        stopThreads();
    }

    @Override
    public void periodic() {
        Vector driverVector = driverVectorSupplier.get();
        telemetry.addData("Driver Vector X: ", driverVector.getX());
        telemetry.addData("Driver Vector Y: ", driverVector.getY());
        telemetry.addData("Driver Vector Z: ", driverVector.getZ());
    }

    public abstract void stopThreads();
    public abstract void stopMotors();
    public abstract void calculateSwerveModuleHeadingsAndDrive(boolean rotating, boolean clockwise, Vector driverVector, int currentRobotOrientation);
}