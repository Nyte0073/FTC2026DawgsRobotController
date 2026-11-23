package org.firstinspires.ftc.teamcode.math;

public final class AngularOperations {

    public static double angleInRelationToXAxis(VectorOperations.Vector vector) {
        return Math.copySign(Math.toDegrees(Math.acos(vector.getComponent(VectorOperations.VectorComponentType.X) /
                vector.magnitude())), vector.getComponent(VectorOperations.VectorComponentType.Y));
    }

    public static double angleInRelationToYAxis(VectorOperations.Vector vector) {
        return Math.copySign(Math.toDegrees(Math.acos(vector.getComponent(VectorOperations.VectorComponentType.Y) /
                vector.magnitude())), vector.getComponent(VectorOperations.VectorComponentType.X));
    }

    public static double angleInRelationToZAxis(VectorOperations.Vector vector) {
        return Math.toDegrees(Math.acos(vector.getComponent(VectorOperations.VectorComponentType.Z) /
                vector.magnitude()));
    }




}
