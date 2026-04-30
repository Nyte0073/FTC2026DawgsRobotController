package org.firstinspires.ftc.teamcode.autos;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.subsystems.driveables.factories.drivetrainfactories.MecanumDriveFactory;
import org.firstinspires.ftc.teamcode.subsystems.driveables.factories.pidfactories.DriveablePIDFactory;
import org.firstinspires.ftc.teamcode.subsystems.driveables.factories.pidfactories.PathSegment;
import org.firstinspires.ftc.teamcode.subsystems.driveables.mecanum.MecanumDrive;

@Autonomous(name = "MecanumPIDPipelineAuto", group = "teamcode")
public class MecanumPIDPipelineAuto extends CommandOpMode {

    @Override
    public void initialize() {
        MecanumDrive drive = (MecanumDrive) MecanumDriveFactory.getInstance().createDrivetrain(hardwareMap);
        CommandBase command = MecanumDriveFactory.getInstance().createDrivetrainCommand(drive,
                new GamepadEx(gamepad1), true);
        command.addRequirements(drive);
        drive.setDefaultCommand(command);
        drive.resetEncoders();
        drive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        schedule(DriveablePIDFactory.getInstance().buildCommandGroup(drive,
                PathSegment.startPath()
                        .strafe(100, PathSegment.StrafeDirection.RIGHT)
                        .buildPipeline()
        ));

    }

    @Override
    public void reset() {
        super.reset();
        CommandScheduler.getInstance().cancelAll();
    }
}