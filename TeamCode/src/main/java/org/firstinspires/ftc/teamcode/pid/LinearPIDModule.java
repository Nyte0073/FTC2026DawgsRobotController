package org.firstinspires.ftc.teamcode.pid;

import static org.firstinspires.ftc.teamcode.subsystems.driveables.factories.FactoryConstants.TeleopAndAutoConstants.distancePerPulseInches;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.factories.FactoryConstants;

import java.util.LinkedList;

/**Class to be used when wanting to use a PID system to make a motor travel a certain distance in inches.
 *To convert from inches to ticks, you divide the inches by inches per tick (DPP) to get the ticks, and for inches,
 * since the DPP is 4π / 1440 ≈ 0.00873, then that means you can set the amount inches you want to travel in the {@code setTarget()}
 * method, because then when the pidController calculates the motor power with the error value calculated from getDistance(), since your DPP
 * directly converts ticks to inches, since getDistance() is DPP x ticks, getDistance() will directly return the amount of inches the robot has traveled.
 Because of this, it can directly compare the amount of inches the robot has traveled to the inches that are the target position to be able to calculate
 the output based on the error. This why you can directly set the setpoint of the PIDController to a distance in inches, because getDistance() is in inches
 anyway.*/
public class LinearPIDModule implements PIDModule {

    /**Motor controller by this module's PIDController output, used for it's {@code getDistance()} method to
     * give the PIDController a reference point generate an error value.*/
    private final LinkedList<Motor> motors = new LinkedList<>();

    /**Controller used for calculating motor output based on the error between the target position of the controller and
     * the current position of the motor.*/
    private final PIDController pidController;

    /**The target position of the PIDController.*/
    private double target;

    /**Constructs a new {@code LinearPIDModule()} with an initialized {@code t} t,
     * {@code motor} Motor, and {@code pidController} PIDController. This constructor also sets the distance per pulse of
     * its corresponding motor to amount of ticks per inches to be able to convert between ticks and inches using {@code getDistance()}.,
     * and also resets the PIDController before it is used in the code below.
     * */
    public LinearPIDModule(LinkedList<Motor> motors, double kP, double kS, double kD) {
        this.motors.addAll(motors);
        for(Motor m : this.motors) {
            m.setDistancePerPulse(distancePerPulseInches);
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
        FactoryConstants.SensorConfig.DASHBOARD_TELEMETRY.addData("Distance: ", getDistance());
        FactoryConstants.SensorConfig.DASHBOARD_TELEMETRY.addData("PID Error between target position and current position: ", pidController.getPositionError());
        FactoryConstants.SensorConfig.DASHBOARD_TELEMETRY.addData("Target: ", getTarget());
        FactoryConstants.SensorConfig.DASHBOARD_TELEMETRY.update();
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
        return Math.abs(pidController.getPositionError()) < 2;
    }

    @Override
    public Telemetry getTelemetry() {
        return FactoryConstants.SensorConfig.DASHBOARD_TELEMETRY;
    }

    @Override
    public double getError() {
        return pidController.getPositionError();
    }

    @Override
    public PIDController getController() {
        return pidController;
    }
}
