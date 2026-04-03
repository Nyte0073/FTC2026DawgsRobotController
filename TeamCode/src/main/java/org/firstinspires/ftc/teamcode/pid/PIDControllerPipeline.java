package org.firstinspires.ftc.teamcode.pid;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PIDControllerPipeline {

    private final PIDTurnToCommand turnToCommand;
    private final PIDMoveCommand moveCommand;

    public PIDControllerPipeline(double angleToTurnToFirst, Collection<IMUAngularModule> angularModules, double inches, Collection<LinearPIDModule> linearPIDModules) {
        turnToCommand = new PIDTurnToCommand(angleToTurnToFirst, angularModules);
        moveCommand = new PIDMoveCommand(inches, linearPIDModules);
    }

    public PIDTurnToCommand getTurnToCommand() {
        return turnToCommand;
    }

    public PIDMoveCommand getMoveCommand() {
        return moveCommand;
    }

    public final static class PIDTurnToCommand extends CommandBase {
        private final List<IMUAngularModule> modules = new ArrayList<>();
        
        public PIDTurnToCommand(double angle, Collection<IMUAngularModule> modules) {
            this.modules.addAll(modules);
            for(IMUAngularModule module : this.modules) {
                module.setTarget(angle);
            }
        }

        @Override
        public void execute() {
            for(IMUAngularModule module : modules) {
                Motor m = module.getMotor();
                m.set(Math.min(0.65, module.calculate()));
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

        public PIDMoveCommand(double inches, Collection<LinearPIDModule> modules) {
            this.modules.addAll(modules);
            for(PIDModule module : this.modules) {
                module.setTarget(inches);
            }
        }

        @Override
        public void execute() {
            for(LinearPIDModule m : modules) {
                Motor motor = m.getMotor();
                motor.set(Math.min(0.65, m.calculate()));
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