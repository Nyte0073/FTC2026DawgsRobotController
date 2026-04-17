package org.firstinspires.ftc.teamcode.teleops;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.ExternalHardwareConstants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.ServoImpl;
import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.TeleopExternalHardwareFactory;
import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.externalhardwareactions.ServoAction;
import org.firstinspires.ftc.teamcode.subsystems.driveables.factories.drivetrainfactories.MecanumDriveFactory;
import org.firstinspires.ftc.teamcode.subsystems.driveables.mecanum.MecanumDrive;

import java.util.Map;

@TeleOp(name = "MecanumDriveTeleop", group = "teamcode")
public class MecanumDriveTeleop extends CommandOpMode {

    @Override
    public void initialize() {
        MecanumDrive drive = (MecanumDrive) MecanumDriveFactory.getInstance().createDrivetrain(hardwareMap);
        CommandBase command = MecanumDriveFactory.getInstance().createDrivetrainCommand(drive, new GamepadEx(gamepad1), false);
        drive.invertLeftSideMotors(true);
        drive.invertRightSideMotors(true);
        drive.invertRightSideEncoders(true);

        ServoImpl leftExtension = TeleopExternalHardwareFactory.createServoImpl(hardwareMap,
                ExternalHardwareConstants.ServoImplConstants.ServoType.LEFT_EXTENSION);

        MecanumDriveFactory.addGameActions(Map.of(
                GamepadKeys.Button.DPAD_UP, new ServoAction(leftExtension, ServoAction.ActionType.GO_TO_MAX_LEFT_EXTENSION),
                GamepadKeys.Button.DPAD_DOWN, new ServoAction(leftExtension, ServoAction.ActionType.GO_TO_MIN_LEFT_EXTENSION)
        ));

        drive.resetEncoders();
        command.addRequirements(drive);
        drive.setDefaultCommand(command);
    }
}