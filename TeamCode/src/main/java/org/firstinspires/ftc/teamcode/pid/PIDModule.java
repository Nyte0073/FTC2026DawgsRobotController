package org.firstinspires.ftc.teamcode.pid;

import com.arcrobotics.ftclib.controller.PIDController;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public interface PIDModule {

    /**@return The set setpoint of the PIDController (in whatever unit you're converting ticks from).*/
    double getTarget();

    /**Updates the telemetry with standardized specs about what's going on in the implementation classes' calculations and
     * stored values.*/
    void update();

    /**@return The output calculated based on the value of the set distance of the PIDController and getDistance() (distance
     * traveled by the motor in whatever unit you're converting ticks to).*/
    double calculate();

    /**@return The distance traveled by the motor (in whatever unit you're converting ticks from).*/
    double getDistance();

    /**Sets the target of the PIDController.*/
    void setTarget(double target);

    /**@return If the motor is within a specified tolerance range of the PIDController's set setpoint.*/
    boolean atTarget();

    /**@return The difference between the current position of the robot's pid controller and the set target position.*/
    double getError();

    /**@return The telemetry object used to send information from the robot to the FTC Dashboard.*/
    Telemetry getTelemetry();

    PIDController getController();
}
