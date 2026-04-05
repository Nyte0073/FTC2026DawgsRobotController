package org.firstinspires.ftc.teamcode.pid;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*a  b
* b  a*/

/**Class that generates a pipeline of rotate + drive commands based on input from the user about an angle to rotate to and a certain amount of
 * inches for the robot to translate forward or backward.*/
public class PIDControllerPipeline {

    /**The command base extension controlling the robot to rotate using a PIDController to track position and generate corresponding error output.*/
    private final PIDTurnToCommand turnToCommand;

    /**The command base extension controlling the robot to translate a certain amount of inches forward or backward using a PIDController to track
     * position and generate corresponding error output.*/
    private final PIDMoveCommand moveCommand;

    /**Constructs a new {@code PIDControllerPipeline()} with initialized turn and move commands to generate the robot's path planning structure.*/
    public PIDControllerPipeline(double angleToTurnToFirst, Collection<IMUAngularModule> angularModules, double inches, Collection<LinearPIDModule> linearPIDModules) {
        turnToCommand = new PIDTurnToCommand(angleToTurnToFirst, angularModules);
        moveCommand = new PIDMoveCommand(inches, linearPIDModules, false);
    }

    /**@return The command to make the robot rotate to a certain angle.*/
    public PIDTurnToCommand getTurnToCommand() {
        return turnToCommand;
    }

    /**@return The command to make the robot translate a certain distance.*/
    public PIDMoveCommand getMoveCommand() {
        return moveCommand;
    }

    /**Command base subclass to make the robot turn to a certain angle using a PIDController to control the magnitude of the motor
     * output.*/
    public final static class PIDTurnToCommand extends CommandBase {

        /**List of PID-controlled modules controlling all the motor outputs using the error between the current and target angular position.*/
        private final List<IMUAngularModule> modules = new ArrayList<>();

        /**Motor power multiplier to make the front and back left motors spin forward or backward to help make the robot turn rotate
         * clockwise or counterclockwise.*/
        private final double frontLeftBackLeftMultiplier,

        /**Motor power multiplier to make the front and back right motors spin backward or forward to help make the robot rotate clockwise or
         * counterclockwise.*/
        frontRightBackRightMultiplier;

        /**Constructs a new {@code PIDTurnToCommand()} that initializes all the turning PID-controlled modules with their target being set
         * to the user-inputted angle that the user wants the robot to turn to. This constructor also initializes the motor multipliers, setting them to
         * a specific configuration to make the robot rotate either right or left depending on if the angle is a positive or negative rotation.*/
        public PIDTurnToCommand(double angle, Collection<IMUAngularModule> modules) {
            frontLeftBackLeftMultiplier = angle < 0 ? 1 : -1;
            frontRightBackRightMultiplier = angle < 0 ? -1 : 1;
            this.modules.addAll(modules);
            for(IMUAngularModule module : this.modules) {
                module.setTarget(angle);
            }
        }

        @Override
        public void execute() {
            for(IMUAngularModule module : modules) {
                Motor m = module.getMotor();
                if(m == Constants.MecanumConstants.turningMotors.get(0) || m == Constants.MecanumConstants.turningMotors.get(2)) {
                    m.set(Math.min(0.65, module.calculate()) * frontLeftBackLeftMultiplier);
                } else {
                    m.set(Math.min(0.65, module.calculate()) * frontRightBackRightMultiplier);
                }
            }
        }

        @Override
        public boolean isFinished() {
            for(IMUAngularModule module : modules) {
                if(!module.atTarget()) return false;
            }
            return true;
        }
    }

    /**Command base subclass to make the robot translate a certain distance forward or backward depending on the sign of the inches variable inputted
     * by the user. This class uses PID-generated error output to control the magnitude of the motors' power set to them depending on the difference
     * between the motors' current and target set of inches.*/
    public static final class PIDMoveCommand extends CommandBase {

        /**List of PID-controlled modules that control the magnitude of the motors' powers when either far away or approaching their
         * set target of inches.*/
        private final List<LinearPIDModule> modules = new ArrayList<>();

        /**Multiplier to set to the front left and back right motors to help make the robot strafe left or right depending on the sign of the inches variable
         * set in the constructor.*/
        private double frontLeftBackRightMultiplier = 1,

        /**Multiplier to set to the front right and back left motors to help make the robot strafe left or right depending on the sign of the inches variable
         * set in the constructor.*/
        frontRightBackLeftMultiplier = 1;

        /**State of whether the robot wants to strafe to the target or not.*/
        private final boolean strafe;

        /**Constructs a new {@code PIDMoveCommand()} that initializes the strafing boolean state, updates the values of the motor strafing multipliers
         * according to the value of the strafe state, and then initializes the PID-controlled modules by setting their targets to the value of the inches
         * variables.*/
        public PIDMoveCommand(double inches, Collection<LinearPIDModule> modules, boolean strafe) {
            this.strafe = strafe;
            if(strafe && inches > 0) {
                frontLeftBackRightMultiplier = 1;
                frontRightBackLeftMultiplier = -1;
            } else if(strafe) {
                frontLeftBackRightMultiplier = -1;
                frontRightBackLeftMultiplier = 1;
            }
            this.modules.addAll(modules);
            for(PIDModule module : this.modules) {
                module.setTarget(inches);
            }
        }

        @Override
        public void execute() {
            for(LinearPIDModule m : modules) {
                Motor motor = m.getMotor();
                double calculate = m.calculate();
                if((motor == Constants.MecanumConstants.turningMotors.get(0) || motor == Constants.MecanumConstants.turningMotors.get(3)) && strafe) {
                    motor.set(Math.min(0.65, Math.abs(calculate)) * frontLeftBackRightMultiplier);
                } else if(strafe) {
                    motor.set(Math.min(0.65, Math.abs(calculate)) * frontRightBackLeftMultiplier);
                } else {
                    motor.set(Math.min(0.65 * Math.signum(calculate), calculate));
                }
            }
        }

        @Override
        public boolean isFinished() {
            for(LinearPIDModule module : modules) {
                if(!module.atTarget()) return false;
            }
            return true;
        }
    }
    
}