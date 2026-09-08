package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadExWrapper {

    private final GamepadEx gamepadEx;

    public GamepadExWrapper(Gamepad gamepad) {
        gamepadEx = new GamepadEx(gamepad);
    }

    public GamepadExWrapper() {
        gamepadEx = null;
    }

    public double getLeftX() {
        return gamepadEx.getLeftX();
    }

    public double getLeftY() {
        return gamepadEx.getLeftY();
    }

    public double getRightX() {
        return gamepadEx.getRightX();
    }
}
