package org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.externalhardwareactions;

import com.qualcomm.robotcore.hardware.IMU;

import java.util.function.Consumer;

public class IMUAction implements HardwareAction {

    private final IMU imu;
    private final IMUActionType actionType;

    public IMUAction(IMU imu, IMUActionType actionType) {
        this.imu = imu;
        this.actionType = actionType;
    }

    @Override
    public void run() {
        actionType.consumer.accept(imu);
    }

    public enum IMUActionType {
        RESET_YAW(IMU::resetYaw);

        public final Consumer<IMU> consumer;

        IMUActionType(Consumer<IMU> consumer) {
            this.consumer = consumer;
        }

    }
}
