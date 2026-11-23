package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.pid.PIDControlSystem;
import org.firstinspires.ftc.teamcode.pid.PIDControlSystemConstraints;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ChassisComponentConstraints {
    private final GamepadExWrapper gamepadExWrapper;
    public final Map<String, PIDControlSystem> pidSystemForTurningMotors = new HashMap<>(),
    pidSystemForDrivingMotors = new HashMap<>();
    private boolean isAutonomous = false, optimizeForShortestRotation = false;
    private AutonomousModeType autonomousModeType;

    private ChassisComponentConstraints(ChassisComponentConstraints.Builder builder) {
        gamepadExWrapper = builder.gamepadExWrapper;
        for(MotorExWrapper turningMotor : builder.turningMotors) {

            PIDControlSystem system = new PIDControlSystem();
            system.setPidControlSystemConstraints(
                    new PIDControlSystemConstraints.Builder()
                            .setPIDInput(turningMotor::getDistance)
                            .setPIDControllerWrapper(new PIDControllerWrapper())
                            .build()
            );

            pidSystemForTurningMotors.put(turningMotor.getMotorName(), system);
        }
        for(MotorExWrapper drivingMotor : builder.drivingMotors) {

            PIDControlSystem system = new PIDControlSystem();
            system.setPidControlSystemConstraints(
                    new PIDControlSystemConstraints.Builder()
                            .setPIDInput(drivingMotor::getDistance)
                            .setPIDControllerWrapper(new PIDControllerWrapper())
                            .build()
            );

            pidSystemForDrivingMotors.put(drivingMotor.getMotorName(), system);
        }
    }

    public void setAutonomousModeType(AutonomousModeType autonomousModeType) {
        this.autonomousModeType = autonomousModeType;
    }

    public AutonomousModeType getAutonomousModeType() {
        return autonomousModeType;
    }

    public void setAutonomous(boolean autonomous) {
        isAutonomous = autonomous;
    }

    public boolean isAutonomous() {
        return isAutonomous;
    }

    public void setOptimizeForShortestRotation(boolean optimizeForShortestRotation) {
        this.optimizeForShortestRotation = optimizeForShortestRotation;
    }

    public boolean isOptimizeForShortestRotation() {
        return optimizeForShortestRotation;
    }

    public GamepadExWrapper getGamepadExWrapper() {
        return gamepadExWrapper;
    }

    public PIDControlSystem getPIDSystemForTurningMotor(MotorExWrapper turningMotor) {
        return pidSystemForTurningMotors.get(turningMotor.getMotorName());
    }

    public PIDControlSystem getPIDSystemForDrivingMotor(MotorExWrapper drivingMotor) {
        return pidSystemForDrivingMotors.get(drivingMotor.getMotorName());
    }


    public static final class Builder {
        private GamepadExWrapper gamepadExWrapper;
        private final List<MotorExWrapper> turningMotors = new LinkedList<>(), drivingMotors = new LinkedList<>();

        public Builder setGamepadExWrapper(GamepadExWrapper gamepadExWrapper) {
            this.gamepadExWrapper = gamepadExWrapper;
            return this;
        }

        public Builder addTurningMotor(MotorExWrapper turningMotor) {
            turningMotors.add(turningMotor);
            return this;
        }

        public Builder addDrivingMotor(MotorExWrapper drivingMotor) {
            drivingMotors.add(drivingMotor);
            return this;
        }

        public ChassisComponentConstraints build() {
            return new ChassisComponentConstraints(this);
        }
    }
}
