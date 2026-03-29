package org.firstinspires.ftc.teamcode.pid;

import com.arcrobotics.ftclib.hardware.motors.Motor;

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

    /**@return The motor object used to find the distance ({@code getDistance()}) traveled in order for the PIDController to generate an error
     * value and calculate output.*/
    Motor getMotor();
}
