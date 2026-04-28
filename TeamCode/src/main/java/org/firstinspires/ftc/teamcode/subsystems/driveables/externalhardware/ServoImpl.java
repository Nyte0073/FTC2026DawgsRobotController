package org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public final class ServoImpl {
    public final ServoEx servo;
    public ServoImpl(HardwareMap hardwareMap, ExternalHardwareConstants.ServoImplConstants.ServoType servoType) {
       servo = new SimpleServo(hardwareMap, servoType.servoName, servoType.min, servoType.max);
       servo.setInverted(servoType.inverted);
    }
}
