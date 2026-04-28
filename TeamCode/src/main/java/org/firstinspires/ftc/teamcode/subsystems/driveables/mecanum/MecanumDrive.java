package org.firstinspires.ftc.teamcode.subsystems.driveables.mecanum;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;
import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.externalhardwareactions.HardwareAction;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class MecanumDrive extends Mecanum {

    /**Reference to the four wheels of the mecanum drive.*/
    private final com.arcrobotics.ftclib.drivebase.MecanumDrive mecanumDrive;
    private final IMU imu;
    private final List<Motor> motors = new LinkedList<>();
    public final Map<GamepadKeys.Button, HardwareAction> buttonsToRunnable = new LinkedHashMap<>();

    public MecanumDrive(Supplier<Vector> driverVectorSupplier, Supplier<Double> currentRobotOrientationSupplier, LinkedList<Motor> motors, Telemetry telemetry, IMU imu, Map<GamepadKeys.Button, HardwareAction> buttonsToRunnables) {
        super(driverVectorSupplier, currentRobotOrientationSupplier, telemetry);
        mecanumDrive = new com.arcrobotics.ftclib.drivebase.MecanumDrive(motors.get(0), motors.get(1), motors.get(2), motors.get(3));
        this.motors.addAll(motors);
        this.imu = imu;
        this.buttonsToRunnable.putAll(buttonsToRunnables);
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
    public List<Motor> getMotors() {
        return motors;
    }

    @Override
    public IMU getIMU() {
        return imu;
    }

    @Override
    public void invertLeftSideMotors(boolean inverted) {
        if(inverted) {
            motors.get(0).setInverted(true);
            motors.get(2).setInverted(true);
        } else {
            motors.get(0).setInverted(false);
            motors.get(2).setInverted(false);
        }
    }

    @Override
    public void invertRightSideMotors(boolean inverted) {
        if(inverted) {
            motors.get(1).setInverted(true);
            motors.get(3).setInverted(true);
        } else {
            motors.get(1).setInverted(false);
            motors.get(3).setInverted(false);
        }
    }

    @Override
    public void invertLeftSideEncoders(boolean inverted) {
        if(inverted) {
            motors.get(0).encoder.setDirection(Motor.Direction.REVERSE);
            motors.get(2).encoder.setDirection(Motor.Direction.REVERSE);
        } else {
            motors.get(0).encoder.setDirection(Motor.Direction.FORWARD);
            motors.get(2).encoder.setDirection(Motor.Direction.FORWARD);
        }
    }

    @Override
    public void invertRightSideEncoders(boolean inverted) {
        if(inverted) {
            motors.get(1).encoder.setDirection(Motor.Direction.REVERSE);
            motors.get(3).encoder.setDirection(Motor.Direction.REVERSE);
        } else {
            motors.get(1).encoder.setDirection(Motor.Direction.FORWARD);
            motors.get(3).encoder.setDirection(Motor.Direction.FORWARD);
        }
    }

    public void resetEncoders() {
        for(Motor m : motors) {
            m.stopAndResetEncoder();
        }
    }

    public void setZeroPowerBehavior(Motor.ZeroPowerBehavior zeroPowerBehavior) {
        for(Motor m : motors) {
            m.setZeroPowerBehavior(zeroPowerBehavior);
        }
    }
}
