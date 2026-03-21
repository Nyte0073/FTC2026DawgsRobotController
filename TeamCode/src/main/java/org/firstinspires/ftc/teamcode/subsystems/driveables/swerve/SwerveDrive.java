package org.firstinspires.ftc.teamcode.subsystems.driveables.swerve;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;
import org.firstinspires.ftc.teamcode.subsystems.io.AndroidStudioServer;
import org.firstinspires.ftc.teamcode.subsystems.io.RobotSpecs;

import java.util.function.Supplier;

public class SwerveDrive extends Swerve {
    private Thread[] robotThreads = null;
    public SwerveDrive(Supplier<Vector> driverVectorSupplier, Supplier<Integer> currentRobotOrientationSupplier, Telemetry telemetry) {
        super(driverVectorSupplier, currentRobotOrientationSupplier, telemetry);
    }

    @Override
    public void stopThreads() {
        for(Thread t : getRobotThreads()) {
            t.interrupt();
        }
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

    @SuppressWarnings("all")
    @Override
    public Thread[] getRobotThreads() {
        if(robotThreads == null) {
            AndroidStudioServer server = new AndroidStudioServer(() -> new RobotSpecs(null, null));
            robotThreads = new Thread[] {
                    new Thread(() -> {
                        try {
                            server.launchSendingServer();
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    })
            };
            return robotThreads;
        } else {
            return robotThreads;
        }
    }
}