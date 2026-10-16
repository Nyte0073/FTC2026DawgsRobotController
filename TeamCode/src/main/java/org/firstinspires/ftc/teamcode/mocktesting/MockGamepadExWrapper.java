package org.firstinspires.ftc.teamcode.mocktesting;

import org.firstinspires.ftc.teamcode.subsystems.GamepadExWrapper;

public class MockGamepadExWrapper extends GamepadExWrapper {

    @Override
    public double getLeftX() {
        return 0;
    }

    @Override
    public double getRightX() {
        return 0;
    }

    @Override
    public double getLeftY() {
        return 0;
    }
}
