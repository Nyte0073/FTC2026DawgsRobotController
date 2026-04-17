package org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware;

public class ExternalHardwareConstants {

    public static final class ServoImplConstants {

        public static final double LEFT_EXTENSION_SERVO_MINIMUM = 25.0,
        RIGHT_EXTENSION_SERVO_MINIMUM = 40.0, LEFT_EXTENSION_SERVO_MAXIMUM = 90.0,
        RIGHT_EXTENSION_SERVO_MAXIMUM = 110.0;

        public enum ServoType {
            LEFT_EXTENSION("servo", true, 25.0, 90.0),

            RIGHT_EXTENSION("servo2", false, 40.0, 110.0);

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
