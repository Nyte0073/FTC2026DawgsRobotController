package org.firstinspires.ftc.teamcode.subsystems.driveables.tank;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public final class TankDrive extends Tank {

    private final TankImpl tank;

    public TankDrive(Supplier<Double> driverYSupplier, Supplier<Double> driverXSupplier, Telemetry telemetry, Motor leftMotor, Motor rightMotor, IMU imu) {
        super(driverYSupplier, driverXSupplier, telemetry);
        tank = new TankImpl(leftMotor, rightMotor, imu);
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
    public void calculateMotorPowersAndDrive(double x, double y) {
        tank.driveWithMotorPowers(y, x);
    }

    @Override
    public IMU getIMU() {
        return tank.imu;
    }

    @Override
    public void invertLeftSideEncoders(boolean inverted) {
        if(inverted) {
            tank.leftMotor.encoder.setDirection(Motor.Direction.REVERSE);
        } else {
            tank.leftMotor.encoder.setDirection(Motor.Direction.FORWARD);
        }
    }

    @Override
    public void invertRightSideEncoders(boolean inverted) {
        if(inverted) {
           tank.rightMotor.encoder.setDirection(Motor.Direction.REVERSE);
        } else {
            tank.rightMotor.encoder.setDirection(Motor.Direction.FORWARD);
        }
    }

    @Override
    public void invertLeftSideMotors(boolean inverted) {
        if(inverted) {
            tank.leftMotor.setInverted(true);
        } else {
            tank.leftMotor.setInverted(false);
        }
    }

    @Override
    public void invertRightSideMotors(boolean inverted) {
        if(inverted) {
            tank.rightMotor.setInverted(true);
        } else {
            tank.leftMotor.setInverted(false);
        }
    }

    @Override
    public void resetEncoders() {
        tank.leftMotor.resetEncoder();
        tank.rightMotor.resetEncoder();
    }

    @Override
    public void setZeroPowerBehavior(Motor.ZeroPowerBehavior zeroPowerBehavior) {
        tank.leftMotor.setZeroPowerBehavior(zeroPowerBehavior);
        tank.rightMotor.setZeroPowerBehavior(zeroPowerBehavior);
    }

    @Override
    public List<Motor> getMotors() {
        return List.of(tank.leftMotor, tank.rightMotor);
    }
}
