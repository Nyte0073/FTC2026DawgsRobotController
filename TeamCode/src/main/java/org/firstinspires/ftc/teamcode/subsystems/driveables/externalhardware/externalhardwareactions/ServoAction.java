package org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.externalhardwareactions;

import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.ExternalHardwareConstants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.ServoImpl;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**Represents an action that be done to one or two servos at a time on the robot.*/
public class ServoAction implements HardwareAction {

    /*Reference to the servo(s) having specifically configured actions executed on them.*/
    private final ServoImpl servoImpl, servoImpl2;

    /*Action to be called upon a singular servo.*/
    private final Consumer<ServoImpl> servoConsumer;

    /*Action to be called upon two servos.*/
    private final BiConsumer<ServoImpl, ServoImpl> servoBiConsumer;

    public ServoAction(ServoImpl servo, SingleActionType actionType) {
        servoImpl = servo;
        servoConsumer = actionType.consumer;
        servoBiConsumer = null;
        servoImpl2 = null;
    }

    public ServoAction(ServoImpl servo, ServoImpl servo2, DoubleActionType actionType) {
        servoImpl = servo;
        servoImpl2 = servo2;
        servoBiConsumer = actionType.biConsumer;
        servoConsumer = null;
    }

    @SuppressWarnings("all")
    @Override
    public void run() {
        boolean consumerNull = servoConsumer == null;
        boolean biConsumerNull = servoBiConsumer == null;
        if(!consumerNull) {
            servoConsumer.accept(servoImpl);
        } else if(!biConsumerNull) {
            servoBiConsumer.accept(servoImpl, servoImpl2);
        }
    }

    public enum SingleActionType {
        GO_TO_MAX_RIGHT_EXTENSION(s -> s.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.RIGHT_EXTENSION_SERVO_MAXIMUM)),
        GO_TO_MIN_RIGHT_EXTENSION(s -> s.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.RIGHT_EXTENSION_SERVO_MINIMUM)),
        GO_TO_MAX_LEFT_EXTENSION(s -> s.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.LEFT_EXTENSION_SERVO_MAXIMUM)),
        GO_TO_MIN_LEFT_EXTENSION(s -> s.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.LEFT_EXTENSION_SERVO_MINIMUM)),
        GO_TO_MAX_ROTATION(s -> s.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.CLAW_MAXIMUM)),
        GO_TO_MIN_ROTATION(s -> s.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.CLAW_MINIMUM)),
        GO_TO_ZERO_ROTATION(s -> s.servo.turnToAngle(0)),
        ENABLE_PIECE_PICKING(s -> s.pickingPieceToggle = !s.pickingPieceToggle);

        public final Consumer<ServoImpl> consumer;

        SingleActionType(Consumer<ServoImpl> consumer) {
            this.consumer = consumer;
        }
    }

    public enum DoubleActionType {
        GO_TO_MAX_EXTENSION(((leftServoImpl, rightServoImpl) -> {
            /*todo turn angle 90, not set position.*/
            leftServoImpl.servo.setPosition(ExternalHardwareConstants.ServoImplConstants.GROUND_MAX_POSITION);
            rightServoImpl.servo.setPosition(ExternalHardwareConstants.ServoImplConstants.GROUND_MAX_POSITION);
        })),
        GO_TO_MIN_EXTENSION((leftServoImpl, rightServoImpl) -> {
            /*todo turn to angle 0, not set position*/
            leftServoImpl.servo.setPosition(ExternalHardwareConstants.ServoImplConstants.ABSOLUTE_MIN_POSITION);
            rightServoImpl.servo.setPosition(ExternalHardwareConstants.ServoImplConstants.ABSOLUTE_MIN_POSITION);
        }),
        GO_TO_BASKET_POSITION((leftServoImpl, rightServoImpl) -> {
            leftServoImpl.servo.setPosition(ExternalHardwareConstants.ServoImplConstants.BASKET_MAX_POSITION);
            rightServoImpl.servo.setPosition(ExternalHardwareConstants.ServoImplConstants.BASKET_MAX_POSITION);
        }),
        CLAW_GRAB((leftClaw, rightClaw) -> {
            if(leftClaw.pickingPieceToggle) {
                leftClaw.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.LEFT_CLAW_MAX_FOAM_PIECE_ROTATION);
                rightClaw.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.RIGHT_CLAW_MAX_FOAM_PIECE_ROTATION);
            } else {
                leftClaw.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.LEFT_CLAW_MAX_ROTATION);
                rightClaw.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.RIGHT_CLAW_MAX_ROTATION);
            }
        }),
        CLAW_RELEASE((leftClaw, rightClaw) -> {
            leftClaw.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.CLAW_MIN_ROTATION);
            rightClaw.servo.turnToAngle(ExternalHardwareConstants.ServoImplConstants.CLAW_MIN_ROTATION);
        }),

        CLAW_GRAB_RELEASE((leftServoImpl, rightServoImpl) -> {
            leftServoImpl.toggle = !leftServoImpl.toggle;
            if(!leftServoImpl.toggle) {
                leftServoImpl.servo.setPosition(ExternalHardwareConstants.ServoImplConstants.LEFT_EXTENSION_SERVO_MINIMUM);
                rightServoImpl.servo.setPosition(ExternalHardwareConstants.ServoImplConstants.RIGHT_EXTENSION_SERVO_MINIMUM);
            } else {
                leftServoImpl.servo.setPosition(ExternalHardwareConstants.ServoImplConstants.LEFT_EXTENSION_SERVO_MAXIMUM);
                rightServoImpl.servo.setPosition(ExternalHardwareConstants.ServoImplConstants.RIGHT_EXTENSION_SERVO_MAXIMUM);
            }
        });

        public final BiConsumer<ServoImpl, ServoImpl> biConsumer;

        DoubleActionType(BiConsumer<ServoImpl, ServoImpl> biConsumer) {
            this.biConsumer = biConsumer;
        }
    }

}
