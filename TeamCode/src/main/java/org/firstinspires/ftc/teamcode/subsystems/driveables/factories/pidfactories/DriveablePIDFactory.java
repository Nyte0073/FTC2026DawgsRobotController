package org.firstinspires.ftc.teamcode.subsystems.driveables.factories.pidfactories;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.pid.PIDControllerPipeline;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Driveable;
import org.firstinspires.ftc.teamcode.subsystems.driveables.factories.FactoryConstants;

import java.util.LinkedList;
import java.util.List;

/**This class is used to generate collections and turn and move commands (controlled by angular and linear PID systems) to make
 * the robot turn and move to specific angles/intervals in inches.*/
public class DriveablePIDFactory implements PIDFactory {

    /*Instance of this class to be used to access the instance-specific command group creation method.*/
    private static DriveablePIDFactory instance;

    /**@return The instance variable used to access the instance-specific command group creation method.*/
    public static DriveablePIDFactory getInstance() {
        if(instance == null) {
            instance = new DriveablePIDFactory();
        }
        return instance;
    }

    @Override
    public SequentialCommandGroup buildCommandGroup(Driveable driveable, List<PathSegment> anglesAndInches) {
        final SequentialCommandGroup commandGroup = new SequentialCommandGroup();
        driveable.getMotors();
        driveable.getIMU();
        for(PathSegment segment : anglesAndInches) {
            PIDControllerPipeline pipeline = new PIDControllerPipeline(FactoryConstants.MathConfig.DRIVE_VECTOR, new LinkedList<>(
                    driveable.getMotors()
            ), driveable.getIMU(), segment.angle, segment.inches, segment.strafe);
            commandGroup.addCommands(pipeline.getTurnToCommand(), pipeline.getMoveCommand());
        }
        return commandGroup;
    }
}
