package org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

public final class TeleopExternalHardwareFactory {

    public static ServoImpl createServoImpl(HardwareMap hardwareMap, ExternalHardwareConstants.ServoImplConstants.ServoType servoType) {
        return new ServoImpl(hardwareMap, servoType);
    }


}
