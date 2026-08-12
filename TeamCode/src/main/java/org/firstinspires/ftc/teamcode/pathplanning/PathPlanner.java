package org.firstinspires.ftc.teamcode.pathplanning;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

public class PathPlanner {
    private final Trajectory[] trajectories;
    private final Drivetrain drivetrain;
    private final boolean fieldOriented;

    public PathPlanner(Trajectory[] trajectories, Drivetrain drivetrain, boolean fieldOriented) {
        this.trajectories = trajectories;
        this.drivetrain = drivetrain;
        this.fieldOriented = fieldOriented;
    }

    public SequentialCommandGroup buildCommands() {
        SequentialCommandGroup commandGroup = new SequentialCommandGroup();
        for(Trajectory trajectory : trajectories) {
            commandGroup.addCommands(
                    new CommandBase() {
                        @Override
                        public void initialize() {
                            /*Put something here, most of the init will occur
                            * in the main setup area anyway. Or just leave it, doesn't matter to me.*/
                        }

                        @Override
                        public void execute() {
                            try {
                                drivetrain.drive(fieldOriented, true, trajectory);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public boolean isFinished() {
                            return drivetrain.getPIDController().atSetPoint();
                        }

                        @Override
                        public void end(boolean interrupted) {
                            drivetrain.stop();
                            drivetrain.getPIDController().reset();
                        }
                    }
            );
        }
        return commandGroup;
    }
}
