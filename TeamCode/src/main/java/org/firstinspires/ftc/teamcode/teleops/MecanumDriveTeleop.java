package org.firstinspires.ftc.teamcode.teleops;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.driveables.factories.drivetrainfactories.MecanumDriveFactory;
import org.firstinspires.ftc.teamcode.subsystems.driveables.mecanum.MecanumDrive;

@TeleOp(name = "MecanumDriveTeleop", group = "teamcode")
public class MecanumDriveTeleop extends CommandOpMode {

    @Override
    public void initialize() {
        MecanumDrive drive = (MecanumDrive) MecanumDriveFactory.getInstance().createDrivetrain(hardwareMap);
        CommandBase command = MecanumDriveFactory.getInstance().createDrivetrainCommand(drive, new GamepadEx(gamepad1), false);
        drive.invertLeftSideMotors(true);
        drive.invertRightSideMotors(true);
        drive.invertRightSideEncoders(true);
        drive.resetEncoders();
        command.addRequirements(drive);
        drive.setDefaultCommand(command);
    }
}