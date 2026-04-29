package org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.externalhardwareactions;

import com.qualcomm.robotcore.hardware.IMU;

import java.util.function.Consumer;

/**Represents an action that can be done to the IMU system of the robot.*/
public class IMUAction implements HardwareAction {

    /*Reference to the IMU system the specifically configured actions will perform their functions on.*/
    private final IMU imu;

    /*The specific action to be executed on the IMU system.*/
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
