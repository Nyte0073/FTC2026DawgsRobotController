package org.firstinspires.ftc.teamcode.subsystems.driveables;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.Supplier;

public final class MecanumDrive extends Mecanum {

    /**Reference to the four wheels of the mecanum drive.*/
    private final com.arcrobotics.ftclib.drivebase.MecanumDrive mecanumDrive;

    /**Constructs a new {@code MecanumDrive()} with initialized {@code driverVector} Supplier, {@code currentRobotOrientation} Supplier,
     * and */
    public MecanumDrive(Supplier<Vector> driverVectorSupplier, Supplier<Double> currentRobotOrientationSupplier, Motor[] motors, Telemetry telemetry) {
        super(driverVectorSupplier, currentRobotOrientationSupplier, telemetry);
        mecanumDrive = new com.arcrobotics.ftclib.drivebase.MecanumDrive(motors[0], motors[1], motors[2], motors[3]);
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
    public void calculateMotorPowers(double originalRobotAngle, Vector driverVector) {
        double x = driverVector.getX();
        double y = driverVector.getY();
        double z = driverVector.getZ();
        mecanumDrive.driveFieldCentric(x, y, z, originalRobotAngle);
    }
}
