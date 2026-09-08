package org.firstinspires.ftc.teamcode.pid;


import com.arcrobotics.ftclib.controller.PIDController;

public class PIDControllerWrapper {

    private volatile double setPoint = 0.0;
    private final PIDController pidController;

    public PIDControllerWrapper(double kp, double ki, double kd) {
       pidController = new PIDController(kp, ki, kd);
       pidController.reset();
       pidController.setTolerance(PIDConstants.PID_TOLERANCE);
    }

    public double calculate(double pv) {
        return pidController.calculate(pv, setPoint);
    }

    public void setSetPoint(double sp) {
        pidController.setSetPoint(sp);
        setPoint = sp;
    }

    public boolean atSetPoint() {
        return pidController.atSetPoint();
    }

    public double getSetPoint() {
        return setPoint;
    }

    public double getPositionError() {
        return pidController.getPositionError();
    }
}
