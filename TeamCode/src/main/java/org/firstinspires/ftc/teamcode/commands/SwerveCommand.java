package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;
import org.firstinspires.ftc.teamcode.subsystems.driveables.swerve.SwerveDrive;

/**Class to command a swerve drive subsystem to drive around */
public class SwerveCommand extends CommandBase {

    private final SwerveDrive swerveDrive;
    private final Vector driverVector;
    private final GamepadEx gamepadEx;

    public SwerveCommand(SwerveDrive swerveDrive, GamepadEx gamepadEx, Vector driverVector) {
        this.gamepadEx = gamepadEx;
        this.swerveDrive = swerveDrive;
        this.driverVector = driverVector;
    }

    @Override
    public void execute() {
        driverVector.setX(gamepadEx.getLeftX());
        driverVector.setY(gamepadEx.getLeftY());
        driverVector.setZ(gamepadEx.getRightX());
        swerveDrive.drive();
    }
}