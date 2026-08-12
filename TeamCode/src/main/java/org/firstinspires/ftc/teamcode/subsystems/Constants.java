package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;

public class Constants {
    public static final double KP = 0.005, KI = 0, KD = 0.005,
    DAMPER = 0.2, SERVO_MIN = 0, SERVO_MAX = 1, PINCER_SYSTEM_LEFT_SERVO_GRAB = 0.5, PINCER_SYSTEM_RIGHT_SERVO_GRAB = 0.6,
    ARM_SYSTEM_POSITION_ONE = 0.2, ARM_SYSTEM_POSITION_TWO = 0.6, ARM_SYSTEM_POSITION_THREE = 0.8;

    public static final GamepadKeys.Button PINCER_TOGGLER = GamepadKeys.Button.A;
}
