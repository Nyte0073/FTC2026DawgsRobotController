package org.firstinspires.ftc.teamcode.subsystems.io;

public class RobotSpecs {

    private final MotorSpecs motorSpecs;
    private final OrientationSpecs orientationSpecs;

    public MotorSpecs getMotorSpecs() {
        return motorSpecs;
    }

    public OrientationSpecs getOrientationSpecs() {
        return orientationSpecs;
    }

    public RobotSpecs(MotorSpecs motorSpecs, OrientationSpecs orientationSpecs) {
        this.motorSpecs = motorSpecs;
        this.orientationSpecs = orientationSpecs;
    }

    public static final class MotorSpecs {
        public double motorPower;
        public double currentMotorPosition;

        public void setCurrentMotorPosition(double currentMotorPosition) {
            this.currentMotorPosition = currentMotorPosition;
        }

        public void setMotorPower(double motorPower) {
            this.motorPower = motorPower;
        }

        public double getCurrentMotorPosition() {
            return currentMotorPosition;
        }

        public double getMotorPower() {
            return motorPower;
        }
    }

    public static final class OrientationSpecs {
        public double currentOrientationDegrees;
        public double currentOrientationRadians;

        public void setCurrentOrientationDegrees(double currentOrientationDegrees) {
            this.currentOrientationDegrees = currentOrientationDegrees;
        }

        public void setCurrentOrientationRadians(double currentOrientationRadians) {
            this.currentOrientationRadians = currentOrientationRadians;
        }

        public double getCurrentOrientationDegrees() {
            return currentOrientationDegrees;
        }

        public double getCurrentOrientationRadians() {
            return currentOrientationRadians;
        }
    }

}
