package org.firstinspires.ftc.teamcode.pid;

import android.os.SystemClock;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.subsystems.MotorWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PIDControl {
    private final Map<MotorWrapper, PIDControllerWrapper> motorsToControllers = new HashMap<>();
    private final Map<MotorWrapper, ChassisPIDKinematics> motorsToKinematics = new HashMap<>();
    private boolean usesAngularOrientation = false;
    private final ExecutorService service = Executors.newSingleThreadExecutor();

    public PIDControl(List<MotorWrapper> motors, ChassisPIDKinematics.PIDKinematicsType kinematicsType, boolean resetEncoders) {
        for(MotorWrapper motorWrapper : motors) {
        ChassisPIDKinematics kinematics = ChassisPIDKinematics.instance(kinematicsType, motorWrapper);
        motorWrapper.setDistancePerPulse(kinematics.getDistancePerPulse());
        motorsToControllers.put(motorWrapper, new PIDControllerWrapper(
                    kinematics.getKP(), kinematics.getKI(), kinematics.getKD()
            ));
        motorsToKinematics.put(motorWrapper, kinematics);
        }
        if(resetEncoders) resetMotorEncoders();
        start();
    }

    public void resetMotorEncoders() {
        for(MotorWrapper motorWrapper : motorsToControllers.keySet()) {
            motorWrapper.resetEncoder();
        }
    }

    public boolean allWheelsAtSetPoint() {
        for(PIDControllerWrapper PIDControllerWrapper : motorsToControllers.values()) {
            if(!PIDControllerWrapper.atSetPoint()) return false;
        }
        return true;
    }

    public boolean wheelAtSetPoint(MotorWrapper motorWrapper) {
        if(!ensureMotorWrapperPresent(motorWrapper)) {
            throw new RuntimeException("Motor not present!");
        }
        return motorsToControllers.get(motorWrapper).atSetPoint();
    }

    public void setUsesAngularOrientation(boolean usesAngularOrientation) {
           this.usesAngularOrientation = usesAngularOrientation;
           Map<MotorWrapper, Double> map = new HashMap<>();
           for(MotorWrapper wrapper : motorsToControllers.keySet()) {
               map.put(wrapper, 0.0);
           }
           setWheelsTargetPositions(map);
    }

    public boolean usesAngularOrientation() {
        return usesAngularOrientation;
    }

    /*For this method, make sure that each wheel gets a specific target position. Don't just set the same target position
    * for all the wheels because if they are rotating or are part of a mecanum drive chassis, then the wheels will need to have their
    * positions set to different values, or if the wheels are not perfectly synced then you will need to make sure that every wheel
    * gets a compensated target position to sync them back up again in the next drive movement sequence.*/
    public void setWheelsTargetPositions(Map<MotorWrapper, Double> motorsToTargetPositions) {
            for(Map.Entry<MotorWrapper, Double> entry : motorsToTargetPositions.entrySet()) {
                MotorWrapper m = entry.getKey();
                if(ensureMotorWrapperPresent(m)) {
                    motorsToControllers.get(m).setSetPoint(entry.getValue());
                }

        }
    }

    public void setWheelTargetPosition(MotorWrapper motorWrapper, double targetPosition) {
        if(ensureMotorWrapperPresent(motorWrapper)) {
            motorsToControllers.get(motorWrapper).setSetPoint(targetPosition);
        }
    }

    private boolean ensureMotorWrapperPresent(MotorWrapper motorWrapper) {
        return motorsToControllers.keySet().contains(motorWrapper) && motorWrapper != null;
    }

    public double pidCalculateForMotor(MotorWrapper motorWrapper) {
        ChassisPIDKinematics kinematics = ensureMotorWrapperPresent(motorWrapper) ? motorsToKinematics.get(motorWrapper) : null;
        return kinematics != null ?  motorsToControllers.get(motorWrapper).calculate(
            usesAngularOrientation ? kinematics.getAngularOrientation(AngleUnit.DEGREES) : kinematics.getDistance()
        ) : null;
    }

    private void start() {
        service.submit(() -> {
            double calculate;
            while(!Thread.currentThread().isInterrupted() && !service.isShutdown()) {
                try {
                    for(MotorWrapper motorWrapper : motorsToControllers.keySet()) {
                        calculate = pidCalculateForMotor(motorWrapper);
                        motorWrapper.set(calculate);
                    }
                } catch(Exception e) {
                    service.shutdown();
                }
            }
            SystemClock.sleep(10);
        });
    }
}
