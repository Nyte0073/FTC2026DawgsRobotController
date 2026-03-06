package org.firstinspires.ftc.teamcode.teleops;

import android.util.Log;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.commands.MecanumCommand;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

@TeleOp(name = "MecanumTeleop", group = "teamcode")
public final class MecanumTeleop extends CommandOpMode {

    @Override
    public void initialize() {
        GamepadEx gamepadEx = new GamepadEx(gamepad1);
        Motor[] motors = new Motor[] {
                new Motor(hardwareMap, Constants.MecanumConstants.frontLeftMecanumMotor),
                new Motor(hardwareMap, Constants.MecanumConstants.frontRightMecanumMotor),
                new Motor(hardwareMap, Constants.MecanumConstants.backLeftMecanumMotor),
                new Motor(hardwareMap, Constants.MecanumConstants.backRightMecanumMotor)
        };
        Constants.MecanumConstants.initConstants(motors[0], motors[1], motors[2], motors[3]);
        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT, RevHubOrientationOnRobot.UsbFacingDirection.DOWN));
        imu.initialize(parameters);
        imu.resetYaw();
        Vector driverVector = new Vector();
        MecanumDrive mecanumDrive = new MecanumDrive(() -> driverVector, () -> imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES), motors, telemetry);
        MecanumCommand command = new MecanumCommand(driverVector, gamepadEx, mecanumDrive);
        command.addRequirements(mecanumDrive);
        mecanumDrive.setDefaultCommand(command);
    }

}
