package org.firstinspires.ftc.teamcode.subsystems.io;

import java.util.List;

public class MotorPowerSpecs implements RobotSpecs<Integer> {

    private final List<Integer> motorPowerSpecs;
    public MotorPowerSpecs(List<Integer> motorPowerSpecs) {
        this.motorPowerSpecs = motorPowerSpecs;
    }
    @Override
    public List<Integer> getSpecs() {
        return motorPowerSpecs;
    }
}
