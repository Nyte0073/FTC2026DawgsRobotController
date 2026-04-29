package org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**Utility class used to create all types of external hardware on the robot, besides anything used with the robot's drivetrain, which is
 * configured separately in the {@link org.firstinspires.ftc.teamcode.subsystems.driveables.factories.drivetrainfactories.DrivetrainFactory}
 * class.*/
public final class TeleopExternalHardwareFactory {

    public static ServoImpl createServoImpl(HardwareMap hardwareMap, ExternalHardwareConstants.ServoImplConstants.ServoType servoType) {
        return new ServoImpl(hardwareMap, servoType);
    }
}
