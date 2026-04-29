package org.firstinspires.ftc.teamcode.subsystems.driveables.factories.drivetrainfactories;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import java.util.List;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.commands.MecanumCommand;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Driveable;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;
import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.externalhardwareactions.HardwareAction;
import org.firstinspires.ftc.teamcode.subsystems.driveables.factories.FactoryConstants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.mecanum.MecanumDrive;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**Factory to call to create new mecanum drivetrain instances easily with two methods, one for drivetrain creation and one for the drivetrain
 * command creation.*/
public final class MecanumDriveFactory implements DrivetrainFactory {

    /*The instance of this class that can be accessed to statically to get access to the instance-specific drivetrain and drive command
    * creation methods.*/
    private static MecanumDriveFactory instance;

    /**The vector to be used to update the drivetrain with user-inputted from the Vector in the {@link FactoryConstants} class.*/
    private final Vector driverVector = new Vector();

    /*Constructs a new instance of this class, but only for the one instance that can be accessed statically.*/
    private MecanumDriveFactory() {}

    private final Map<GamepadKeys.Button, HardwareAction> buttonsToRunnable = new LinkedHashMap<>();

    /**@return The instance of this class that be used to access the instance-specific drivetrain and drive command creation methods.*/
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
                FactoryConstants.SensorConfig.DASHBOARD_TELEMETRY, imu, buttonsToRunnable);
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

    @Override
    public void addGameActions(Map<GamepadKeys.Button, HardwareAction> buttonsToRunnable) {
        this.buttonsToRunnable.clear();
        this.buttonsToRunnable.putAll(buttonsToRunnable);
    }

    @Override
    public Map<GamepadKeys.Button, HardwareAction> getGameActions() {
        return buttonsToRunnable;
    }
}
