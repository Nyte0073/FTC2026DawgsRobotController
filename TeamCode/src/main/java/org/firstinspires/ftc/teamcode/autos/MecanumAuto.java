package org.firstinspires.ftc.teamcode.autos;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.pid.LinearPIDModule;
import org.firstinspires.ftc.teamcode.pid.PIDModule;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "MecanumAuto", group = "teamcode")
public class MecanumAuto extends CommandOpMode {

    private final List<PIDModule> pidModules = new ArrayList<>();

    @Override
    public void initialize() {
        List<Motor> motors = new ArrayList<>(
                List.of(new Motor(hardwareMap, Constants.MecanumConstants.frontLeftMecanumMotor),
                        new Motor(hardwareMap, Constants.MecanumConstants.frontRightMecanumMotor),
                        new Motor(hardwareMap, Constants.MecanumConstants.backLeftMecanumMotor),
                        new Motor(hardwareMap, Constants.MecanumConstants.backRightMecanumMotor))
        );
        Constants.MecanumConstants.initConstants(
                motors.get(0), motors.get(1), motors.get(2), motors.get(3), true
        );
        for(Motor m : motors) {
            pidModules.add(new LinearPIDModule(m, telemetry));
        }
        for(PIDModule module : pidModules) {
            module.setTarget(39.37);
        }
    }

    @Override
    public void run() {
        for(PIDModule module : pidModules) {
            Motor m = module.getMotor();
            m.set(module.calculate());
        }
    }
}