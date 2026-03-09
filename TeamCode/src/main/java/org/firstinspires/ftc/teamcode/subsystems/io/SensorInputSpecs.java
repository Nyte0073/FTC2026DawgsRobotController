package org.firstinspires.ftc.teamcode.subsystems.io;

import java.util.List;

public class SensorInputSpecs<E> implements RobotSpecs<E> {

    private final List<E> specs;

    public SensorInputSpecs(List<E> specs) {
        this.specs = specs;
    }

    @Override
    public List<E> getSpecs() {
        return specs;
    }
}
