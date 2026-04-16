package org.firstinspires.ftc.teamcode.subsystems.driveables.factories.pidfactories;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.driveables.Driveable;
import java.util.List;

/**Blueprint to follow for building a list of commands for the robot to follow to drive along a specific path.*/
@FunctionalInterface
public interface PIDFactory {

    /**@return The collection of turn and move commands created in the {@link org.firstinspires.ftc.teamcode.pid.PIDControllerPipeline} class
     * to make the robot turn and drive to specific angles/inches.*/
    SequentialCommandGroup buildCommandGroup(Driveable driveable, List<PathSegment> anglesAndInches);
}
