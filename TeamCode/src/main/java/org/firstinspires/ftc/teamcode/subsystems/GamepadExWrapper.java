package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.gamepad.GamepadEx;

public class GamepadExWrapper {

    private final GamepadEx gamepadEx;
    private double leftX, leftY, rightX, rightY;
    boolean isMockGamepadExWrapper = false;

    public GamepadExWrapper(GamepadEx gamepadEx) {
        this.gamepadEx = gamepadEx;
    }

    public GamepadExWrapper() {
        gamepadEx = null;
        setMockGamepadExWrapper(true);
    }

    public void setLeftX(double leftX) {
        this.leftX = leftX;
    }

    public void setLeftY(double leftY) {
        this.leftY = leftY;
    }

    public void setRightY(double rightY) {
        this.rightY = rightY;
    }

    public void setRightX(double rightX) {
        this.rightX = rightX;
    }

    public double getLeftX() {
        return isMockGamepadExWrapper ? leftX : gamepadEx.getLeftX();
    }

    public double getLeftY() {
        return isMockGamepadExWrapper ? leftY : gamepadEx.getLeftY();
    }

    public double getRightX() {
        return isMockGamepadExWrapper ? rightX : gamepadEx.getRightX();
    }

    public double getRightY() {
        return isMockGamepadExWrapper ? rightY : gamepadEx.getRightY();
    }

    public void setMockGamepadExWrapper(boolean mockGamepadExWrapper) {
        isMockGamepadExWrapper = mockGamepadExWrapper;
    }

    public boolean isMockGamepadExWrapper() {
        return isMockGamepadExWrapper;
    }
}
