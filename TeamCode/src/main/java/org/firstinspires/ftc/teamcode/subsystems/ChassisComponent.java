package org.firstinspires.ftc.teamcode.subsystems;

import android.util.Log;

import java.util.List;

/*What does a general drivetrain need in order to function properly everywhere? Well,
* a drivetrain a way to get input from the user's controller, so the drivetrain needs a method (using a GamepadExWrapper)
* to return the forward, backward and rotation motions of the robot.
*
* The drivetrain also needs a way to have its driving and/or turning motors controlled by PID systems that can depend on any type of input
* they are supplied to calculate errors for target positions they have set relative to the input they are being supplied.
*
* A drivetrain also needs a way to differentiate between Teleop and Autonomous runs, and be able to execute differently depending on the
* choice of mode they want to run.
*
* Also for Autonomous runs, it would be nice to have a supplier that simply supplies an autonomous script in which you can simply drop your
* drivetrain instance into to start running right away, and in this way, all the user really has to do is input an Autonomous mode enum type,
* and then they are good to go. <-- todo this note specifically for the supplier, this can be worked out later in detail.*/
public abstract class ChassisComponent implements HardwareComponent {

    private ChassisComponentConstraints chassisComponentConstraints;

    public void setChassisComponentConstraints(ChassisComponentConstraints chassisComponentConstraints) {
        this.chassisComponentConstraints = chassisComponentConstraints;
    }

    public ChassisComponentConstraints getChassisComponentConstraints() {
        return chassisComponentConstraints;
    }

    public boolean isOptimizeForShortestRotation() {
        return chassisComponentConstraints.isOptimizeForShortestRotation();
    }

    public double getLeftX() {
        return chassisComponentConstraints.getGamepadExWrapper().getLeftX();
    }

    public double getLeftY() {
        return chassisComponentConstraints.getGamepadExWrapper().getLeftY();
    }

    public double getRightX() {
        return chassisComponentConstraints.getGamepadExWrapper().getRightX();
    }

    public double getRightY() {
        return chassisComponentConstraints.getGamepadExWrapper().getRightY();
    }

    public void setTargetPositionForTurningMotor(double targetPositionInTicks, MotorExWrapper motorExWrapper) {
        /*Update this so that it uses a map that maps specific driving motors to specific PIDControlSystem's.*/
//        System.out.println("Turning is motor is in map: " + chassisComponentConstraints.getPIDSystemForTurningMotor(motorExWrapper) != null);
        chassisComponentConstraints.getPIDSystemForTurningMotor(motorExWrapper).setTargetPosition(targetPositionInTicks);
    }

    public void setTargetPositionForDrivingMotor(double targetPositionInTicks, MotorExWrapper motorExWrapper) {
        /*Update this so that it uses a map that maps specific driving motors to specific PIDControlSystem's.*/
        chassisComponentConstraints.getPIDSystemForDrivingMotor(motorExWrapper).setTargetPosition(targetPositionInTicks);
    }

    public double getCalculateForTurningMotor(MotorExWrapper motorExWrapper) {
        /*Update this so that it uses a map that maps specific driving motors to specific PIDControlSystem's.*/
        return chassisComponentConstraints.getPIDSystemForTurningMotor(motorExWrapper).getPIDOutput();
    }

    public double getCalculateForDrivingMotor(MotorExWrapper motorExWrapper) {
        /*Update this so that it uses a map that maps specific driving motors to specific PIDControlSystem's.*/
        return chassisComponentConstraints.getPIDSystemForDrivingMotor(motorExWrapper).getPIDOutput();
    }

    public double getTargetPositionForTurningMotor(MotorExWrapper motorExWrapper) {
        return chassisComponentConstraints.getPIDSystemForTurningMotor(motorExWrapper).getTargetPosition();
    }

    public double getTargetPositionForDrivingMotor(MotorExWrapper motorExWrapper) {
        return chassisComponentConstraints.getPIDSystemForDrivingMotor(motorExWrapper).getTargetPosition();
    }

    public boolean pidSystemAtSetPointForDrivingMotor(MotorExWrapper motorExWrapper) {
        return chassisComponentConstraints.pidSystemForDrivingMotors.get(motorExWrapper.getMotorName())
                .getPidControlSystemConstraints().getControllerWrapper().atSetPoint();
    }

    public boolean pidSystemAtSetPointForTurningMotor(MotorExWrapper motorExWrapper) {
        return chassisComponentConstraints.pidSystemForTurningMotors.get(motorExWrapper.getMotorName())
                .getPidControlSystemConstraints().getControllerWrapper().atSetPoint();
    }

    public boolean pidSystemAtSetPointForTurningMotors(List<MotorExWrapper> turningMotors) {
        for(MotorExWrapper m : turningMotors) {
            if(!pidSystemAtSetPointForTurningMotor(m)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void function() throws Exception {
        try {
            if(isDrivingEnabled()) {
                drive();
            } else {
                stopEverything();
            }
        } catch(Exception e) {
            Log.e(getClass().getSimpleName(), String.format("%s %s", "Exception happened in ", getClassName()));
            Thread.sleep(8000);
        }
    }

    public abstract void drive();
    public abstract boolean isDrivingEnabled();
    public abstract void stopEverything();
    public abstract String getClassName();

}
