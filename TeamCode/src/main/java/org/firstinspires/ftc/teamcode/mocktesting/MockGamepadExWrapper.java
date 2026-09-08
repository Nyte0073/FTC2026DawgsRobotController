package org.firstinspires.ftc.teamcode.mocktesting;

import org.firstinspires.ftc.teamcode.subsystems.GamepadExWrapper;

public class MockGamepadExWrapper extends GamepadExWrapper {

    @Override
    public double getLeftX() {
        return 1;
    }

    @Override
    public double getRightX() {
        return 0;
    }

    @Override
    public double getLeftY() {
        return 1.0/2;
    }
}
