package org.firstinspires.ftc.teamcode.subsystems.driveables.factories.pidfactories;

import java.util.List;

public class PathSegment {

    public double angle, inches;
    public boolean strafe;

    public PathSegment(double angle, double inches, boolean strafe) {
        this.angle = angle;
        this.inches = inches;
        this.strafe = strafe;
    }

    public static List<PathSegment> of(PathSegment... segments) {
        return List.of(segments);
    }
}
