package org.firstinspires.ftc.teamcode.subsystems.driveables.factories.pidfactories;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.pid.PIDControllerPipeline;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Driveable;
import org.firstinspires.ftc.teamcode.subsystems.driveables.factories.FactoryConstants;

import java.util.LinkedList;
import java.util.List;

public class DriveablePIDFactory implements PIDFactory {

    private static DriveablePIDFactory instance;

    public static DriveablePIDFactory getInstance() {
        if(instance == null) {
            instance = new DriveablePIDFactory();
        }
        return instance;
    }

    @Override
    public SequentialCommandGroup buildCommandGroup(Driveable driveable, List<PathSegment> anglesAndInches) {
        final SequentialCommandGroup commandGroup = new SequentialCommandGroup();
        for(PathSegment segment : anglesAndInches) {
            PIDControllerPipeline pipeline = new PIDControllerPipeline(FactoryConstants.MathConfig.DRIVE_VECTOR, new LinkedList<>(
                    driveable.getMotors()
            ),
                    driveable.getIMU(), segment.angle, segment.inches, segment.strafe);
            commandGroup.addCommands(pipeline.getTurnToCommand(), pipeline.getMoveCommand());
        }
        return commandGroup;
    }
}
