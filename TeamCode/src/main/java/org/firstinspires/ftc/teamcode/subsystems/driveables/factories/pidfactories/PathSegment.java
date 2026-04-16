package org.firstinspires.ftc.teamcode.subsystems.driveables.factories.pidfactories;

import java.util.List;

/**Defines a certain angle and a specific to rotate to and travel/strafe to.*/
public class PathSegment {

    /**The angle for the robot to turn to.*/
    public double angle,
    /**The amount of inches for the robot to travel/strafe to.*/
    inches;

    /**Boolean state of whether the robot should strafe to the target or not.*/
    public boolean strafe;

    /**Constructs a new instance of this class that initializes all the variables.*/
    public PathSegment(double angle, double inches, boolean strafe) {
        this.angle = angle;
        this.inches = inches;
        this.strafe = strafe;
    }


    public static List<PathSegment> of(PathSegment... segments) {
        return List.of(segments);
    }
}
