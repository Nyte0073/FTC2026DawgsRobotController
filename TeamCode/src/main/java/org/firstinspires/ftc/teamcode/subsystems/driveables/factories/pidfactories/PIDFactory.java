package org.firstinspires.ftc.teamcode.subsystems.driveables.factories.pidfactories;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.driveables.Driveable;
import java.util.List;

@FunctionalInterface
public interface PIDFactory {
    SequentialCommandGroup buildCommandGroup(Driveable driveable, List<PathSegment> anglesAndInches);
}
