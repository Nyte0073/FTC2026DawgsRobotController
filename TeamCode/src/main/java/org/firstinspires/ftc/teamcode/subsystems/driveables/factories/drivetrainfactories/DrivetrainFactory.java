package org.firstinspires.ftc.teamcode.subsystems.driveables.factories.drivetrainfactories;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.driveables.Driveable;

public interface DrivetrainFactory {
    Driveable createDrivetrain(HardwareMap hardwareMap);
    CommandBase createDrivetrainCommand(Driveable driveable, GamepadEx gamepadEx, boolean vectorControlled);
}
