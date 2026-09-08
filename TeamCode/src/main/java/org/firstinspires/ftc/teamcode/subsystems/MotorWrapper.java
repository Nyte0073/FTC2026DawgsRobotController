package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MotorWrapper {
    public String motorName;
    public final MotorEx motorEx;

    public MotorWrapper(HardwareMap hardwareMap, String motorName) {
        this.motorName = motorName;
        motorEx = new MotorEx(hardwareMap, motorName);
    }

    public MotorWrapper(String motorName) {
        this.motorName = motorName;
        motorEx = null;
    }

    public String getMotorName() {
        return motorName;
    }

    public void set(double output) {
        motorEx.set(output);
    }

    public double getVelocity() {
        return motorEx.getVelocity();
    }

    public void resetEncoder() {
        motorEx.resetEncoder();
    }

    public void setDistancePerPulse(double distancePerPulse) {
        motorEx.setDistancePerPulse(distancePerPulse);
    }

    public double get() {
        return motorEx.get();
    }

    public void stopMotor() {
        motorEx.stopMotor();
    }

    public boolean atTargetPosition() {
        return motorEx.atTargetPosition();
    }

    public double getDistance() {
        return motorEx.getDistance();
    }

    public double getCPR() {
        return motorEx.getCPR();
    }

    public int getCurrentPosition() {
        return motorEx.getCurrentPosition();
    }

    public void setTargetPosition(int targetPosition) {
        motorEx.setTargetPosition(targetPosition);
    }
}
