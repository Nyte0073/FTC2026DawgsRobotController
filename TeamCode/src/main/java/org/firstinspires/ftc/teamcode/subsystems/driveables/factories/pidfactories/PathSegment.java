package org.firstinspires.ftc.teamcode.subsystems.driveables.factories.pidfactories;

import java.util.LinkedList;
import java.util.List;

/**Defines a certain angle and a specific to rotate to and travel/strafe to.*/
public class PathSegment {

    public static final int NO_TURN = -1000;
    private static final PathSegment instance = new PathSegment();
    private static final List<PathSegment> pathSegments = new LinkedList<>();

    /**
     * The angle for the robot to turn to.
     */
    public double angle,
    /**
     * The amount of inches for the robot to travel/strafe to.
     */
    inches;

    /**
     * Boolean state of whether the robot should strafe to the target or not.
     */
    public boolean strafe;

    private PathSegment() {}

    /**
     * Constructs a new instance of this class that initializes all the variables.
     */
    public PathSegment(double angle, double inches, boolean strafe) {
        this.angle = angle;
        this.inches = inches;
        this.strafe = strafe;
    }

    public static PathSegment startPath() {
        pathSegments.clear();
        return instance;
    }

    public PathSegment turnAndStrafe(double angle, TurnDirection rotationDirection, double strafingInches, StrafeDirection strafingDirection) {
        pathSegments.add(new PathSegment(angle * rotationDirection.directionMultiplier,
                strafingInches * strafingDirection.directionMultiplier, true));
        return instance;
    }

    public PathSegment strafe(double strafingInches, StrafeDirection strafingDirection) {
        pathSegments.add(new PathSegment(NO_TURN, strafingInches * strafingDirection.directionMultiplier, true));
        return instance;
    }

    public PathSegment turnAndTravelLinearly(double angle, TurnDirection rotationDirection, double linearInches, LinearDirection linearDirection) {
        pathSegments.add(new PathSegment(angle * rotationDirection.directionMultiplier,
                linearInches * linearDirection.directionMultiplier,
                false));
        return instance;
    }

    public PathSegment travelLinearly(double linearInches, LinearDirection linearDirection) {
        pathSegments.add(new PathSegment(NO_TURN,
                linearInches * linearDirection.directionMultiplier, false));
        return instance;
    }

    public List<PathSegment> buildPipeline() {
        return pathSegments;
    }

    public enum LinearDirection {
        FORWARD(1),
        BACKWARD(-1);

        public final int directionMultiplier;

        LinearDirection(int directionMultiplier) {
            this.directionMultiplier = directionMultiplier;
        }
    }

    public enum StrafeDirection {
        LEFT(-1),
        RIGHT(1);

        public final int directionMultiplier;

        StrafeDirection(int directionMultiplier) {
            this.directionMultiplier = directionMultiplier;
        }
    }

    public enum TurnDirection {
        CLOCKWISE(-1),
        COUNTERCLOCKWISE(1);

        public final int directionMultiplier;

        TurnDirection(int directionMultiplier) {
            this.directionMultiplier = directionMultiplier;
        }
    }

}