package org.firstinspires.ftc.teamcode.pathplanning;


public class PathTrajectory {
    private boolean fieldOrientated, rotateToCorrectHeading;
    private double pathHeading, trajectoryMagnitude;
    private PathTrajectoryMeasurement measurement;
    private ChassisPathingStrategy chassisPathingStrategy;

    public void setMeasurement(PathTrajectoryMeasurement measurement) {
        this.measurement = measurement;
    }

    public void setChassisPathingStrategy(ChassisPathingStrategy chassisPathingStrategy) {
        this.chassisPathingStrategy = chassisPathingStrategy;
    }

    public boolean isFieldOrientated() {
        return fieldOrientated;
    }

    public boolean isRotateToCorrectHeading() {
        return rotateToCorrectHeading;
    }

    public PathTrajectoryMeasurement getMeasurement() {
        return measurement;
    }

    public ChassisPathingStrategy getChassisPathingStrategy() {
        return chassisPathingStrategy;
    }

    public double getPathHeading() {
        return pathHeading;
    }

    public double getTrajectoryMagnitude() {
        return trajectoryMagnitude;
    }

    public void setRotateToCorrectHeading(boolean rotateToCorrectHeading) {
        this.rotateToCorrectHeading = rotateToCorrectHeading;
    }

    public void setFieldOrientated(boolean fieldOrientated) {
        this.fieldOrientated = fieldOrientated;
    }

    public void setPathHeading(double pathHeading) {
        this.pathHeading = pathHeading;
    }

    public void setTrajectoryMagnitude(double trajectoryMagnitude) {
        this.trajectoryMagnitude = trajectoryMagnitude;
    }

    public enum PathTrajectoryMeasurement {
        INCHES,
        METERS,
        FEET;
    }
}
