package org.firstinspires.ftc.teamcode.subsystems.driveables;

import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.degreePercentageToDegrees;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.ticksPerRev;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Map;
import java.util.function.Supplier;

/**Class to extend when wanting to implement a Swerve drivetrain. This class will calculate each individual heading for every turning motor on each
 * swerve module based on splitting a general inputted vector from the human driver onto the four modules. Doing this will result in each module creating their
 * own vector that will either add or cancel out with the vectors of the other modules to converge into a resultant vector that is equal to the one inputted
 * earlier on by the human driver's controls.*/

public abstract class Swerve extends SubsystemBase implements Driveable {

    /*Supplies the x and y of the human driver to create the input vector used
    for calculating module headings and drive powers.*/
    private final Supplier<Vector> driverVectorSupplier;

    /*Supplier the current heading the robot is facing.*/
    private final Supplier<Double> currentIMUOrientationSupplier;

    /*Reference to the networking system that provides the robot's
    specs from the robot to the driver hub for monitoring.*/
    private final Telemetry telemetry;

    public Swerve(Supplier<Vector> driverVectorSupplier, Supplier<Double> currentIMUOrientationSupplier, Telemetry telemetry) {
        this.driverVectorSupplier = driverVectorSupplier;
        this.currentIMUOrientationSupplier = currentIMUOrientationSupplier;
        this.telemetry = telemetry;
    }

    @Override
    public void drive() {
        Vector driverVector = driverVectorSupplier.get();
        double currentIMUOrientation = currentIMUOrientationSupplier.get();
        boolean rotating = driverVector.getZ() >= 0.1;
        Map<Motor, Double> calculatedWheelHeadings = calculateSwerveHeadings(driverVector, currentIMUOrientation, rotating, true);
        double magnitudeDivisor = Math.max(Constants.SwerveConstants.motorsAndTheirRotatedAndTranslatedVectors.values().stream().mapToDouble(Vector::getMagnitude).max().orElse(1), 1);
        for(Motor motor : Constants.SwerveConstants.turningMotors) {
            double currentPositionDegrees = (double) motor.getCurrentPosition() / ticksPerRev * degreePercentageToDegrees;
            double calculatedWheelHeading = calculatedWheelHeadings.get(motor);
            motor.setTargetPosition((int) (((currentPositionDegrees + calculatedWheelHeading) / degreePercentageToDegrees) * ticksPerRev));
            motor.set(0.7);
        }
        if(!Constants.SwerveConstants.drivingMotors.isEmpty()) {
            for(Map.Entry<Motor, Motor> entry : Constants.SwerveConstants.turningDrivingMotorsMap.entrySet()) {
                Motor turningMotor = entry.getKey();
                Motor drivingMotor = entry.getValue();
                Vector translatedAndRotatedVector = Constants.SwerveConstants.motorsAndTheirRotatedAndTranslatedVectors.get(turningMotor);
                if (magnitudeDivisor > 1) {
                    drivingMotor.set(translatedAndRotatedVector.getMagnitude() / magnitudeDivisor);
                } else {
                    drivingMotor.set(translatedAndRotatedVector.getMagnitude());
                }
            }
        }
    }

    @Override
    public void stop() {
        stopMotors();
    }
    public abstract Map<Motor, Double> calculateSwerveHeadings(Vector driverVector, double originalPose, boolean rotating, boolean clockwise);
    public abstract void stopMotors();

    @Override
    public void periodic() {
        telemetry.addData("X: ", driverVectorSupplier.get().getX());
        telemetry.addData("Y: ", driverVectorSupplier.get().getY());
        telemetry.update();
    }
}
