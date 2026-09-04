package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MotorWrapper extends MotorEx {
    public String motorName;
    public MotorWrapper(String motorName, HardwareMap hardwareMap) {
        super(hardwareMap, motorName);
        this.motorName = motorName;
    }
}
