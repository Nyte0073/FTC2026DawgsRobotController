package org.firstinspires.ftc.teamcode.subsystems.driveables.factories.drivetrainfactories;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.sun.tools.javac.util.List;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.commands.MecanumCommand;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Driveable;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;
import org.firstinspires.ftc.teamcode.subsystems.driveables.factories.FactoryConstants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.mecanum.MecanumDrive;

import java.util.LinkedList;

public class MecanumDriveFactory implements DrivetrainFactory {

    private static MecanumDriveFactory instance;
    private final Vector driverVector = new Vector();

    private MecanumDriveFactory() {}

    public static MecanumDriveFactory getInstance() {
        if(instance == null) {
            instance = new MecanumDriveFactory();
        }
        return instance;
    }
    @Override
    public Driveable createDrivetrain(HardwareMap hardwareMap) {
        IMU imu = hardwareMap.get(
                IMU.class, FactoryConstants.SensorConfig.IMU_ID
        );
        imu.resetYaw();
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.DOWN));
        imu.initialize(parameters);
        return new MecanumDrive(() -> driverVector,
                () -> imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES),
                new LinkedList<>(List.of(new Motor(hardwareMap, FactoryConstants.MotorConfig.FRONT_LEFT_MECANUM_MOTOR),
                        new Motor(hardwareMap, FactoryConstants.MotorConfig.FRONT_RIGHT_MECANUM_MOTOR),
                        new Motor(hardwareMap, FactoryConstants.MotorConfig.BACK_LEFT_MECANUM_MOTOR),
                        new Motor(hardwareMap, FactoryConstants.MotorConfig.BACK_RIGHT_MECANUM_MOTOR))),
                FactoryConstants.SensorConfig.DASHBOARD_TELEMETRY, imu);
    }

    @Override
    public CommandBase createDrivetrainCommand(Driveable driveable, GamepadEx gamepadEx, boolean vectorControlled) {
        MecanumDrive drive = (MecanumDrive) driveable;
        if(vectorControlled) {
          return new MecanumCommand.VectorMecanumCommand(driverVector, drive);
        } else {
            return new MecanumCommand(driverVector, gamepadEx, drive);
        }
    }
}
