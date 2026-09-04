package org.firstinspires.ftc.teamcode.pid;

import org.firstinspires.ftc.teamcode.subsystems.MotorWrapper;

public class SwerveChassisPIDKinematics extends ChassisPIDKinematics {
    public SwerveChassisPIDKinematics(MotorWrapper pidControlledMotor) {
        super(pidControlledMotor);
    }

    @Override
    public double getDistancePerPulse() {
        return PIDConstants.SWERVE_CHASSIS_DISTANCE_PER_PULSE;
    }

    @Override
    public double getKP() {
        return PIDConstants.SWERVE_CHASSIS_KP;
    }

    @Override
    public double getKD() {
        return PIDConstants.SWERVE_CHASSIS_KD;
    }

    @Override
    public double getKI() {
        return PIDConstants.SWERVE_CHASSIS_KI;
    }
}
