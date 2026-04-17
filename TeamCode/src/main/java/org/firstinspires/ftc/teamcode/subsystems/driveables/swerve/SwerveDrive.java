package org.firstinspires.ftc.teamcode.subsystems.driveables.swerve;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;
import org.firstinspires.ftc.teamcode.subsystems.io.AndroidStudioServer;
import org.firstinspires.ftc.teamcode.subsystems.io.RobotSpecs;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class SwerveDrive extends Swerve {
    private Thread[] robotThreads = null;
    private final LinkedList<SwerveModule> modules = new LinkedList<>();
    private final IMU imu;

    public SwerveDrive(Supplier<Vector> driverVectorSupplier, Supplier<Integer> currentRobotOrientationSupplier, Telemetry telemetry, LinkedList<SwerveModule> swerveModules, IMU imu) {
        super(driverVectorSupplier, currentRobotOrientationSupplier, telemetry);
        modules.addAll(swerveModules);
        this.imu = imu;
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
        for(SwerveModule swerveModule : modules) {
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
        List<Motor> motors = new LinkedList<>();
        for(SwerveModule module : modules) {
            motors.add(module.rotatingMotor);
        }
        return motors;
    }

    @Override
    public IMU getIMU() {
        return imu;
    }

    @Override
    public void invertLeftSideEncoders(boolean inverted) {
        if(inverted) {
           modules.get(0).drivingMotor.encoder.setDirection(Motor.Direction.REVERSE);
           modules.get(2).drivingMotor.encoder.setDirection(Motor.Direction.REVERSE);
        } else {
            modules.get(0).drivingMotor.encoder.setDirection(Motor.Direction.FORWARD);
            modules.get(2).drivingMotor.encoder.setDirection(Motor.Direction.FORWARD);
        }
    }

    @Override
    public void invertRightSideEncoders(boolean inverted) {
        if(inverted) {
            modules.get(1).drivingMotor.encoder.setDirection(Motor.Direction.REVERSE);
            modules.get(3).drivingMotor.encoder.setDirection(Motor.Direction.REVERSE);
        } else {
            modules.get(1).drivingMotor.encoder.setDirection(Motor.Direction.FORWARD);
            modules.get(3).drivingMotor.encoder.setDirection(Motor.Direction.FORWARD);
        }
    }

    @Override
    public void invertLeftSideMotors(boolean inverted) {
        if(inverted) {
            modules.get(0).drivingMotor.setInverted(true);
            modules.get(2).drivingMotor.setInverted(true);
        } else {
            modules.get(0).drivingMotor.setInverted(false);
            modules.get(2).drivingMotor.setInverted(false);
        }
    }

    @Override
    public void invertRightSideMotors(boolean inverted) {
        if(inverted) {
            modules.get(1).drivingMotor.setInverted(true);
            modules.get(3).drivingMotor.setInverted(true);
        } else {
            modules.get(1).drivingMotor.setInverted(false);
            modules.get(3).drivingMotor.setInverted(false);
        }
    }

    @Override
    public void resetEncoders() {
        for(SwerveModule module : modules) {
            module.drivingMotor.resetEncoder();
        }
    }

    @Override
    public void setZeroPowerBehavior(Motor.ZeroPowerBehavior zeroPowerBehavior) {
        for(SwerveModule module : modules) {
            module.drivingMotor.setZeroPowerBehavior(zeroPowerBehavior);
        }
    }
}