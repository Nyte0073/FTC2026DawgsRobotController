package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import org.firstinspires.ftc.teamcode.subsystems.driveables.SwerveDrive;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

/**Class for commanding a Swerve Drive subsystem using four turning and driving motors. Make sure to set this
 * class as a default command for the subsystem you're using it with to make its {@code execute()}
 * method run and power the turning and driving motors automatically.*/
public final class SwerveCommand extends CommandBase {

    /**Reference the drivetrain object containing all the swerve motor implementation software for powering
     * and updating the motors and their states.*/
    private final SwerveDrive swerveDrive;

    /**Reference to the human driver's gamepad controller.*/
    private final GamepadEx gamepadEx;

    /**The x and y values of the human driver's gamepad controller that will be used to calculate the motor powers and
     * directions for the turning and driving motors on all four of the robot's swerve modules.*/
    private final Vector driverVector;

    /**Constructs a new {@code SwerveCommand()} with initialized {@code GamepadEx}, {@code SwerveDrivetrain} and {@code Vector}
     * variables for the {@code swerveDrive}, {@code gamepadEx} and {@code driverVector} variables.*/
    public SwerveCommand(SwerveDrive swerveDrive, GamepadEx gamepadEx, Vector driverVector) {
        this.swerveDrive = swerveDrive;
        this.gamepadEx = gamepadEx;
        this.driverVector = driverVector;
        addRequirements(swerveDrive);
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
