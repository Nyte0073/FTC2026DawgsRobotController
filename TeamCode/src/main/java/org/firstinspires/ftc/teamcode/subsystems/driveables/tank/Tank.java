package org.firstinspires.ftc.teamcode.subsystems.driveables.tank;

import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Driveable;
import java.util.function.Supplier;

/**Class to extend when wanting to implement a Tank drivetrain. This class will calculate the correct powers to the to the
 * two back drive motors of the robot to make it drive forward, backward and side to side.*/
public abstract class Tank extends SubsystemBase implements Driveable {

    /*Suppliers to get the x and y values from the human controller to use to calculate motor powers.*/
    private final Supplier<Double> driverYSupplier, driverXSupplier;

    /*Reference to the networking system used to upload the robot's specs from the robot to the driver hub
    * for monitoring.*/
    private final Telemetry telemetry;

    /**Constructs a new {@code Tank()} with an initialized {@code driverYSupplier} Supplier, {@code driverXSupplier} Supplier and
     * {@code telemetry} Telemetry.*/
    public Tank(Supplier<Double> driverYSupplier, Supplier<Double> driverXSupplier, Telemetry telemetry) {
        this.driverYSupplier = driverYSupplier;
        this.driverXSupplier = driverXSupplier;
        this.telemetry = telemetry;
    }

    @Override
    public void drive() {
        double y = driverYSupplier.get();
        double x = driverXSupplier.get();
        calculateMotorPowersAndDrive(x, y);
    }

    @Override
    public void stop() {
        stopMotors();
        stopThreads();
    }

    public abstract void stopMotors();
    public abstract void stopThreads();
    public abstract void calculateMotorPowersAndDrive(double x, double y);
}
