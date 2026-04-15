package org.firstinspires.ftc.teamcode.subsystems.driveables.factories;

import com.acmerobotics.dashboard.FtcDashboard;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

public class FactoryConstants {

    public static final class SensorConfig {
        public static final String IMU_ID = "imu";
        public static final Telemetry DASHBOARD_TELEMETRY = FtcDashboard.getInstance().getTelemetry();
    }

    public static final class MotorConfig {
        public static final String FRONT_LEFT_MECANUM_MOTOR = "frontLeftMecanumMotor",
        FRONT_RIGHT_MECANUM_MOTOR = "frontRightMecanumMotor", BACK_LEFT_MECANUM_MOTOR = "backLeftMecanumMotor",
        BACK_RIGHT_MECANUM_MOTOR = "backRightMecanumMotor";
    }

    public static final class MathConfig {
        public static final Vector DRIVE_VECTOR = new Vector();
    }
}
