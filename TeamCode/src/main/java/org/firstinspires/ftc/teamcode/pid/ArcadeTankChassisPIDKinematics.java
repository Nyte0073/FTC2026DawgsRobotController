package org.firstinspires.ftc.teamcode.pid;

import org.firstinspires.ftc.teamcode.subsystems.MotorWrapper;

public class ArcadeTankChassisPIDKinematics extends ChassisPIDKinematics {
    public ArcadeTankChassisPIDKinematics(MotorWrapper pidControlledMotor) {
        super(pidControlledMotor);
    }

    @Override
    public double getDistancePerPulse() {
        return PIDConstants.ARCADE_TANK_CHASSIS_DISTANCE_PER_PULSE;
    }

    @Override
    public double getKP() {
        return PIDConstants.ARCADE_TANK_CHASSIS_KP;
    }

    @Override
    public double getKD() {
        return PIDConstants.ARCADE_TANK_CHASSIS_KD;
    }

    @Override
    public double getKI() {
        return PIDConstants.ARCADE_TANK_CHASSIS_KI;
    }
}
