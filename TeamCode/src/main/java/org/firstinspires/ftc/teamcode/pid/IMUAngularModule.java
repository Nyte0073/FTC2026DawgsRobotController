package org.firstinspires.ftc.teamcode.pid;

import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.telemetry;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.subsystem_math.SwerveMath;

/**Class for making the entire robot rotate to a specified angle with PID adjustments based on the error between the
 * current and target angle.*/
public class IMUAngularModule implements PIDModule {

    /**Controller used for calculating the output to multiply the motor's power by error based on the motor's current position
     * and the motor's target position.*/
    private final PIDController controller;

    /**Reference to the gyroscope system that returns the current orientation relative to what is considered 0 degrees.*/
    private final IMU imu;

    /**The target of the of the controller producing motor error output based on angular error reported from the robot's imu system.*/
    private double target = 0;

    /**Reference to the motor that this PIDController is sending error output to.*/
    private final Motor motor;

    public IMUAngularModule(IMU imu, double kP, double kS, double kD, Motor motor) {
        controller = new PIDController(kP, kS, kD);
        controller.reset();
        this.imu = imu;
        this.imu.resetYaw();
        this.motor = motor;
    }

    @Override
    public double getTarget() {
        return target;
    }

    @Override
    public void update() {
        telemetry.addData("Target", getTarget());
        telemetry.addData("Position Error", getError());
        telemetry.addData("At target", atTarget());
        telemetry.update();
    }

    @Override
    public double calculate() {
        double error = SwerveMath.normalizeHeading(target, getDistance());
        return Math.abs(controller.calculate(error, target));
    }

    @Override
    public double getDistance() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    @Override
    public void setTarget(double target) {
        this.target = SwerveMath.normalizeHeading(target, 0);
    }

    @Override
    public boolean atTarget() {
        return Math.abs(controller.getPositionError()) < 0.05;
    }

    @Override
    public Motor getMotor() {
        return motor;
    }

    @Override
    public double getError() {
        return SwerveMath.normalizeHeading(controller.getPositionError(), 0);
    }

    @Override
    public Telemetry getTelemetry() {
        return telemetry;
    }
}
