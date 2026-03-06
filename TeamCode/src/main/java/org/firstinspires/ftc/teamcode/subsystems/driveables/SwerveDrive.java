package org.firstinspires.ftc.teamcode.subsystems.driveables;

import static org.firstinspires.ftc.teamcode.subsystem_math.SwerveMath.calculateFinalNormalizedHeading;
import static org.firstinspires.ftc.teamcode.subsystem_math.SwerveMath.calculateTranslatedAndRotatedVector;
import static org.firstinspires.ftc.teamcode.subsystem_math.SwerveMath.driverVectorIsTooSmall;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("all")
public final class SwerveDrive extends Swerve {

    public SwerveDrive(Supplier<Vector> driverVectorSupplier, Supplier<Double> currentOrientationSupplier, Telemetry telemetry) {
        super(driverVectorSupplier, currentOrientationSupplier, telemetry);
    }

    @Override
    public void periodic() {
        System.out.println("Something is right with this code.");
    }

    @Override
    public Map<Motor, Double> calculateSwerveHeadings(Vector driverVector, double originalPose, boolean rotating, boolean clockwise) {
        if(driverVectorIsTooSmall(driverVector)) {
            driverVector = new Vector(0, 0, 0);
        }
        Map<Motor, Double> returnedMap = new HashMap<>();
        for(Motor turningMotor : Constants.SwerveConstants.turningMotors) {
            Vector translatedAndRotatedVector = calculateTranslatedAndRotatedVector(Vector.motorsToVectorPositions.get(turningMotor),
                    rotating, clockwise, driverVector);
            Constants.SwerveConstants.motorsAndTheirRotatedAndTranslatedVectors.put(turningMotor, translatedAndRotatedVector);
            double currentPosition = turningMotor.getCurrentPosition();
            double absolutePosition = currentPosition + originalPose;
            double reversedHeading = calculateFinalNormalizedHeading(driverVector, translatedAndRotatedVector, turningMotor, currentPosition
                    , absolutePosition);
            returnedMap.put(turningMotor, reversedHeading);
        }
        return returnedMap;
    }

    @Override
    public void stopMotors() {
        System.out.println("Stopping motors.");
    }
}
