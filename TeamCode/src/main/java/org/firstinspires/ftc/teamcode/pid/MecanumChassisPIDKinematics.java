package org.firstinspires.ftc.teamcode.pid;

import org.firstinspires.ftc.teamcode.subsystems.MotorWrapper;

public class MecanumChassisPIDKinematics extends ChassisPIDKinematics {
    public MecanumChassisPIDKinematics(MotorWrapper pidControlledMotor) {
        super(pidControlledMotor);
    }

    @Override
    public double getDistancePerPulse() {
        return PIDConstants.MECANUM_CHASSIS_DISTANCE_PER_PULSE;
    }

    @Override
    public double getKP() {
        return PIDConstants.MECANUM_CHASSIS_KP;
    }

    @Override
    public double getKD() {
        return PIDConstants.MECANUM_CHASSIS_KD;
    }

    @Override
    public double getKI() {
        return PIDConstants.MECANUM_CHASSIS_KI;
    }
}
