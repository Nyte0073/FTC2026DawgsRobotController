package org.firstinspires.ftc.teamcode.mocktesting;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.subsystems.IMUWrapper;

public class MockIMU extends IMUWrapper {
    private double yaw = 0;

    public MockIMU(String imuName) {
        super(imuName);
    }

    @Override
    public double getYaw(AngleUnit angleunit) {
        double convertedDegrees = angleunit.fromDegrees(yaw);
        if(convertedDegrees < 0) {
            convertedDegrees += 360;
        }
        return convertedDegrees;
    }

    public void setYawDegrees(double yaw) {
        this.yaw = yaw;
    }

    @Override
    public void simulateMotion(double calculate) {
        setYawDegrees(yaw + calculate * 20);
    }
}