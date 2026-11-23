package org.firstinspires.ftc.teamcode.math;

import androidx.annotation.NonNull;

import java.util.LinkedList;

public class VectorOperations {

    public static Vector add(Vector starter, Vector adder) {
        if(starter.getVectorSize() == adder.getVectorSize()) {
            LinkedList<Double> linkedList = new LinkedList<>();
            for(int i = 0; i < starter.getVectorSize(); i++) {
                linkedList.add(starter.getComponent(i) + adder.getComponent(i));
            }
            return new Vector(linkedList);
        }
        return null;
    }



    public static Vector subtract(Vector starter, Vector subtracter) {
        return add(starter, multiply(subtracter, -1.0));
    }

    public static Vector multiply(Vector vector, double multiplier) {
        LinkedList<Double> linkedList = new LinkedList<>();
        for(int i = 0; i < vector.getVectorSize(); i++) {
            linkedList.add(vector.getComponent(i) * multiplier);
        }
        return new Vector(linkedList);
    }

    public static Vector divide(Vector vector, double divisor) {
        return multiply(vector, 1.0 / divisor);
    }

    public static Vector getUnitBaseVector(double theta, DimensionType dimensionType) {
        int dimension = dimensionType.getDimension();
        double cosTheta = Math.cos(Math.toRadians(theta));

        double pow = Math.pow(Math.cos(Math.toRadians(theta)), 2);
        double complimentComponentValue = Math.sqrt((Math.pow(cosTheta, 2) * (1 - pow)) / ((dimension - 1) * pow));

        LinkedList<Double> list = new LinkedList<>();
        for(int i  = 0; i < dimension; i++) {
            if(i == 0) {
                list.add(cosTheta);
            } else {
                list.add(complimentComponentValue);
            }
        }
        VectorOperations.Vector v = new Vector(list);
        return VectorOperations.divide(v, v.magnitude());
    }

    public static double angleBetween(Vector vector1, Vector vector2) {
        double i = 0;
        if(vector1.getVectorSize() == vector2.getVectorSize()) {
            for(int j = 0; j < vector1.getVectorSize(); j++) {
                i += vector1.getComponent(j) * vector2.getComponent(j);
            }
            return Math.toDegrees(Math.acos(i / (vector1.magnitude() * vector2.magnitude())));
        }
        return -1000.0;
    }

    public static final class Vector {
        private final LinkedList<Double> components = new LinkedList<>();

        public Vector(LinkedList<Double> components) {
            this.components.addAll(components);
        }

        public double getComponent(VectorComponentType type) {
            return components.get(type.getIndex());
        }

        public double getComponent(int index) {
            return components.get(index);
        }

        public double getVectorSize() {
            return components.size();
        }

        @NonNull
        @Override
        public String toString() {
            return components.toString();
        }

        public double magnitude() {
            double i = 0;
            for(double d : components) {
                i += Math.pow(d, 2);
            }
            return Math.sqrt(i);
        }

        public Vector rotateAroundZAxis(double theta) {
            theta = Math.toRadians(theta);
            LinkedList<Double> updatedList = new LinkedList<>(components);

            updatedList.set(VectorComponentType.X.getIndex(), rotateX(theta, getComponent(0), getComponent(1)));
            updatedList.set(VectorComponentType.Y.getIndex(), rotateY(theta, getComponent(0), getComponent(1)));
            return new Vector(new LinkedList<>(updatedList));
        }

        private double rotateX(double thetaRadians, double x, double y) {
            return Math.cos(thetaRadians) * x - y * Math.sin(thetaRadians);
        }

        private double rotateY(double thetaRadians, double x, double y) {
            return Math.sin(thetaRadians) * x + y * Math.cos(thetaRadians);
        }
    }

    public enum VectorComponentType {
        X(0), Y(1), Z(2), W(3);

        private final int index;

        VectorComponentType(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    public enum DimensionType {
        R1(1),
        R2(2),
        R3(3),
        R4(4),
        R5(5),
        R6(6),
        R7(7),
        R8(8),
        R9(9),
        R10(10);

        private final int dimension;

        DimensionType(int dimension) {
            this.dimension = dimension;
        }

        public int getDimension() {
            return dimension;
        }
    }

    public enum AxisType {
        X_AXIS(1),
        Y_AXIS(2),
        Z_AXIS(3);

        private final int axisComponentNumber;

        AxisType(int axisComponentNumber) {
            this.axisComponentNumber = axisComponentNumber;
        }

        public int getAxisComponentNumber() {
            return axisComponentNumber;
        }
    }
}