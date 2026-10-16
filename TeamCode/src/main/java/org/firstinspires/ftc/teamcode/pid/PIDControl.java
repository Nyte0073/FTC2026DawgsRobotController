package org.firstinspires.ftc.teamcode.pid;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*Note for this class: Integrate a method where it relies on an angle wrapping kind of class to act as a setpoint */

public class PIDControl {
    /*todo: make it so that this class can either run with motors or the gyroscope system on the robot, this way, it can handle pid for the robots'
    *  drivetrains driving and for the gyroscope systems tracking rotation in real time, making the class more versatile. You can do this by changing
    * the way the setter methods for the target positions of the motors work. Make it so that the methods have overloaded versions of themselves
    * that can use angular settings instead, or if you don't want to make overloaded methods, then make a tiny helper class that can store both motor
    * target positions and angular values, thus getting the user to put in an instance of this class into the one method that can use either value depending
    * on the state of the boolean flag indicating rotation usage or not.*/
    private boolean usesAngularOrientation = false, pidForIMU = false;
    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private List<PIDWrapper> pidWrappers = new ArrayList<>();
    private PIDIMUWrapper pidimuWrapper;

    public PIDControl(List<PIDWrapper> pidWrappers, boolean resetEncoders) {
        this.pidWrappers.addAll(pidWrappers);
        if(resetEncoders) { resetMotorEncoders(); }
        start();
    }

    public PIDControl(List<PIDWrapper> pidWrappers, boolean resetEncoders, PIDIMUWrapper pidimuWrapper, boolean resetYaw) {
        this.pidWrappers.addAll(pidWrappers);
        if(resetEncoders) {
            resetMotorEncoders();
        }
        this.pidimuWrapper = pidimuWrapper;
        if(resetYaw) {
            this.pidimuWrapper.getPidControlledIMUWrapper().resetYaw();
        }
        start();
    }

    public PIDControl(PIDIMUWrapper pidimuWrapper, boolean resetYaw) {
        this.pidimuWrapper = pidimuWrapper;
        if(resetYaw) pidimuWrapper.getPidControlledIMUWrapper().resetYaw();
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

    public boolean imuAtSetPoint() {
        return pidimuWrapper.getPidControllerWrapper().atSetPoint();
    }

    public void setUsesMotorAngularOrientation(boolean usesAngularOrientation) {
           this.usesAngularOrientation = usesAngularOrientation;
    }

    public void setPIDForIMU(boolean pidForIMU) {
        this.pidForIMU = pidForIMU;
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

    public void setIMUTargetPosition(double targetDegrees, PIDIMUWrapper wrapper) {
        pidimuWrapper.getPidControllerWrapper().setSetPoint(targetDegrees);
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

    public double pidCalculateForIMU(PIDIMUWrapper pidimuWrapper) {
        double s = pidimuWrapper.getPidControlledIMUWrapper().getYaw(AngleUnit.DEGREES);
        System.out.println("Current angle: " + s);
        return pidimuWrapper.getPidControllerWrapper().calculate(
                s
        );
    }

    private void start() {
        service.submit(() -> {
            double calculate, initialCalc;
            while(!Thread.currentThread().isInterrupted() && !service.isShutdown()) {
                if(pidForIMU) {
                    try {
                        Thread.sleep(70);
                    } catch(Exception e) {
                        throw new RuntimeException(e);
                    }
                    initialCalc = pidCalculateForIMU(pidimuWrapper);
                    calculate = Math.min(1, Math.abs(initialCalc)) * Math.signum(initialCalc);
                    for(PIDWrapper wrapper : pidWrappers) {
                        wrapper.getPidControlledMotor().set(calculate);
                    }
                    pidimuWrapper.getPidControlledIMUWrapper().simulateMotion(calculate);
                } else {
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
            }

        });
    }

    public double getSetPoint(PIDWrapper pidWrapper) {
        return pidWrapper.getPidController().getSetPoint();
    }
}
