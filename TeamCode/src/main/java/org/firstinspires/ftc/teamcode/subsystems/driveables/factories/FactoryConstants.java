package org.firstinspires.ftc.teamcode.subsystems.driveables.factories;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

import java.util.List;

public class FactoryConstants {

    public static final class SensorConfig {
        public static final String IMU_ID = "imu";
        public static final Telemetry DASHBOARD_TELEMETRY = FtcDashboard.getInstance().getTelemetry();
    }

    public static final class MotorConfig {
        public static final String FRONT_LEFT_MECANUM_MOTOR = "frontLeftMecanumMotor",
        FRONT_RIGHT_MECANUM_MOTOR = "frontRightMecanumMotor", BACK_LEFT_MECANUM_MOTOR = "backLeftMecanumMotor",
        BACK_RIGHT_MECANUM_MOTOR = "backRightMecanumMotor";
        public static final boolean invertLeftSideEncoders = true, invertRightSideEncoders = true,
        invertLeftSideMotors = false, invertRightSideMotors = true;
    }

    public static final class MathConfig {
        public static final Vector DRIVE_VECTOR = new Vector();
    }

    public static final class TeleopAndAutoConstants {
        /**P, I and D coefficients to be used in the PID system for autos to have control over how the motor power is set to
         * the robot depending on the error between it and its target position.*/
        public static final double mecanumKp = 0.08, mecanumKs = 0, mecanumKd = 0.00297, swerveDistancePerPulse = 0.25, swerveTolerance = 0.08,
                swerveKp = 0.01, swerveKi = 0, swerveKd = 0.0005, tolerance = 6;

        /***/
        public static final double distancePerPulseInches = 0.03343806, distancePerPulseAngle = 0.25;

        /**The amount of ticks the motor encoders count every full rotation of the wheel.*/
        public static final int ticksPerRev = 1440;

        /**The length of the field, in inches.*/
        public static final int fieldLength = 144;

        public static final double deadZoneTolerance = 0.12, degreePercentageToDegrees = 360;

        public static final List<GamepadKeys.Button> buttons = List.of(
                GamepadKeys.Button.DPAD_DOWN,
                GamepadKeys.Button.DPAD_LEFT,
                GamepadKeys.Button.DPAD_RIGHT,
                GamepadKeys.Button.DPAD_UP,
                GamepadKeys.Button.A,
                GamepadKeys.Button.B,
                GamepadKeys.Button.X,
                GamepadKeys.Button.Y
        );
    }
}
