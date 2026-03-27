package org.firstinspires.ftc.teamcode.teleops;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.commands.SwerveCommand;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;
import org.firstinspires.ftc.teamcode.subsystems.driveables.swerve.SwerveDrive;
import org.firstinspires.ftc.teamcode.subsystems.driveables.swerve.SwerveModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@TeleOp(name = "SwerveTeleop", group = "teamcode")
public class SwerveTeleop extends CommandOpMode {

    @Override
    public void initialize() {
        GamepadEx gamepadEx = new GamepadEx(gamepad1);
        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.DOWN));
        imu.initialize(parameters);
        SwerveModule[] swerveModules = new SwerveModule[] {
            new SwerveModule(new Motor(hardwareMap, Constants.SwerveConstants.frontLeftRotatingMotorID),
                    null, new Vector(-1, 1), telemetry),
                new SwerveModule(new Motor(hardwareMap, Constants.SwerveConstants.frontRightRotatingMotorID),
                        null, new Vector(1, 1), telemetry),
            new SwerveModule(new Motor(hardwareMap, Constants.SwerveConstants.backLeftRotatingMotorID),
                    null, new Vector(-1, -1), telemetry),
                new SwerveModule(new Motor(hardwareMap, Constants.SwerveConstants.backRightRotatingMotorID),
                        null, new Vector(1, -1), telemetry)
        };
        Constants.SwerveConstants.initConstants(swerveModules[0], swerveModules[1], swerveModules[2], swerveModules[3]);
        Vector driverVector = new Vector(0, 0, 0);
        SwerveDrive swerveDrive = new SwerveDrive(() -> driverVector, () -> (int) (imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)), telemetry);
        SwerveCommand swerveCommand = new SwerveCommand(swerveDrive, gamepadEx, driverVector);
        swerveCommand.addRequirements(swerveDrive);
        swerveDrive.setDefaultCommand(swerveCommand);
    }


}
