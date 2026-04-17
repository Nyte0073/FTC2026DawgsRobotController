package org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.externalhardwareactions;

import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.ExternalHardwareConstants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.ServoImpl;

import java.util.function.Consumer;

public class ServoAction implements Runnable {
    private final ServoImpl servoImpl;
    public final Consumer<ServoImpl> servoConsumer;

    public ServoAction(ServoImpl servo, ActionType actionType) {
        servoImpl = servo;
        servoConsumer = actionType.consumer;
    }

    @Override
    public void run() {
        servoConsumer.accept(servoImpl);
    }

    public enum ActionType {
        GO_TO_MAX_RIGHT_EXTENSION(s -> s.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.RIGHT_EXTENSION_SERVO_MAXIMUM)),
        GO_TO_MIN_RIGHT_EXTENSION(s -> s.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.RIGHT_EXTENSION_SERVO_MINIMUM)),
        GO_TO_MAX_LEFT_EXTENSION(s -> s.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.LEFT_EXTENSION_SERVO_MAXIMUM)),
        GO_TO_MIN_LEFT_EXTENSION(s -> s.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.LEFT_EXTENSION_SERVO_MINIMUM));

        public final Consumer<ServoImpl> consumer;

        ActionType(Consumer<ServoImpl> consumer) {
            this.consumer = consumer;
        }
    }
}
