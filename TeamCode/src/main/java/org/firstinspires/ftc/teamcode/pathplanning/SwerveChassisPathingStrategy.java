package org.firstinspires.ftc.teamcode.pathplanning;


import org.firstinspires.ftc.teamcode.pid.PIDControl;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.GamepadExWrapper;
import org.firstinspires.ftc.teamcode.subsystems.SwerveChassis;


public class SwerveChassisPathingStrategy implements ChassisPathingStrategy {
    private final SwerveChassis swerveChassis;
    private final GamepadExWrapper gamepadExWrapper;

    public SwerveChassisPathingStrategy(SwerveChassis swerveChassis, GamepadExWrapper gamepadExWrapper) {
        this.swerveChassis = swerveChassis;
        this.gamepadExWrapper = gamepadExWrapper;
        this.gamepadExWrapper.setAutomaticValueReturn(false);
    }
    @Override
    public void rotate(double degrees) throws Exception {
        /*We need to make a PID loop to basically allow the robot to turn until it has reached its specific angle.*/
        gamepadExWrapper.setLeftX(0);
        gamepadExWrapper.setLeftY(0);
        swerveChassis.setRotate(true);

        PIDControl control = swerveChassis.getPIDControlForTurningMotors();
        control.setPIDForIMU(true);
        control.setIMUTargetPosition(degrees, null);

        swerveChassis.function();
        while(!control.allWheelsAtSetPoint()) {
            try {
                Thread.sleep(10);
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }

        while(!control.imuAtSetPoint()) {
            try {
                Thread.sleep(10);
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }


    }


    @Override
    public void driveForwardOrBackward(double magnitude) throws Exception {

    }

    @Override
    public Chassis getChassis() {
        return swerveChassis;
    }
}
