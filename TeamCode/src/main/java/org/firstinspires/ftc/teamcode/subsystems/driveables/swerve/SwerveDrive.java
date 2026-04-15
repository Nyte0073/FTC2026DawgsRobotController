package org.firstinspires.ftc.teamcode.subsystems.driveables.swerve;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;
import org.firstinspires.ftc.teamcode.subsystems.io.AndroidStudioServer;
import org.firstinspires.ftc.teamcode.subsystems.io.RobotSpecs;

import java.util.Collections;
import java.util.List;
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
//            SwerveModule swerveModule = Constants.SwerveConstants.swerveModules.get(0);
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

    @Override
    public List<Motor> getMotors() {
        return Collections.emptyList();
    }

    @Override
    public IMU getIMU() {
        return null;
    }

    @Override
    public void invertLeftSideEncoders(boolean inverted) {

    }

    @Override
    public void invertRightSideEncoders(boolean inverted) {

    }

    @Override
    public void invertLeftSideMotors(boolean inverted) {

    }

    @Override
    public void invertRightSideMotors(boolean inverted) {

    }
}