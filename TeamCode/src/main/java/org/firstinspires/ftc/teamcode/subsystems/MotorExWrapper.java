package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public class MotorExWrapper {

    private final MotorEx motorEx;
    private final String motorName;

    public MotorExWrapper(MotorEx motorEx, String motorName) {
        this.motorEx = motorEx;
        this.motorName = motorName;
    }

    public String getMotorName() {
        return motorName;
    }

    public MotorExWrapper(String motorName) {
        this.motorName = motorName;
        motorEx = null;
    }

    public double getDistance() {
        return motorEx.getDistance();
    }

    public double getCurrentPosition() {
        return motorEx.getCurrentPosition();
    }

    public void set(double power) {
        motorEx.set(power);
    }

    public void stopMotor() {
        motorEx.stopMotor();
    }

    public double getCPR() {
        return motorEx.getCPR();
    }
    }
