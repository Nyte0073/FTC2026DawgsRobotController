package org.firstinspires.ftc.teamcode.teleops;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.TankCommand;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.TankDrive;

import java.util.function.Supplier;

@TeleOp(name = "TankTeleop", group = "teamcode")
public final class TankTeleop extends CommandOpMode {

    @Override
    public void initialize() {
        Motor leftMotor = new Motor(hardwareMap, Constants.TankConstants.backLeftTankMotor);
        Motor rightMotor = new Motor(hardwareMap, Constants.TankConstants.backRightTankMotor);
        Constants.TankConstants.initConstants(leftMotor, rightMotor, false);
        GamepadEx gamepadEx = new GamepadEx(gamepad1);
        Supplier<Double> xSupplier = gamepadEx::getLeftX,
        ySupplier = () -> -gamepadEx.getLeftY();
        TankDrive tankDrive = new TankDrive(ySupplier, xSupplier, telemetry);
        TankCommand tankCommand = new TankCommand(tankDrive);
        tankCommand.addRequirements(tankDrive);
        tankDrive.setDefaultCommand(tankCommand);
    }
}
