package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.subsystems.driveables.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

/**Class for commanding a Mecanum Drive subsystem to drive around using four mecanum wheels.
 * Make sure to set this class as a default command for the subsystem you are using it with so
 * that it's {@code execute()} method runs automatically every command scheduler cycle.*/
public class MecanumCommand extends CommandBase {

    /*Representing human driver controller x and y input.*/
    private final Vector driverVector;

    /*Reference to human driver's controller.*/
    private final GamepadEx gamepadEx;

    /*Reference to internal drivetrain system controlling the four mecanum wheels of the
    * robot.*/
    private final MecanumDrive mecanumDrive;

    /**Constructs a new {@code MecanumCommand()} with initialized {@code driverVector} (Vector),
     * {@code gamepadEx} (GamepadEx) and {@code mecanumDrive} (MecanumDrive) variables.*/
    public MecanumCommand(Vector driverVector, GamepadEx gamepadEx, MecanumDrive mecanumDrive) {
        this.driverVector = driverVector;
        this.gamepadEx = gamepadEx;
        this.mecanumDrive = mecanumDrive;
    }

    @Override
    public void execute() {
        driverVector.setX(gamepadEx.getLeftX());
        driverVector.setY(-gamepadEx.getLeftY());
        driverVector.setZ(gamepadEx.getRightX());
        mecanumDrive.drive();
    }
}
