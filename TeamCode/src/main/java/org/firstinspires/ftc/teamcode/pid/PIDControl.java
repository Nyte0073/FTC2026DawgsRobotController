package org.firstinspires.ftc.teamcode.pid;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PIDControl {
    private boolean usesAngularOrientation = false;
    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private List<PIDWrapper> pidWrappers = new ArrayList<>();

    public PIDControl(List<PIDWrapper> pidWrappers, boolean resetEncoders) throws Exception {
        this.pidWrappers.addAll(pidWrappers);
        if(resetEncoders) { resetMotorEncoders(); }
        start();
    }

    public void resetMotorEncoders() {
       for(PIDWrapper pidWrapper : pidWrappers) {
           pidWrapper.getPidControlledMotor().resetEncoder();
       }
    }

    public boolean allWheelsAtSetPoint() {
       for(PIDWrapper pidWrapper : pidWrappers) {
           if(!pidWrapper.getPidController().atSetPoint()) return false;
       }
       return true;
    }

    public boolean wheelAtSetPoint(PIDWrapper pidwrapper) {
       return pidwrapper.getPidController().atSetPoint();
    }

    public void setUsesAngularOrientation(boolean usesAngularOrientation) {
           this.usesAngularOrientation = usesAngularOrientation;
    }

    public boolean usesAngularOrientation() {
        return usesAngularOrientation;
    }

    /*For this method, make sure that each wheel gets a specific target position. Don't just set the same target position
    * for all the wheels because if they are rotating or are part of a mecanum drive chassis, then the wheels will need to have their
    * positions set to different values, or if the wheels are not perfectly synced then you will need to make sure that every wheel
    * gets a compensated target position to sync them back up again in the next drive movement sequence.*/
    public void setWheelsTargetPositions(Map<PIDWrapper, Double> wrappersToTargetPositions) {
        for(Map.Entry<PIDWrapper, Double> entry : wrappersToTargetPositions.entrySet()) {
            entry.getKey().getPidController().setSetPoint(entry.getValue());
        }
    }

    public void setWheelTargetPosition(PIDWrapper pidWrapper, double targetPosition) {
        pidWrapper.getPidController().setSetPoint(targetPosition);
    }

    private boolean ensureMotorWrapperPresent(PIDWrapper pidWrapper) {
        return pidWrapper.getPidControlledMotor() != null;
    }

    public double pidCalculateForMotor(PIDWrapper pidWrapper) {
        boolean kinematicsNotNull = pidWrapper.getChassisPIDKinematics() != null;
        double returnedCalculate;
        if(usesAngularOrientation && kinematicsNotNull) {
            returnedCalculate = pidWrapper.getPidController().calculate(
                    pidWrapper.getChassisPIDKinematics().getAngularOrientation(AngleUnit.DEGREES)
            );
        } else if(!usesAngularOrientation) {
            returnedCalculate = pidWrapper.getPidController().calculate(
                    pidWrapper.getChassisPIDKinematics().getDistance());
        } else {
            returnedCalculate = 0;
        }
        return returnedCalculate;
    }

    private void start() {
        service.submit(() -> {
            double calculate, initialCalc;
            while(!Thread.currentThread().isInterrupted()) {
                try {
                    for(PIDWrapper pidWrapper : pidWrappers) {
                        initialCalc = pidCalculateForMotor(pidWrapper);
                        calculate = Math.min(1, Math.abs(initialCalc)) * Math.signum(initialCalc);
                        pidWrapper.getPidControlledMotor().set(calculate);
                    }
                } catch(Exception e) {
                    System.out.println("Exception happened.");
                    service.shutdown();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }

    public double getSetPoint(PIDWrapper pidWrapper) {
        return pidWrapper.getPidController().getSetPoint();
    }
}
