package org.firstinspires.ftc.teamcode.subsystems.driveables.mecanum;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

import java.util.function.Supplier;

public final class MecanumDrive extends Mecanum {

    /**Reference to the four wheels of the mecanum drive.*/
    private final com.arcrobotics.ftclib.drivebase.MecanumDrive mecanumDrive;

    private final IMU imu;

    public MecanumDrive(Supplier<Vector> driverVectorSupplier, Supplier<Double> currentRobotOrientationSupplier, Motor[] motors, Telemetry telemetry, IMU imu) {
        super(driverVectorSupplier, currentRobotOrientationSupplier, telemetry);
        mecanumDrive = new com.arcrobotics.ftclib.drivebase.MecanumDrive(motors[0], motors[1], motors[2], motors[3]);
        this.imu = imu;
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
        mecanumDrive.driveFieldCentric(x, y, z, originalRobotAngle, true);
    }

    @Override
    public void switchActions(GamepadKeys.Button button) {
        switch(button) {
            case DPAD_DOWN:
                imu.resetYaw();
                break;

            case DPAD_UP:
                stopMotors();
                break;
        }
    }
}
