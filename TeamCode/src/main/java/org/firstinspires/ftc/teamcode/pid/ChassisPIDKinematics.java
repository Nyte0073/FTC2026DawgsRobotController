package org.firstinspires.ftc.teamcode.pid;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.subsystems.MotorWrapper;

public abstract class ChassisPIDKinematics {
    private final MotorWrapper pidControlledMotor;

    public ChassisPIDKinematics(MotorWrapper pidControlledMotor) {
        this.pidControlledMotor = pidControlledMotor;
    }

    public abstract double getDistancePerPulse();
    public abstract double getKP();
    public abstract double getKD();
    public abstract double getKI();

    public double getAngularOrientation(AngleUnit angleUnit) {
        return angleUnit.fromDegrees(
                pidControlledMotor.getCurrentPosition() / pidControlledMotor.getCPR() * 360
        );
    }

    public double getDistance() {
        return pidControlledMotor.getDistance();
    }

    public MotorWrapper getPidControlledMotor() {
        return pidControlledMotor;
    }

    public static ChassisPIDKinematics instance(PIDKinematicsType type, MotorWrapper motorWrapper) {
        switch(type) {
            case MECANUM_KINEMATICS: return new MecanumChassisPIDKinematics(motorWrapper);

            case SWERVE_KINEMATICS: return new SwerveChassisPIDKinematics(motorWrapper);

            case ARCADE_TANK_KINEMATICS: return new ArcadeTankChassisPIDKinematics(motorWrapper);

            default: return null;
        }
    }

    public enum PIDKinematicsType {
        MECANUM_KINEMATICS,
        SWERVE_KINEMATICS,
        ARCADE_TANK_KINEMATICS,
        DIFFERENTIAL_TANK_KINEMATICS
    }
}
