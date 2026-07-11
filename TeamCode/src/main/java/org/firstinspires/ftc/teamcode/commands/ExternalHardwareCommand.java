package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.subsystems.RobotInput;

import java.util.Map;

public class ExternalHardwareCommand extends CommandBase {

    private final Map<Hardware, RobotInput.InputType> hardwareInputTypeMap;
    private final RobotInput robotInput;

    public ExternalHardwareCommand(Map<Hardware, RobotInput.InputType> map, RobotInput input) {
        hardwareInputTypeMap = map;
        this.robotInput = input;
    }

    @Override
    public void initialize() {
        for(Hardware h : hardwareInputTypeMap.keySet()) {
            addRequirements(h);
            h.setDefaultCommand(this);
        }
    }

    @Override
    public void execute() {
        for(Map.Entry<Hardware, RobotInput.InputType> entry : hardwareInputTypeMap.entrySet()) {
            if(robotInput.getRobotButtonInput().get(entry.getValue()).getAsBoolean()) {
                entry.getKey().operate();
            }
        }
    }
}
