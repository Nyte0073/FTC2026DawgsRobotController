package org.firstinspires.ftc.teamcode.subsystems.io;

import java.io.Serializable;
import java.util.List;

public interface RobotSpecs<E> extends Serializable {
    List<E> getSpecs();
}
