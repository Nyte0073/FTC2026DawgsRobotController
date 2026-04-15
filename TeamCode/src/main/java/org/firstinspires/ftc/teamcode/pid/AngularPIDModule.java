package org.firstinspires.ftc.teamcode.pid;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;

import java.util.LinkedList;

/**Class to be used when wanting to use a PID system to rotate a motor to specified
 * angle in the range of 0 - 360 degrees. Because the uses 0.25 as the distance per pulse, and 1440 ticks is the amount of
 * ticks per rotation, then that means that getDistance() will return the angle in degrees that the motor is currently facing.
 * Which means that you can set the angle of the motor directly in the {@code setTarget()} method and the PID system will make the robot turn
 * to 360 / 0.25 = 1440 ticks by tracking the distance in terms of the degrees.*/
public class AngularPIDModule implements PIDModule {

    /**Reference to the networking system between the robot and the driver hub to deliver over the robot's specs.*/
    private final Telemetry telemetry;

    /**Controller used for calculating motor output based on the error between the target position of the controller and
     * the current position of the motor.*/
    private final PIDController pidController;

    /**The target position of the PIDController.*/
    private double target;

    private final LinkedList<Motor> motors = new LinkedList<>();

    /**Constructs a new {@code AngularPIDModule()} with an initialized {@code telemetry} Telemetry,
     * {@code motor} Motor, and {@code pidController} PIDController. This constructor also sets the distance per pulse of
     * its corresponding motor to amount of ticks per angle to be able to convert between ticks and angles using {@code getDistance()}.,
     * and also resets the PIDController before it is used in the code below.
     * */
    public AngularPIDModule(Telemetry telemetry, LinkedList<Motor> motors, double kP, double kS, double kD) {
        this.telemetry = telemetry;
        this.motors.addAll(motors);
        for(Motor m : this.motors) {
            m.setDistancePerPulse(Constants.MecanumConstants.distancePerPulseAngle);
        }
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
        double distance = 0;
        for(Motor m : motors) {
            distance += m.getDistance();
        }
        return distance / motors.size();
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
    public double getError() {
        return pidController.getPositionError();
    }

    @Override
    public Telemetry getTelemetry() {
        return telemetry;
    }

    @Override
    public PIDController getController() {
        return pidController;
    }
}
