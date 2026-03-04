package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.driveables.TankDrive;

/**CommandBase extension class for controlling a Tank drivetrain. Set this as a default command for an already created
 * {@code TankDrive} to make the tank drivetrain run and update the motors constantly and automatically.*/
public final class TankCommand extends CommandBase {

    /**Reference to the drivetrain object operating the two back driving motors to make the robot
     * move.*/
    private final TankDrive tankDrive;

    /**Constructs a new {@code TankCommand()} with an initialized {@code TankDrive}
     * for the {@code tankDrive} variable.*/
    public TankCommand(TankDrive tankdrive) {
        this.tankDrive = tankdrive;
    }

    @Override
    public void execute() {
        tankDrive.drive();
    }
}
