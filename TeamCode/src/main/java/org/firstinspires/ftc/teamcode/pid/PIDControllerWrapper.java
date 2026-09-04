package org.firstinspires.ftc.teamcode.pid;

import com.arcrobotics.ftclib.controller.PIDController;

public class PIDControllerWrapper extends PIDController {

    private volatile double setPoint = 0.0;

    public PIDControllerWrapper(double kp, double ki, double kd) {
        super(kp, ki, kd);
    }

    @Override
    public double calculate(double pv) {
        return super.calculate(pv, setPoint);
    }

    @Override
    public void setSetPoint(double sp) {
        super.setSetPoint(sp);
        setPoint = sp;
    }
}
