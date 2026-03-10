package org.firstinspires.ftc.teamcode.subsystems.driveables.swerve;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

import java.util.function.Supplier;

public class SwerveDrive extends Swerve {

    public SwerveDrive(Supplier<Vector> driverVectorSupplier, Supplier<Integer> currentRobotOrientationSupplier, Telemetry telemetry) {
        super(driverVectorSupplier, currentRobotOrientationSupplier, telemetry);
    }

    @Override
    public void stopThreads() {
        System.out.println("Stopping threads.");
    }

    @Override
    public void stopMotors() {
        System.out.println("Stopping motors.");
    }

    @Override
    public void calculateSwerveModuleHeadingsAndDrive(boolean rotating, boolean clockwise, Vector driverVector, int currentRobotOrientation) {
        for(SwerveModule swerveModule : Constants.SwerveConstants.swerveModules) {
            Vector translatedAndRotatedVector = swerveModule.calculateTranslatedAndRotatedMotorVector(rotating,
                    clockwise, driverVector, driverVector.getZ());
            swerveModule.applyTransAndRotVectorToMotor(translatedAndRotatedVector, currentRobotOrientation);
        }
    }
}