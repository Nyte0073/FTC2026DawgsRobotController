package org.firstinspires.ftc.teamcode.pid;

public final class PIDConstants {

    private PIDConstants() {}

    public static final double
            MECANUM_CHASSIS_DISTANCE_PER_PULSE = 1, MECANUM_CHASSIS_KP = 0.005, MECANUM_CHASSIS_KI = 0, MECANUM_CHASSIS_KD = 0.00001,

            SWERVE_CHASSIS_DISTANCE_PER_PULSE = 1, SWERVE_CHASSIS_KP = 0.005, SWERVE_CHASSIS_KI = 0, SWERVE_CHASSIS_KD = 0,

            ARCADE_TANK_CHASSIS_DISTANCE_PER_PULSE = 0, ARCADE_TANK_CHASSIS_KP = 0, ARCADE_TANK_CHASSIS_KI = 0, ARCADE_TANK_CHASSIS_KD = 0,
            PID_TOLERANCE = 2;
}
