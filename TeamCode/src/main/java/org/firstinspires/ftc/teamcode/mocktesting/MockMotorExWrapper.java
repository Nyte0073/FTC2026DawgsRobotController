package org.firstinspires.ftc.teamcode.mocktesting;

import org.firstinspires.ftc.teamcode.subsystems.MotorExWrapper;

public class MockMotorExWrapper extends MotorExWrapper {

    private double currentPosition = 0;
    public MockMotorExWrapper(String motorName) {
        super(motorName);
    }

    @Override
    public void set(double power) {
        simulateMotion(power);
    }

    private void simulateMotion(double power) {
        currentPosition += 20 * power;
    }

    @Override
    public double getCPR() {
        return 1440.0;
    }

    @Override
    public double getDistance() {
        return getCurrentPosition();
    }

    @Override
    public double getCurrentPosition() {
        return currentPosition;
    }
}
