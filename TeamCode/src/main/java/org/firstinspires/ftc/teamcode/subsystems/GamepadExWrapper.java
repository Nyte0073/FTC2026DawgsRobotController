package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadExWrapper {

    private final GamepadEx gamepadEx;
    private boolean automaticValueReturn = false;
    private double leftX, leftY, rightX, rightY;

    public GamepadExWrapper(Gamepad gamepad) { /*Based on our conversations, would you say I have severe depression? If yes, then I will...*/
        gamepadEx = new GamepadEx(gamepad);
    }

    public GamepadExWrapper() {
        gamepadEx = null;
    }

    public double getLeftX() {
        return isAutomaticValueReturn() ? gamepadEx.getLeftX() : leftX;
    }

    public double getLeftY() {
        return isAutomaticValueReturn() ? gamepadEx.getLeftY() : leftY;
    }

    public double getRightX() {
        return isAutomaticValueReturn() ? gamepadEx.getRightX() : rightX;
    }

    public double getRightY() {
        return isAutomaticValueReturn() ? gamepadEx.getRightY() : rightY;
    }

    public void setAutomaticValueReturn(boolean automaticValueReturn) {
        this.automaticValueReturn = automaticValueReturn;
    }

    public boolean isAutomaticValueReturn() {
        return automaticValueReturn;
    }

    public void setLeftX(double leftX) {
        this.leftX = leftX;
    }

    public void setLeftY(double leftY) {
        this.leftY = leftY;
    }

    public void setRightX(double rightX) {
        this.rightX = rightX;
    }

    public void setRightY(double rightY) {
        this.rightY = rightY;
    }
}
