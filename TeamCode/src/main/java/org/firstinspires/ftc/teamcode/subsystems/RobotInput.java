package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.Map;
import java.util.function.BooleanSupplier;

public class RobotInput {
    private final GamepadEx gamepadEx;
    private final IMU imu;
    public RobotInput(GamepadEx gamepadEx, IMU imu) {
        this.imu = imu;
        this.gamepadEx = gamepadEx;
    }

    public RobotInput() {
        gamepadEx = null;
        imu = null;
    }

    public Map<InputType, Double> getRobotDriveBaseInput() {
        return Map.of(
                InputType.FORWARD, gamepadEx.getLeftY(),
                InputType.SIDEWARDS, gamepadEx.getRightY(),
                InputType.ROTATION, gamepadEx.getRightX(),
                InputType.ORIENTATION_DEGREES, imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)
        );
    }

    public Map<InputType, BooleanSupplier> getRobotButtonInput() {
        return Map.of(
                InputType.PINCER_SYSTEM_TOGGLE, () -> gamepadEx.wasJustPressed(Constants.PINCER_TOGGLER)
        );
    }

    public enum InputType {
        FORWARD,
        SIDEWARDS,
        ROTATION,
        ORIENTATION_DEGREES,
        PINCER_SYSTEM_TOGGLE
    }

    public static final class AutoRobotInput {
        public final double forward, sideward;

        public AutoRobotInput(double forward, double sideward) {
            this.forward = forward;
            this.sideward = sideward;
        }
    }
}
