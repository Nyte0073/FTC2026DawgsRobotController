package org.firstinspires.ftc.teamcode.pid;

import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.MecanumConstants.mecanumKd;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.MecanumConstants.mecanumKp;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.MecanumConstants.mecanumKs;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;

public class AngularPIDModule implements PIDModule {

    private final Motor motor;
    private final Telemetry telemetry;
    private final PIDController pidController = new PIDController(mecanumKp, mecanumKs, mecanumKd);
    private double target;

    public AngularPIDModule(Telemetry telemetry, Motor motor) {
        this.telemetry = telemetry;
        this.motor = motor;
        this.motor.setDistancePerPulse(Constants.MecanumConstants.distancePerPulseAngle);
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
