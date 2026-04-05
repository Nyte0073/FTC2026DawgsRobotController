package org.firstinspires.ftc.teamcode.subsystems.driveables.swerve;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Driveable;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

import java.util.function.Supplier;

/**Class to extend when wanting to create/run a swerve drivetrain. This class contains methods to calculate and apply general vectors for
 * each swerve module to add up and create the general driving vector of the robot which is provided by the human driver's input from
 * the gamepad.*/
public abstract class Swerve extends SubsystemBase implements Driveable {

    /**Supplies the x and y input from the human driver's joystick movement.*/
    private final Supplier<Vector> driverVectorSupplier;

    /**Supplies the current orientation the robot is facing relative to its starting orientation on the field.*/
    private final Supplier<Integer> currentRobotOrientationSupplier;

    /**Reference to the robot's internal networking system that sends the robot specs from its control hub to the Android Studio
     * log cat for monitoring and debugging.*/
    private final Telemetry telemetry;

    /**Constructs a new {@code Swerve()} with an initialized {@code driverVectorSupplier} Supplier, {@code currentRobotOrientationSupplier}
     * Supplier and {@code telemetry} Telemetry.*/
    public Swerve(Supplier<Vector> driverVectorSupplier, Supplier<Integer> currentRobotOrientationSupplier, Telemetry telemetry) {
        this.driverVectorSupplier = driverVectorSupplier;
        this.currentRobotOrientationSupplier = currentRobotOrientationSupplier;
        this.telemetry = telemetry;
    }

    @Override
    public void drive() {
        Vector driverVector = driverVectorSupplier.get();
        int currentRobotOrientation = currentRobotOrientationSupplier.get();
        boolean rotating = driverVector.getZ() >= Constants.deadZoneTolerance;
        calculateSwerveModuleHeadingsAndDrive(rotating, true, driverVector, currentRobotOrientation);
    }

    @Override
    public void stop() {
        stopMotors();
        stopThreads();
    }

    @Override
    public void periodic() {
        Vector driverVector = driverVectorSupplier.get();
        telemetry.addData("Driver Vector X: ", driverVector.getX());
        telemetry.addData("Driver Vector Y: ", driverVector.getY());
        telemetry.addData("Driver Vector Z: ", driverVector.getZ());
        telemetry.update();
    }

    public abstract void stopThreads();
    public abstract void stopMotors();

    /**Calculates and applies the contribution vector to their corresponding swerve module to add up to a general vector that is the robot's
     * original drive vector inputted from human driver's gamepad.*/
    public abstract void calculateSwerveModuleHeadingsAndDrive(boolean rotating, boolean clockwise, Vector driverVector, int currentRobotOrientation);

    /**@return The threads that are used for initializing the Android Studio send and receive servers built to send data about the robot from it to
     * IntelliJ IDEA for monitoring, and then receiving messages from IntelliJ to it to get input on what has been received by IntelliJ IDEA from
     * Android Studio.*/
    public abstract Thread[] getRobotThreads();
}