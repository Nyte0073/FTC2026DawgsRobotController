package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pid.ChassisPIDKinematics;
import org.firstinspires.ftc.teamcode.pid.PIDControl;
import org.firstinspires.ftc.teamcode.pid.PIDIMUWrapper;
import org.firstinspires.ftc.teamcode.pid.PIDWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*Note for this class: try to include a boolean flag for where if the optimized heading calculates a value for the motor to go to that
* puts it in the opposite direction of its initial total heading, then make sure to reverse the motor power to counteract for the
* flip in the direction.*/
public class SwerveChassis extends Chassis {

    public final SwerveModule[] swerveModules;
    private volatile boolean previousDrivingActionCompleted = true;
    private final ExecutorService mainWorkerService = Executors.newSingleThreadExecutor(),
    secondaryWorkerService;
    private final Map<MotorWrapper, Future<?>> completedTargetPositionSettingFutures = new HashMap<>();

    public SwerveChassis(Telemetry telemetry, HardwareMap hardwareMap, Map<MotorWrapper, MotorWrapper> drivingMotorsToToTurningMotors, GamepadExWrapper gamepadEx, PIDIMUWrapper pidimuWrapper)  {
        super(telemetry, hardwareMap, new ArrayList<>(drivingMotorsToToTurningMotors.values()), gamepadEx);
        swerveModules = new SwerveModule[drivingMotorsToToTurningMotors.size()];

        secondaryWorkerService = Executors.newFixedThreadPool(drivingMotorsToToTurningMotors.size());
        int i = 0;
        for(Map.Entry<MotorWrapper, MotorWrapper> entry : drivingMotorsToToTurningMotors.entrySet()) {
            swerveModules[i] = new SwerveModule(entry.getKey(), entry.getValue());
            i++;
        }
        List<PIDWrapper> pidWrappersForTurningMotors = new ArrayList<>();
        List<PIDWrapper> pidWrappersForDrivingMotors = new ArrayList<>();


        for(SwerveModule module : swerveModules) {
            pidWrappersForTurningMotors.add(module.turningMotorWrapper);
            pidWrappersForDrivingMotors.add(new PIDWrapper(module.drivingMotor, ChassisPIDKinematics.PIDKinematicsType.SWERVE_KINEMATICS));
        }
            setPIDControlForTurningMotors(new PIDControl(pidWrappersForTurningMotors, true));
            if(pidimuWrapper != null) {
                setPIDControlForDrivingMotors(new PIDControl(pidWrappersForDrivingMotors, true, pidimuWrapper, false));
            }
    }

    public SwerveModule[] getSwerveModules() {
        return swerveModules;
    }

    @Override
    public void drive(boolean rotate) {
        PIDControl pidControl = getPIDControlForTurningMotors();
        long time = System.nanoTime();
        if(!previousDrivingActionCompleted) {
            return;
        }
        /*Note: transform rotate into forward and sideward vectors.*/
        previousDrivingActionCompleted = false;
        double forward = getForward(), sideward = getSideward(), rotation = getRotation();
        double targetHeading = ChassisMath.calculateTargetHeading.apply(forward, sideward);

        mainWorkerService.submit(() -> {
                for(SwerveModule module : swerveModules) {
                    MotorWrapper turningMotor = module.turningMotorWrapper.getPidControlledMotor();
                    double currentPositionTicks =  turningMotor.getCurrentPosition();
                    double currentPositionDegrees = ChassisMath.currentMotorPositionTicksToDegrees.apply(currentPositionTicks, turningMotor.getCPR());
                    double currentTargetHeadingDifference = ChassisMath.calculateCurrentTargetHeadingDifference.apply(
                            rotate ? SubsystemConstants.SWERVE_TURNING_MOTOR_ROTATION_POSITIONS.get(turningMotor.getMotorName())
                            : targetHeading, currentPositionDegrees
                    );
                    double totalHeading = currentPositionDegrees + currentTargetHeadingDifference;
                    double optimizedHeading = ChassisMath.optimizeHeading.apply(totalHeading, currentPositionDegrees);

                    completedTargetPositionSettingFutures.put(turningMotor, secondaryWorkerService.submit(
                            () -> {
                                pidControl.setWheelTargetPosition(module.turningMotorWrapper, ChassisMath.degreesToTicks.apply(optimizedHeading,
                                        turningMotor.getCPR()));

                                while(!Thread.currentThread().isInterrupted() && !pidControl.wheelAtSetPoint(module.turningMotorWrapper)) {
                                    try {
                                        Thread.sleep(10);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                    ));
                }

            for(Future<?> future : completedTargetPositionSettingFutures.values()) {
                try {
                    future.get();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            double drivingMotorPower = rotate ? rotation : ChassisMath.vectorMagnitude.apply(forward, sideward);
            for(SwerveModule swerveModule : swerveModules) {
                swerveModule.drivingMotor.set(drivingMotorPower);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Time elapsed: " + ((System.nanoTime() - time) / 1e9));
            previousDrivingActionCompleted = true;
        });

    }

    @Override
    public boolean drivingEnabled() {
        return true;
    }

    @Override
    public void shutdown() {
        mainWorkerService.shutdownNow();
        secondaryWorkerService.shutdownNow();
    }

    @Override
    public void stopMotors() {
        for(SwerveModule module : swerveModules) {
            module.turningMotorWrapper.getPidControlledMotor().stopMotor();
            module.drivingMotor.stopMotor();
        }
    }

    @Override
    public Map<String, Object> getTelemetryInformation() {
        return Collections.emptyMap();
    }

    public static final class SwerveModule {
        public final MotorWrapper drivingMotor;
        public final PIDWrapper turningMotorWrapper;

        public SwerveModule(MotorWrapper drivingMotor, MotorWrapper turningMotor) {
            this.drivingMotor = drivingMotor;
            turningMotorWrapper = new PIDWrapper(turningMotor, ChassisPIDKinematics.PIDKinematicsType.SWERVE_KINEMATICS);
        }
    }
}
