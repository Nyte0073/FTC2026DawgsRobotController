package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import org.firstinspires.ftc.teamcode.subsystems.driveables.SwerveDrive;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

public class SwerveCommand extends CommandBase {

    private final SwerveDrive swerveDrive;
    private final GamepadEx gamepadEx;
    private final Vector driverVector;

    public SwerveCommand(SwerveDrive swerveDrive, GamepadEx gamepadEx, Vector driverVector) {
        this.swerveDrive = swerveDrive;
        this.gamepadEx = gamepadEx;
        this.driverVector = driverVector;
    }

    @Override
    public void execute() {
        double x = gamepadEx.getLeftX();
        double y = -gamepadEx.getLeftY();
        double z = gamepadEx.getRightX();
        driverVector.setX(x);
        driverVector.setY(y);
        driverVector.setZ(z);
        swerveDrive.drive();
    }
}
