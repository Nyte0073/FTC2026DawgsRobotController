package org.firstinspires.ftc.teamcode.pid;

/*What does the PIDControlSystem need in order to function properly everywhere?
*
* Well, it needs:
*
* - A way to supply itself with input to be able to calculate PID errors and correctional output with.
* - The classes instances of HardwareComponents it will be applying this correctional output to.
* - A method to possibly change the input for the PID to use to calculate correctional output.
* - A PIDController instance to calculate the correctional output.*/
public class PIDControlSystem {

    private PIDControlSystemConstraints pidControlSystemConstraints;

    public void setPidControlSystemConstraints(PIDControlSystemConstraints pidControlSystemConstraints) {
        this.pidControlSystemConstraints = pidControlSystemConstraints;
    }

    public PIDControlSystemConstraints getPidControlSystemConstraints() {
        return pidControlSystemConstraints;
    }

    public void setTargetPosition(double targetPosition) {
//        System.out.println("PID Controller is null: " + (pidControlSystemConstraints.getControllerWrapper() == null));
        pidControlSystemConstraints.getControllerWrapper().setSetPoint(targetPosition);
    }

    public double getPIDOutput() {
        return pidControlSystemConstraints.getControllerWrapper().
                calculate(pidControlSystemConstraints.getPidInput().getAsDouble());
    }

    public double getTargetPosition() {
        System.out.println("Controller wrapper is null: " + (pidControlSystemConstraints.getControllerWrapper() == null));
        return pidControlSystemConstraints.getControllerWrapper().getTargetPosition();
    }


}
