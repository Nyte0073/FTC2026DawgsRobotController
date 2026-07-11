package org.firstinspires.ftc.teamcode.hardware;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.Subsystem;

@FunctionalInterface
public interface Hardware extends Subsystem {
    void operate();
    default void register() {
        CommandScheduler.getInstance().registerSubsystem(this);
    }
}
