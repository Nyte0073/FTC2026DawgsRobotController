package org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware;

public final class ExternalHardwareConstants {

    public static final class ServoImplConstants {

        public static final double LEFT_EXTENSION_SERVO_MINIMUM = 0.2 / 18.0,
        RIGHT_EXTENSION_SERVO_MINIMUM = 1.0 / 18.0, LEFT_EXTENSION_SERVO_MAXIMUM = 4.0 / 18.0,
        RIGHT_EXTENSION_SERVO_MAXIMUM = 4.0 / 18.0, RIGHT_CLAW_MAX_ROTATION = -75, CLAW_MIN_ROTATION = -180,
        LEFT_CLAW_MAX_ROTATION = -55, CLAW_MAXIMUM = 180, CLAW_MINIMUM = -180,
                LEFT_CLAW_MAX_FOAM_PIECE_ROTATION = -30, RIGHT_CLAW_MAX_FOAM_PIECE_ROTATION = -55;

        public enum ServoType {
            LEFT_EXTENSION("servo", true, LEFT_EXTENSION_SERVO_MINIMUM, LEFT_EXTENSION_SERVO_MAXIMUM),

            RIGHT_EXTENSION("servo2", false, RIGHT_EXTENSION_SERVO_MINIMUM, RIGHT_EXTENSION_SERVO_MAXIMUM),
            LEFT_CLAW("servo3", false, CLAW_MINIMUM, CLAW_MAXIMUM),
            RIGHT_CLAW("servo4", false, CLAW_MINIMUM, CLAW_MAXIMUM),
            CLAW_SYSTEM_ROTATER("servo5", false, CLAW_MINIMUM, CLAW_MAXIMUM);

            public final String servoName;
            public final boolean inverted;
            public final double min, max;

            ServoType(String servoName, boolean inverted, double min, double max) {
                this.servoName = servoName;
                this.inverted = inverted;
                this.min = min;
                this.max = max;
            }
        }
    }
}
