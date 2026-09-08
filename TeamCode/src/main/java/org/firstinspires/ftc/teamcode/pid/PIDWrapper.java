package org.firstinspires.ftc.teamcode.pid;
import org.firstinspires.ftc.teamcode.subsystems.MotorWrapper;

public class PIDWrapper {

    private final MotorWrapper pidControlledMotor;
    private final ChassisPIDKinematics chassisPIDKinematics;
    private final PIDControllerWrapper pidController;

    public PIDWrapper(MotorWrapper pidControlledMotor, ChassisPIDKinematics.PIDKinematicsType type) {
        this.pidControlledMotor = pidControlledMotor;
        chassisPIDKinematics = ChassisPIDKinematics.instance(type, pidControlledMotor);
        pidController = new PIDControllerWrapper(chassisPIDKinematics.getKP(), chassisPIDKinematics.getKI(), chassisPIDKinematics.getKD());
    }

    public MotorWrapper getPidControlledMotor() {
        return pidControlledMotor;
    }

    public ChassisPIDKinematics getChassisPIDKinematics() {
        return chassisPIDKinematics;
    }

    public PIDControllerWrapper getPidController() {
        return pidController;
    }
}
