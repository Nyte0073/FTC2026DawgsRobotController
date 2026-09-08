package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class IMUWrapper {

    public final IMU imu;
    public String imuName;

    public IMUWrapper(HardwareMap hardwareMap, String imuName) {
        this.imuName = imuName;
        imu = hardwareMap.get(IMU.class, imuName);
    }

    public IMUWrapper(String imuName) {
        imu = null;
        this.imuName = imuName;
    }

    public double getYaw(AngleUnit angleunit) {
        return imu.getRobotYawPitchRollAngles().getYaw(angleunit);
    }

    public void resetYaw() {
        imu.resetYaw();
    }
}
