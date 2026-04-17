package org.firstinspires.ftc.teamcode.subsystems.io;

public class RobotSpecs {

    private final MotorSpecs motorSpecs;
    private final OrientationSpecs orientationSpecs;

    public RobotSpecs(MotorSpecs motorSpecs, OrientationSpecs orientationSpecs) {
        this.motorSpecs = motorSpecs;
        this.orientationSpecs = orientationSpecs;
    }

    public static final class MotorSpecs {
        public final double motorPower;
        public final double currentMotorPosition;

        public MotorSpecs(double motorPower, double currentMotorPosition) {
            this.motorPower = motorPower;
            this.currentMotorPosition = currentMotorPosition;
        }
    }

    public static final class OrientationSpecs {
        public final double currentOrientationDegrees;
        public final double currentOrientationRadians;

        public OrientationSpecs(double currentOrientationDegrees, double currentOrientationRadians) {
            this.currentOrientationRadians = currentOrientationRadians;
            this.currentOrientationDegrees = currentOrientationDegrees;
        }
    }

}
