package org.firstinspires.ftc.teamcode.autos;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.ExternalHardwareConstants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.ServoImpl;
import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.TeleopExternalHardwareFactory;
import org.firstinspires.ftc.teamcode.subsystems.driveables.factories.drivetrainfactories.MecanumDriveFactory;
import org.firstinspires.ftc.teamcode.subsystems.driveables.mecanum.MecanumDrive;

@Autonomous(name = "ClassAssignmentAutonomous", group = "teamcode")
public class ClassAssignmentAutonomous extends CommandOpMode {

    private MecanumDrive drive;
    boolean runOnce = false;
    ServoImpl leftClaw, rightClaw, clawRotator, arm;

    @Override
    public void initialize() {
         drive = (MecanumDrive) MecanumDriveFactory.getInstance().createDrivetrain(hardwareMap);
        CommandBase command = MecanumDriveFactory.getInstance().createDrivetrainCommand(drive, new GamepadEx(gamepad1),
                true);
        command.addRequirements(drive);
        drive.setDefaultCommand(command);
        drive.invertRightSideEncoders(true);
        drive.invertLeftSideMotors(true);
         arm = TeleopExternalHardwareFactory.createServoImpl(hardwareMap, ExternalHardwareConstants.ServoImplConstants.ServoType.RIGHT_EXTENSION);
        leftClaw = TeleopExternalHardwareFactory.createServoImpl(hardwareMap, ExternalHardwareConstants.ServoImplConstants.ServoType.LEFT_CLAW);
        rightClaw = TeleopExternalHardwareFactory.createServoImpl(hardwareMap, ExternalHardwareConstants.ServoImplConstants.ServoType.RIGHT_CLAW);
        clawRotator = TeleopExternalHardwareFactory.createServoImpl(hardwareMap, ExternalHardwareConstants.ServoImplConstants.ServoType.CLAW_SYSTEM_ROTATER);
        arm.servo.setPosition(ExternalHardwareConstants.ServoImplConstants.GROUND_MAX_POSITION);
        clawRotator.servo.setPosition(ExternalHardwareConstants.ServoImplConstants.CLAW_MIN_ROTATION);
        drive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftClaw.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.CLAW_MINIMUM);
        rightClaw.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.CLAW_MINIMUM);
    }

    @Override
    public void run() {
        if(!runOnce) {

            for(Motor motor : drive.getMotors()) {
                motor.set(0.5);

            }
            sleep(1250);
            for(Motor m : drive.getMotors()) {
                m.stopMotor();
            }
            arm.servo.setPosition(ExternalHardwareConstants.ServoImplConstants.ABSOLUTE_MIN_POSITION);
            sleep(500);
            rightClaw.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.RIGHT_CLAW_MAX_FOAM_PIECE_ROTATION);
            leftClaw.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.LEFT_CLAW_MAX_FOAM_PIECE_ROTATION);
            sleep(500);
            arm.servo.setPosition(ExternalHardwareConstants.ServoImplConstants.GROUND_MAX_POSITION);
            for(Motor motor : drive.getMotors()) {
                motor.set(0.5);
            }
            sleep(700);
            for(Motor m : drive.getMotors()) {
                m.stopMotor();

            }
            leftClaw.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.CLAW_MINIMUM);
            rightClaw.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.CLAW_MINIMUM);
            for (Motor motor : drive.getMotors()) {
                motor.set(-0.5);
            }
            sleep(850);
            for (Motor m: drive.getMotors()){
                m.stopMotor();
            }
            runOnce = true;
        }
    }
}
