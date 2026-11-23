package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.controller.PIDController;

public class PIDControllerWrapper {

    private final PIDController pidController;
    private double setPoint = 0;

    public PIDControllerWrapper(PIDController controller) {
        pidController = controller;
        pidController.setTolerance(2);
    }

    public PIDControllerWrapper() {
        pidController = new PIDController(0.01, 0, 0.0005);
        pidController.setTolerance(2);
    }

    public void setSetPoint(double setPoint) {
        pidController.setSetPoint(setPoint);
        this.setPoint = setPoint;
    }

    public double calculate(double sv) {
        return pidController.calculate(sv);
    }

    public boolean atSetPoint() {
        return pidController.atSetPoint();
    }

    public double getTargetPosition() {
        return setPoint;
    }

    public PIDController getPidController() {
        return pidController;
    }
}
