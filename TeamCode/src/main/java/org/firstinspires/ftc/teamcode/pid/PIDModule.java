package org.firstinspires.ftc.teamcode.pid;

import com.arcrobotics.ftclib.hardware.motors.Motor;

public interface PIDModule {
    double getTarget();
    void update();
    double calculate();
    double getDistance();
    void setTarget(double target);
    boolean atTarget();
    Motor getMotor();
}
