package org.firstinspires.ftc.teamcode.subsystems.driveables;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**Class that handles all directional and magnitude calculations according to an inputted x and y value.
 * This class handles those coordinates like such on a cartesian plane, where they can be used
 * along with trigonometric ratios to figure out a resultant
 * vector with a magnitude and a direction.*/
public final class Vector {

    /**Map of the turning modules to their respective positions relative to the center of the robot.*/
    public static final Map<Motor, Vector> motorsToVectorPositions = new HashMap<>();

    /**Rotates an inputted vector 90 degrees clockwise by inputting y for x and x for y, and by changing the x value of the original
     * vector to be negative.*/
    public static final BiFunction<Double, Double, Vector> rotate90DegreesClockwise = (x, y) ->
            new Vector(y, -x),

    /**Rotates an inputted vector 90 degrees counterclockwise by inputting y for x and x for y, and by changing the y value of the original
     * vector to be negative. */
    rotate90DegreesCounterclockwise = (x, y) ->
            new Vector(-y, x);

    /*x, y and z value fields that can be represented in a cartesian plane as coordinates.*/
    private double x = 0, y = 0, z = 0;

    public Vector(double x, double y) {
        setX(x);
        setY(y);
    }

    public Vector(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    public void update(double x, double y) {
        setX(x);
        setY(y);
    }

    public Vector() {}

    @Override
    public boolean equals(Object o) {
        Vector v = (Vector) o;
        double x = v.getX();
        double y = v.getY();
        return Math.abs(x - getX()) <= Constants.tolerance && Math.abs(y - getY()) <= Constants.tolerance;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getZ() {
        return z;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    /**@return The resultant vector of two vectors with their x, y, and z values added with the other vectors'.*/
    public Vector plus(Vector vector) {
        return new Vector(getX() + vector.getX(), getY() + vector.getY(), getZ() + vector.getZ());
    }

    /**@return The resultant vector of two vectors with their x, y, and z values subtracted with the other vectors'.*/
    public Vector minus(Vector vector) {
        return new Vector(getX() - vector.getX(),getY() - vector.getY(), getZ() - vector.getZ());
    }

    /**@return The resultant vector of two vectors with their x, y, and z values multiplied by the other vectors'.*/
    public Vector times(double multiplier) {
        return new Vector(getX() * multiplier, getY() * multiplier, getZ() * multiplier);
    }

    /**@return The resultant vector of two vectors with their x, y, and z values divided by the other vectors'.*/
    public Vector dividedBy(double divisor) {
        return new Vector(getX() / divisor, getY() / divisor, getZ() / divisor);
    }

    /**@return The 'length' or magnitude of the vector, relative to the highest possible magnitude which is 1, caused by
     * a vector of the values (1, 1).*/
    public double getMagnitude() {
        return Math.hypot(getX(), getY()) / Math.sqrt(2);
    }
}
