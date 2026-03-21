package org.firstinspires.ftc.teamcode.subsystems.io;

public class RobotSpecs {

    private final MotorSpecs motorSpecs;
    private final OrientationSpecs orientationSpecs;

    public RobotSpecs(MotorSpecs motorSpecs, OrientationSpecs orientationSpecs) {
        this.motorSpecs = motorSpecs;
        this.orientationSpecs = orientationSpecs;
    }

    public static final class MotorSpecs {
        private final double motorPower;
        private final double currentMotorPosition;

        public MotorSpecs(double motorPower, double currentMotorPosition) {
            this.motorPower = motorPower;
            this.currentMotorPosition = currentMotorPosition;
        }
    }

    public static final class OrientationSpecs {
        private final double currentOrientationDegrees;
        private final double currentOrientationRadians;

        public OrientationSpecs(double currentOrientationDegrees, double currentOrientationRadians) {
            this.currentOrientationRadians = currentOrientationRadians;
            this.currentOrientationDegrees = currentOrientationDegrees;
        }
    }

}
