package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pathplanning.Trajectory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class SwerveDriveTrain extends Drivetrain {
    private final SwerveModule[] swerveModules;
    private final PIDController controller = new PIDController(Constants.KP, Constants.KI, Constants.KD);
    private final Map<SwerveModule, Double> modulesToHeadings = new HashMap<>(), modulesToDrivingMotorPower = new HashMap<>();
    public static final Lock initiationLock = new ReentrantLock(), mainWorkerThreadLock = new ReentrantLock(),
    completionLock = new ReentrantLock();
    public static final Condition initiationLockCondition = initiationLock.newCondition(),
    completionLockCondition = completionLock.newCondition(), mainWorkerThreadLockCondition = mainWorkerThreadLock.newCondition();

    public SwerveDriveTrain(Map<String, String> swerveModuleTurningAndDrivingMotorNames, RobotInput input, HardwareMap hardwareMap) {
        super(input);
        swerveModules = new SwerveModule[swerveModuleTurningAndDrivingMotorNames.size()];
        int i = 0;
        for(Map.Entry<String, String> entry : swerveModuleTurningAndDrivingMotorNames.entrySet()) {
            swerveModules[i] = new SwerveModule(new Motor(hardwareMap, entry.getKey()), new Motor(hardwareMap, entry.getValue()),
                    new PIDController(Constants.KP, Constants.KI, Constants.KD));
            i++;
        }
        Thread mainThread = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                try {
                    initiationLock.lock();
                    completionLock.lock();
                    initiationLockCondition.await();

                    mainWorkerThreadLock.lock();
                    mainWorkerThreadLockCondition.signal();
                    mainWorkerThreadLockCondition.await();

                    while(!allMotorsRotated()) {
                        for(SwerveModule module : swerveModules) {
                            double calculate = module.controller.calculate(module.turningMotor.getCurrentPosition());
                            module.turningMotor.set(Math.min(1 * Math.signum(calculate), calculate));
                        }
                    }

                    mainWorkerThreadLockCondition.signal();
                    mainWorkerThreadLock.unlock();
                    completionLockCondition.await();
                } catch(Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }), workerThread = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                try {
                   mainWorkerThreadLock.lock();
                   mainWorkerThreadLockCondition.await();

                   for(SwerveModule module : swerveModules) {
                       module.controller.setSetPoint(modulesToHeadings.get(module) / 360 * module.turningMotor.getCPR());
                   }
                   mainWorkerThreadLockCondition.signal();
                   mainWorkerThreadLockCondition.await();

                   completionLock.lock();
                   for(SwerveModule m : swerveModules) {
                       m.drivingMotor.set(modulesToDrivingMotorPower.get(m));
                   }
                   completionLockCondition.signal();
                   completionLock.unlock();

                } catch(Exception e) {
                    throw new RuntimeException(e);
                }
            }

        });

        mainThread.start();
        workerThread.start();
    }

    @Override
    public void drive(boolean fieldOriented, boolean vectorControlled, Trajectory vectorTrajectory) throws Exception {
        RobotInput input = getInput();
        Map<RobotInput.InputType, Supplier<Double>> map = input.getRobotDriveBaseInput();
        double forward, sideward;
            if (vectorControlled) {
                forward = vectorTrajectory.magnitude * Math.sin(vectorTrajectory.rotation) * Constants.DAMPER;
                sideward = vectorTrajectory.magnitude * Math.cos(vectorTrajectory.rotation) * Constants.DAMPER;
            } else {
                forward = map.get(RobotInput.InputType.FORWARD).get();
                sideward = map.get(RobotInput.InputType.SIDEWARDS).get();
            }
        if(initiationLock.tryLock(15, TimeUnit.MILLISECONDS)) {
            for (SwerveModule module : swerveModules) {
                double turningMotorHeading = module.calculateTurningMotorHeading(fieldOriented, sideward, forward,
                        map.get(RobotInput.InputType.ORIENTATION_DEGREES).get());
                modulesToHeadings.put(module, turningMotorHeading);
                double drivingMotorPower = Math.hypot(sideward, forward);
                modulesToDrivingMotorPower.put(module, drivingMotorPower);
            }
            initiationLockCondition.signal();
            initiationLock.unlock();
        }
    }

    public boolean allMotorsRotated() {
        for(SwerveModule swerveModule : swerveModules) {
            if(!swerveModule.controller.atSetPoint()) return false;
        }
        return true;
    }

    @Override
    public void stop() {

    }

    @Override
    public Motor[] getMotors() {
        return null;
    }

    @Override
    public PIDController getPIDController() {
        return controller;
    }

    public static final class SwerveModule {
        public final Motor turningMotor, drivingMotor;
        private double currentHeading = 0;
        public final PIDController controller;

        public SwerveModule(Motor turningMotor, Motor drivingMotor, PIDController controller) {
            this.turningMotor = turningMotor;
            this.drivingMotor = drivingMotor;
            this.controller = controller;
            this.controller.reset();
            this.controller.setTolerance(4);
        }

        public double calculateTurningMotorHeading(boolean fieldCentric, double sidewards, double forwards, double robotIMUHeading) {
            double desiredFieldAngle = (Math.hypot(sidewards, forwards) == 0) ? 0 : Math.toDegrees(Math.atan2(forwards, sidewards)) - 90;
            double currentTurningMotorRotationRelativeToField;
            if(fieldCentric) {
                currentTurningMotorRotationRelativeToField  = currentHeading + robotIMUHeading;
            } else {
                currentTurningMotorRotationRelativeToField = currentHeading;
            }
            double rotationToMakeTowardsDesiredHeading = headingToTakeTowardsTarget(desiredFieldAngle, currentTurningMotorRotationRelativeToField);
            double totalHeading = currentTurningMotorRotationRelativeToField + rotationToMakeTowardsDesiredHeading;
            double finalizedHeading = Math.abs(totalHeading) > 90 ? headingToTakeTowardsTarget(totalHeading < 0 ? desiredFieldAngle + 180 : desiredFieldAngle - 180,
                    currentTurningMotorRotationRelativeToField) : rotationToMakeTowardsDesiredHeading;
            currentHeading += finalizedHeading;
            currentHeading = headingToTakeTowardsTarget(currentHeading, 0);
            return finalizedHeading;
        }

        private double headingToTakeTowardsTarget(double target, double current) {
            return (target - current + 540) % 360 - 180;
        }

    }
}
