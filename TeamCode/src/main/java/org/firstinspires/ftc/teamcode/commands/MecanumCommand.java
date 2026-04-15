package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.factories.FactoryConstants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.mecanum.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;
import java.util.function.Predicate;

/**Class for commanding a Mecanum Drive subsystem to drive around using four mecanum wheels.
 * Make sure to set this class as a default command for the subsystem you are using it with so
 * that it's {@code execute()} method runs automatically every command scheduler cycle.*/
public final class MecanumCommand extends CommandBase {

    /**Representing human driver controller x and y input.*/
    private final Vector driverVector;

    /**Reference to human driver's controller.*/
    private final GamepadEx gamepadEx;

    /**Reference to internal drivetrain system controlling the four mecanum wheels of the
     * robot.*/
    private final MecanumDrive mecanumDrive;

    private final Predicate<GamepadKeys.Button> buttonPredicate;

    /**Constructs a new {@code MecanumCommand()} with initialized {@code driverVector} (Vector),
     * {@code gamepadEx} (GamepadEx) and {@code mecanumDrive} (MecanumDrive) variables.*/
    public MecanumCommand(Vector driverVector, GamepadEx gamepadEx, MecanumDrive mecanumDrive) {
        this.driverVector = driverVector;
        this.gamepadEx = gamepadEx;
        this.mecanumDrive = mecanumDrive;
        buttonPredicate = this.gamepadEx::wasJustPressed;
    }

    @Override
    public void execute() {
        gamepadEx.readButtons();
        driverVector.setX(-gamepadEx.getLeftX());
        driverVector.setY(-gamepadEx.getLeftY());
        driverVector.setZ(-gamepadEx.getRightX());
        mecanumDrive.drive();
        for (GamepadKeys.Button b : Constants.buttons) {
            if (buttonPredicate.test(b)) {
                mecanumDrive.switchActions(b);
            }
        }
    }

    /**Class for controlling a Mecanum drivetrain by the vector in the {@link FactoryConstants} class
     * being used to supply the drive motion of the robot to apply to the robot's drive command system. */
    public static final class VectorMecanumCommand extends CommandBase {

        /**The vector that will be edited and sent over to the {@link MecanumDrive} class to make
         * the robot drive a certain way.*/
        private final Vector driverVector;

        /**Reference to the Mecanum drive object making the robot drive with specific motor powers.*/
        private final MecanumDrive drive;

        /**Constructs a new {@code VectorMecanumCommand()} with initialized {@code driverVector} Vector and
         * {@code drive} MecanumDrive.*/
        public VectorMecanumCommand(Vector driverVector, MecanumDrive drive) {
            this.driverVector = driverVector;
            this.drive = drive;
        }

        @Override
        public void execute() {
            driverVector.setX(-FactoryConstants.MathConfig.DRIVE_VECTOR.getX());
            driverVector.setY(-FactoryConstants.MathConfig.DRIVE_VECTOR.getY());
            driverVector.setZ(-FactoryConstants.MathConfig.DRIVE_VECTOR.getZ());
            drive.drive();
        }
    }
}
