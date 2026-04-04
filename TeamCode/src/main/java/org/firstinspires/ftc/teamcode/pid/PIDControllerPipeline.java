package org.firstinspires.ftc.teamcode.pid;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*a  b
* b  a*/

public class PIDControllerPipeline {

    private final PIDTurnToCommand turnToCommand;
    private final PIDMoveCommand moveCommand;

    public PIDControllerPipeline(double angleToTurnToFirst, Collection<IMUAngularModule> angularModules, double inches, Collection<LinearPIDModule> linearPIDModules) {
        turnToCommand = new PIDTurnToCommand(angleToTurnToFirst, angularModules);
        moveCommand = new PIDMoveCommand(inches, linearPIDModules, false);
    }

    public PIDTurnToCommand getTurnToCommand() {
        return turnToCommand;
    }

    public PIDMoveCommand getMoveCommand() {
        return moveCommand;
    }

    public final static class PIDTurnToCommand extends CommandBase {
        private final List<IMUAngularModule> modules = new ArrayList<>();
        private final double frontLeftBackLeftMultiplier,
        frontRightBackRightMultiplier;
        
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
    
    public static final class PIDMoveCommand extends CommandBase {
        private final List<LinearPIDModule> modules = new ArrayList<>();
        private double frontLeftBackRightMultiplier = 1, frontRightBackLeftMultiplier = 1;
        private final boolean strafe;

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