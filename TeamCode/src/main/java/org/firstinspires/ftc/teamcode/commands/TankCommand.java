package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.driveables.TankDrive;

public class TankCommand extends CommandBase {

    private final TankDrive tankDrive;

    public TankCommand(TankDrive tankdrive) {
        this.tankDrive = tankdrive;
    }

    @Override
    public void execute() {
        tankDrive.drive();
    }
}
