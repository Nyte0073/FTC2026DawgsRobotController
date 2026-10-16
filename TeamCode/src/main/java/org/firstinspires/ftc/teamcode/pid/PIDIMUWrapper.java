package org.firstinspires.ftc.teamcode.pid;

import org.firstinspires.ftc.teamcode.subsystems.IMUWrapper;

public class PIDIMUWrapper {

    private final IMUWrapper pidControlledIMUWWrapper;
    private final PIDControllerWrapper pidControllerWrapper;

    public PIDIMUWrapper(IMUWrapper pidControlledIMUWWrapper) {
        this.pidControlledIMUWWrapper = pidControlledIMUWWrapper;
        pidControllerWrapper = new PIDControllerWrapper(PIDConstants.IMU_KP, PIDConstants.IMU_KI, PIDConstants.IMU_KD);
    }

    public IMUWrapper getPidControlledIMUWrapper() {
        return pidControlledIMUWWrapper;
    }

    public PIDControllerWrapper getPidControllerWrapper() {
        return pidControllerWrapper;
    }
}
