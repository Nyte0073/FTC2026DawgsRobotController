package org.firstinspires.ftc.teamcode.pid;

import android.util.Log;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;

/**Class to be used when wanting to use a PID system to make a motor travel a certain distance in inches.
 *To convert from inches to ticks, you divide the inches by inches per tick (DPP) to get the ticks, and for inches,
 * since the DPP is 4π / 1440 ≈ 0.00873, then that means you can set the amount inches you want to travel in the {@code setTarget()}
 * method to get the */
public class LinearPIDModule implements PIDModule {

    private final Motor motor;
    private final Telemetry telemetry;
    private final PIDController pidController;
    private double target;

    public LinearPIDModule(Motor turningMotor, Telemetry telemetry, double kP, double kS, double kD) {
        this.motor = turningMotor;
        this.motor.setDistancePerPulse(Constants.MecanumConstants.distancePerPulseInches);
        this.telemetry = telemetry;
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
        telemetry.addData("PID Error between target position and current position: ", pidController.getPositionError());
        telemetry.addData("Target: ", getTarget());
        telemetry.update();
    }

    @Override
    public double calculate() {
        Log.i(getClass().getSimpleName(), "PID Error: " + pidController.getPositionError());
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
