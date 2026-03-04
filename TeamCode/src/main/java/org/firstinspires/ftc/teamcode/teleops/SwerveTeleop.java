package org.firstinspires.ftc.teamcode.teleops;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.commands.SwerveCommand;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.SwerveDrive;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@TeleOp(group = "teamcode", name = "SwerveTeleop")
public class SwerveTeleop extends CommandOpMode {

    @Override
    public void initialize() {
        List<Motor> drivingMotors = new ArrayList<>()
        , rotatingMotors = List.of(
                new Motor(hardwareMap, Constants.SwerveConstants.frontLeftRotatingMotorID),
                new Motor(hardwareMap, Constants.SwerveConstants.frontRightRotatingMotorID),
                new Motor(hardwareMap, Constants.SwerveConstants.backLeftRotatingMotorID),
                new Motor(hardwareMap, Constants.SwerveConstants.backRightRotatingMotorID)
        );
        IMU imu = hardwareMap.get(IMU.class, "imu");
        GamepadEx gamepadEx = new GamepadEx(gamepad1);
        Constants.SwerveConstants.initConstants(drivingMotors, rotatingMotors);

        Vector driverVector = new Vector();
        Supplier<Vector> driverVectorSupplier = () -> driverVector;
        Supplier<Double> currentIMUOrientationSupplier = () -> imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        SwerveDrive swerveDrive = new SwerveDrive(driverVectorSupplier, currentIMUOrientationSupplier, telemetry);
        SwerveCommand swerveCommand = new SwerveCommand(swerveDrive, gamepadEx, driverVector);
        swerveCommand.addRequirements(swerveDrive);
        swerveDrive.setDefaultCommand(swerveCommand);
    }
}
