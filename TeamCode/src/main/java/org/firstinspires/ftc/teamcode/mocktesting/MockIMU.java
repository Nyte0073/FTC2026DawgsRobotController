package org.firstinspires.ftc.teamcode.mocktesting;

import org.firstinspires.ftc.teamcode.subsystems.IMUWrapper;

public class MockIMU extends IMUWrapper {
    private double yaw = 0;

    public MockIMU(String imuName) {
        super(imuName);
    }

    public double getCurrentYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }
}