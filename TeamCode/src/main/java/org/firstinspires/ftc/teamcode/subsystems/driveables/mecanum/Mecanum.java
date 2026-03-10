package org.firstinspires.ftc.teamcode.subsystems.driveables.mecanum;

import android.util.Log;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Driveable;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

import java.util.function.Supplier;

/**Class to extending when wanting to create and run a mecanum drivetrain. This class is where all the calculated values for the
 * mecanum drivetrains motor powers get put together to make the robot travel forwards/backwards and/or strafe side to side,
 * depending on the inputted x and y values from the human controller.*/
public abstract class Mecanum extends SubsystemBase implements Driveable {

    /*Supplies the x and y values from the human controller to this class for using to calculate
    * motor powers.*/
    private final Supplier<Vector> driverVectorSupplier;

    /*Supplies the current angular orientation the robot is currently facing.*/
    private final Supplier<Double> currentRobotOrientationSupplier;

    /*Reference to robot-driver hub networking system designed to provide information about the robot's specs back to the FTC
    * Driver Hub for monitoring.*/
    private final Telemetry telemetry;

    /**Constructs a new {@code Mecanum()} with an initialized {@code driverVectorSupplier} Supplier, {@code currentRobotOrientationSupplier}
     * Supplier and {@code telemetry} Telemetry.*/
    public Mecanum(Supplier<Vector> driverVectorSupplier, Supplier<Double> currentRobotOrientationSupplier, Telemetry telemetry) {
        this.driverVectorSupplier = driverVectorSupplier;
        this.currentRobotOrientationSupplier = currentRobotOrientationSupplier;
        this.telemetry = telemetry;
    }

    @Override
    public void periodic() {
        Vector driverVector = driverVectorSupplier.get();
        telemetry.addData("Current Robot Orientation: ", currentRobotOrientationSupplier.get());
        telemetry.addData("X: ", driverVector.getX());
        telemetry.addData("Y: ", driverVector.getY());
        Log.i(getName(), "Periodic running.");
        telemetry.update();
}

@Override
    public void drive() {
        Vector driverVector = driverVectorSupplier.get();
        Log.i(getName(), "Calculating motor owers.");
        calculateMotorPowers(currentRobotOrientationSupplier.get(), driverVector);
    }

    @Override
    public void stop() {
        stopMotors();
        stopThreads();
    }

    /**Stops the robot's driving motors by setting their power to 0.*/
    public abstract void stopMotors();

    /**Stops any extra threads running in the background that will crash if the robot suddenly stops working for some reason.*/
    public abstract void stopThreads();

    /**Calculates the motor powers for the mecanum drive wheels and sets all their powers. This is the main method to make the robot
     * drive around.*/
    public abstract void calculateMotorPowers(double originalRobotAngle, Vector driverVector);
}
