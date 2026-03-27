package org.firstinspires.ftc.teamcode.pid;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;

/**Class to be used when wanting to use a PID system to rotate a motor to specified
 * angle in the range of 0 - 360 degrees. Because the uses 0.25 as the distance per pulse, and 1440 ticks is the amount of
 * ticks per rotation, then that means that getDistance() will return the angle in degrees that the motor is currently facing.
 * Which means that you can set the angle of the motor directly in the {@code setTarget()} method and the PID system will make the robot turn
 * to 360 / 0.25 = 1440 ticks by tracking the distance in terms of the degrees.*/
public class AngularPIDModule implements PIDModule {

    private final Motor motor;
    private final Telemetry telemetry;
    private final PIDController pidController;
    private double target;

    public AngularPIDModule(Telemetry telemetry, Motor motor, double kP, double kS, double kD) {
        this.telemetry = telemetry;
        this.motor = motor;
        this.motor.setDistancePerPulse(Constants.MecanumConstants.distancePerPulseAngle);
        pidController = new PIDController(kP, kS, kD);
        pidController.reset();
    }

    @Override
    public double getTarget() {
        return target;
    }

    @Override
    public void update() {
        telemetry.addData("Distance: ", getDistance());
        telemetry.addData("At set point: ", atTarget());
        telemetry.addData("Target: ", getTarget());
        telemetry.update();
    }

    @Override
    public double calculate() {
        return pidController.calculate(getDistance());
    }

    @Override
    public double getDistance() {
        return motor.getDistance();
    }

    @Override
    public void setTarget(double target) {
        this.target = target;
        pidController.setSetPoint(target);
    }

    @Override
    public boolean atTarget() {
        return Math.abs(pidController.getPositionError()) < 0.05;
    }

    @Override
    public Motor getMotor() {
        return motor;
    }
}
