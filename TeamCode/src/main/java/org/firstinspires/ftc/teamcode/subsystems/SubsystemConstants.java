package org.firstinspires.ftc.teamcode.subsystems;

import java.util.Map;

public class SubsystemConstants {

    public static final Map<String, Double> SWERVE_TURNING_MOTOR_ROTATION_POSITIONS;

    /*---------------------------------------------------------------------*/

    public static final String FRONT_LEFT_SWERVE_TURNING_MOTOR_ID = "frontLeftSwerveTurningMotor",
    FRONT_RIGHT_SWERVE_TURNING_MOTOR_ID = "frontRightSwerveTurningMotor",
    BACK_LEFT_SWERVE_TURNING_MOTOR_ID = "backLeftSwerveTurningMotor",
    BACK_RIGHT_SWERVE_TURNING_MOTOR_ID = "backRightSwerveTurningMotor", FRONT_LEFT_SWERVE_DRIVING_MOTOR_ID = "frontLeftSwerveDrivingMotor",
    FRONT_RIGHT_SWERVE_DRIVING_MOTOR_ID = "frontRightSwerveDrivingMotor",
    BACK_LEFT_SWERVE_DRIVING_MOTOR_ID = "backLeftSwerveDrivingMotor", BACK_RIGHT_SWERVE_DRIVING_MOTOR_ID = "backRightSwerveDrivingMotor";

    /*---------------------------------------------------------------------*/

    public static final String IMU_ID = "imu",
    MECANUM_CHASSIS_FRONT_LEFT_MOTOR_ID = "frontLeftMecanumDriveMotor",
    MECANUM_CHASSIS_FRONT_RIGHT_MOTOR_ID = "frontRightMecanumDriveMotor",
    MECANUM_CHASSIS_BACK_LEFT_MOTOR_ID = "backLeftMecanumDriveMotor",
    MECANUM_CHASSIS_BACK_RIGHT_MOTOR_ID = "backRightMecanumDriveMotor",

    /*---------------------------------------------------------------------*/

    /*These four constants right here -> ZORAIZ AND DANIEL, DO NOT CHANGE! YOU TOO DECLAN! I'M WATCHING YOU.*/
    FRONT_LEFT_MOTOR_UNIVERSAL_IDENTIFIER = "frontleft",
    FRONT_RIGHT_MOTOR_UNIVERSAL_IDENTIFIER = "frontright",
    BACK_LEFT_MOTOR_UNIVERSAL_IDENTIFIER = "backleft",
    BACK_RIGHT_MOTOR_UNIVERSAL_IDENTIFIER = "backright";

    /*---------------------------------------------------------------------*/
    /*These four constants right here -> ZORAIZ AND DANIEL, DO NOT CHANGE! YOU TOO DECLAN! I'M WATCHING YOU.*/
    public static final double NEGATIVE_SIN_45 = Math.sin(Math.toRadians(315)),
    POSITIVE_SIN_45 = Math.sin(Math.toRadians(45)), POSITIVE_COS_45 = Math.cos(Math.toRadians(45)),
    NEGATIVE_COS_45 = Math.cos(Math.toRadians(135)),

    /*---------------------------------------------------------------------*/
    WHEEL_STALLING_THRESHOLD = 0.5, STALLING_VELOCITY_THRESHOLD = 10, MOTOR_STALL_WAIT_TIME_MILLIS = 1500,
    COMPONENT_POWER_MINIMUM_THRESHOLD = 0.05;

    /*---------------------------------------------------------------------*/

    public static final double FRONT_LEFT_SWERVE_TURNING_MOTOR_ROTATE_POSITION_DEGREES = -45.0,
            FRONT_RIGHT_SWERVE_TURNING_MOTOR_ROTATE_POSITION_DEGREES = -135.0,
            BACK_LEFT_SWERVE_TURNING_MOTOR_ROTATE_POSITION_DEGREES = 45.0,
            BACK_RIGHT_SWERVE_TURNING_MOTOR_ROTATE_POSITION_DEGREES = 135.0;

    static {
        SWERVE_TURNING_MOTOR_ROTATION_POSITIONS = Map.of(
            FRONT_LEFT_SWERVE_TURNING_MOTOR_ID, FRONT_LEFT_SWERVE_TURNING_MOTOR_ROTATE_POSITION_DEGREES,
                FRONT_RIGHT_SWERVE_TURNING_MOTOR_ID, FRONT_RIGHT_SWERVE_TURNING_MOTOR_ROTATE_POSITION_DEGREES,
                BACK_LEFT_SWERVE_TURNING_MOTOR_ID, BACK_LEFT_SWERVE_TURNING_MOTOR_ROTATE_POSITION_DEGREES,
                BACK_RIGHT_SWERVE_TURNING_MOTOR_ID, BACK_RIGHT_SWERVE_TURNING_MOTOR_ROTATE_POSITION_DEGREES
        );
    }
}
