package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class TeleopDriveCommand extends CommandBase {
    private final Drivetrain drivetrain;
    private final boolean fieldOriented;

    public TeleopDriveCommand(Drivetrain drivetrain, boolean fieldOriented) {
        this.drivetrain = drivetrain;
        this.fieldOriented = fieldOriented;
    }

    @Override
    public void initialize() {
        addRequirements(drivetrain);
        drivetrain.setDefaultCommand(this);
    }

    @Override
    public void execute() {
       try {
           drivetrain.drive(fieldOriented, false, null);
       } catch(Exception e) {
           throw new RuntimeException(e);
       }
    }
}
