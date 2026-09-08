package org.firstinspires.ftc.teamcode.subsystems;

public class SubsystemConstants {

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
}
