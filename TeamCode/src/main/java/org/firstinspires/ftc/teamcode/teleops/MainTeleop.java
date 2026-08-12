package org.firstinspires.ftc.teamcode.teleops;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.commands.ExternalHardwareCommand;
import org.firstinspires.ftc.teamcode.commands.TeleopDriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.PincerSystem;
import org.firstinspires.ftc.teamcode.subsystems.RobotInput;
import org.firstinspires.ftc.teamcode.subsystems.SwerveDriveTrain;

import java.util.Map;

@TeleOp(name = "MainTeleop", group = "teamcode")
public class MainTeleop extends CommandOpMode {

    @Override
    public void initialize() {
        /*Example of Mecanum drivetrain initialization.*/
        RobotInput robotInput = new RobotInput(
                new GamepadEx(gamepad1), hardwareMap.get(IMU.class, "imu"));
        Drivetrain drivetrain = new MecanumDriveTrain(robotInput,
                hardwareMap);
        drivetrain.getHardware().put(
              new PincerSystem(hardwareMap, "leftPincer", "rightPincer"), RobotInput.InputType.PINCER_SYSTEM_TOGGLE
        );
        TeleopDriveCommand command = new TeleopDriveCommand(drivetrain, true);
        command.initialize();
        ExternalHardwareCommand hardwareCommand = new ExternalHardwareCommand(drivetrain.getHardware(),
                robotInput
        );
        hardwareCommand.initialize();

        /*Example of Swerve drivetrain initialization.*/
        RobotInput rInput = new RobotInput(
                new GamepadEx(gamepad1), hardwareMap.get(IMU.class, "imu")
        );
        Drivetrain swerveDrivetrain = new SwerveDriveTrain(
                Map.of(
                        "frontLeftTurning", "frontLeftDriving",
                        "frontRightTurning", "frontRightDriving",
                        "backLeftTurning", "backLeftDriving",
                        "backRightTurning", "backRightDriving"
                ), rInput, hardwareMap
        );
        TeleopDriveCommand swerveDriveCommand = new TeleopDriveCommand(swerveDrivetrain, true);
        swerveDriveCommand.initialize();
    }
}
