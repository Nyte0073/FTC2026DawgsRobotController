package org.firstinspires.ftc.teamcode.mocktesting;

import org.firstinspires.ftc.teamcode.subsystems.MotorWrapper;

public class MockMotorWrapper extends MotorWrapper {
    private double motorPower = 0;
    private volatile int currentPosition = 0;
    public volatile int numberOfTimesThreadRan = 0;

    public MockMotorWrapper(String motorName) {
        super(motorName);
    }

    public boolean simulateMotion() {
        currentPosition += (int) Math.round((100 * motorPower));
        return true;
    }

    @Override
    public double getDistance() {
        return currentPosition;
    }

    @Override
    public void set(double output) {
        motorPower = output;
        simulateMotion();
    }

    @Override
    public void setDistancePerPulse(double distancePerPulse) {
        System.out.println("Does nothing.");
    }

    @Override
    public void resetEncoder() {
        currentPosition = 0;
    }

    @Override
    public int getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public double getCPR() {
        return 1440;
    }
}
