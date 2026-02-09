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

import java.util.List;
import java.util.function.Supplier;

@TeleOp(group = "teamcode", name = "SwerveTeleop")
public class SwerveTeleop extends CommandOpMode {

    @Override
    public void initialize() {
        List<Motor> drivingMotors = List.of(
                new Motor(hardwareMap, Constants.frontLeftDrivingMotorID),
                new Motor(hardwareMap, Constants.frontRightDrivingMotorID),
                new Motor(hardwareMap, Constants.backLeftDrivingMotorID),
                new Motor(hardwareMap, Constants.backRightDrivingMotorID)
        ), rotatingMotors = List.of(
                new Motor(hardwareMap, Constants.frontLeftRotatingMotorID),
                new Motor(hardwareMap, Constants.frontRightRotatingMotorID),
                new Motor(hardwareMap, Constants.backLeftRotatingMotorID),
                new Motor(hardwareMap, Constants.backRightRotatingMotorID)
        );
        IMU imu = hardwareMap.get(IMU.class, "imu");
        GamepadEx gamepadEx = new GamepadEx(gamepad1);
        Constants.initConstants(drivingMotors, rotatingMotors);

        Vector driverVector = new Vector();
        Supplier<Vector> driverVectorSupplier = () -> driverVector;
        Supplier<Double> currentIMUOrientationSupplier = () -> imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        SwerveDrive swerveDrive = new SwerveDrive(driverVectorSupplier, currentIMUOrientationSupplier);
        SwerveCommand swerveCommand = new SwerveCommand(swerveDrive, gamepadEx, driverVector);
        swerveDrive.setDefaultCommand(swerveCommand);
    }
}
