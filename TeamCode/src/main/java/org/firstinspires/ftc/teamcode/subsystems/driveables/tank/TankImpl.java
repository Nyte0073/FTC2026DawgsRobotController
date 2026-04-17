package org.firstinspires.ftc.teamcode.subsystems.driveables.tank;

import static org.firstinspires.ftc.teamcode.subsystem_math.TankMath.clamp;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.subsystem_math.TankMath;

public class TankImpl {
    public final Motor leftMotor, rightMotor;
    public final IMU imu;

    public TankImpl(Motor leftMotor, Motor rightMotor, IMU imu) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        this.imu = imu;
    }

    public void driveWithMotorPowers(double y, double x) {
        double[] motorPowers = TankMath.calculateMotorPower(x, y);
        motorPowers = clamp(motorPowers[0], motorPowers[1]);
        leftMotor.set(motorPowers[0]);
        rightMotor.set(motorPowers[1]);
    }
}
