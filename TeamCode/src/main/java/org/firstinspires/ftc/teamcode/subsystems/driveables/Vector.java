package org.firstinspires.ftc.teamcode.subsystems.driveables;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public final class Vector {

    public static final Map<Motor, Vector> motorsToVectorPositions = new HashMap<>();
    public static final BiFunction<Double, Double, Vector> rotate90DegreesClockwise = (x, y) ->
            new Vector(y, -x), rotate90DegreesCounterclockwise = (x, y) ->
            new Vector(-y, x);

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

    public Vector plus(Vector vector) {
        return new Vector(getX() + vector.getX(), getY() + vector.getY(), getZ() + vector.getZ());
    }

    public Vector minus(Vector vector) {
        return new Vector(getX() - vector.getX(),getY() - vector.getY(), getZ() - vector.getZ());
    }

    public Vector times(double multiplier) {
        return new Vector(getX() * multiplier, getY() * multiplier, getZ() * multiplier);
    }

    public Vector dividedBy(double divisor) {
        return new Vector(getX() / divisor, getY() / divisor, getZ() / divisor);
    }

    public double getMagnitude() {
        return Math.hypot(getX(), getY()) / Math.sqrt(2);
    }
}
