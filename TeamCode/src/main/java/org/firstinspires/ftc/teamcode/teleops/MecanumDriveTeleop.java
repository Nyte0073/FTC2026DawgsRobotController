package org.firstinspires.ftc.teamcode.teleops;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.ExternalHardwareConstants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.ServoImpl;
import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.TeleopExternalHardwareFactory;
import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.externalhardwareactions.HardwareAction;
import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.externalhardwareactions.IMUAction;
import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.externalhardwareactions.ServoAction;
import org.firstinspires.ftc.teamcode.subsystems.driveables.factories.drivetrainfactories.MecanumDriveFactory;
import org.firstinspires.ftc.teamcode.subsystems.driveables.mecanum.MecanumDrive;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

@TeleOp(name = "MecanumDriveTeleop", group = "teamcode")
public class MecanumDriveTeleop extends CommandOpMode implements ActionFactory {
    private final Map<String, Supplier<Object>> telemetryMap = new LinkedHashMap<>();
    private GamepadEx gamepadEx;

    @Override
    public void initialize() {
         gamepadEx = new GamepadEx(gamepad1);
        MecanumDrive drive = (MecanumDrive) MecanumDriveFactory.getInstance().createDrivetrain(hardwareMap);
        CommandBase command = MecanumDriveFactory.getInstance().createDrivetrainCommand(drive, gamepadEx, false);
        drive.invertRightSideEncoders(true);

         ServoImpl leftExtension = TeleopExternalHardwareFactory.createServoImpl(hardwareMap,
                ExternalHardwareConstants.ServoImplConstants.ServoType.LEFT_EXTENSION);

         ServoImpl rightExtension = TeleopExternalHardwareFactory.createServoImpl(hardwareMap,
                ExternalHardwareConstants.ServoImplConstants.ServoType.RIGHT_EXTENSION);

         ServoImpl leftClaw = TeleopExternalHardwareFactory.createServoImpl(hardwareMap,
                 ExternalHardwareConstants.ServoImplConstants.ServoType.LEFT_CLAW);

         ServoImpl rightClaw = TeleopExternalHardwareFactory.createServoImpl(hardwareMap,
                 ExternalHardwareConstants.ServoImplConstants.ServoType.RIGHT_CLAW);

        MecanumDriveFactory.addGameActions(Map.of(
                GamepadKeys.Button.B, new ServoAction(leftExtension, rightExtension, ServoAction.DoubleActionType.GO_TO_MAX_EXTENSION),
                GamepadKeys.Button.A, new ServoAction(leftExtension, rightExtension, ServoAction.DoubleActionType.GO_TO_MIN_EXTENSION),
                GamepadKeys.Button.X, new ServoAction(leftClaw, rightClaw, ServoAction.DoubleActionType.CLAW_GRAB),
                GamepadKeys.Button.Y, new ServoAction(leftClaw, rightClaw, ServoAction.DoubleActionType.CLAW_RELEASE),
                GamepadKeys.Button.DPAD_UP, new IMUAction(drive.getIMU(), IMUAction.IMUActionType.RESET_YAW)
        ));

        telemetryMap.putAll(Map.of(
               "Left Extension Position", leftExtension.servo::getPosition,
               "Right Extension Position", rightExtension.servo::getPosition,
               "Front Left Encoder Distance", () -> drive.getMotors().get(0).encoder.getDistance(),
               "Front Right Encoder Distance", () -> drive.getMotors().get(1).encoder.getDistance(),
               "Back Left Encoder Distance", () -> drive.getMotors().get(2).encoder.getDistance(),
               "Back Right Encoder Distance", () -> drive.getMotors().get(3).encoder.getDistance()
        ));

        initializeGameActions();

        drive.resetEncoders();
        command.addRequirements(drive);
        drive.setDefaultCommand(command);
    }

    @Override
    public void run() {
        gamepadEx.readButtons();
        for(Map.Entry<String, Supplier<Object>> entry : telemetryMap.entrySet()) {
            telemetry.addData(entry.getKey(), entry.getValue().get());
        }
        telemetry.update();
        super.run();
    }

    @Override
    public void initializeGameActions() {
        for(Map.Entry<GamepadKeys.Button, HardwareAction> entry : MecanumDriveFactory.getGameActions().entrySet()) {
            gamepadEx.getGamepadButton(entry.getKey()).whenPressed(() -> entry.getValue().run());
        }
    }
}