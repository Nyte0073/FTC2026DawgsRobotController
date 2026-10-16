package org.firstinspires.ftc.teamcode.pathplanning;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import java.util.LinkedList;
import java.util.List;

/*Class is going to be here to combine multiple path trajectories into a single, complex path that any robot will be able to take.
* Because the PathPlanner class can handle pathing for many different types of drivetrains, this means that there has to be an abstract
* extension in where it uses a different strategy to calculate the motor powers for the different actions the user wants to take
* to the desired path.*/
public class PathPlanner {

    private final List<PathTrajectory> pathTrajectories = new LinkedList<>();

    public void addToPathTrajectories(PathTrajectory trajectory) {
        pathTrajectories.add(trajectory);
    }

    public SequentialCommandGroup buildPathPlanningCommandGroup() {
        SequentialCommandGroup sequentialCommandGroup = new SequentialCommandGroup();
        for (PathTrajectory trajectory : pathTrajectories) {
            sequentialCommandGroup.addCommands(
                    new CommandBase() {
                        @Override
                        public void execute() {
                            ChassisPathingStrategy chassisPathingStrategy = trajectory.getChassisPathingStrategy();
                            if (trajectory.isRotateToCorrectHeading()) {
                                try {
                                    chassisPathingStrategy.rotate(trajectory.getPathHeading());
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            try {
                                chassisPathingStrategy.driveForwardOrBackward(trajectory.getTrajectoryMagnitude());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public boolean isFinished() {
                            try {
                                /*todo fix this later so that this method doesn't constantly call the pid control instance over and over again in a
                                *  brand new setting. Make it so that it sets a pre-set PIDControl variable to the instance returned by the getter method
                                * (only if the variable was null to begin with), and then use that same variable in the boolean check calls. This will
                                * make sure that the CPU isn't burning returning a brand new fresh instance of this variable every single time, it just
                                * uses the same variable that can be populated if it is currently null.*/
                                return trajectory.getChassisPathingStrategy().getPIDControl().allWheelsAtSetPoint();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
            );
        }
        return sequentialCommandGroup;
    }
}
