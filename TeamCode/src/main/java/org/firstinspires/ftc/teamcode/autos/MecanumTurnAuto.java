package org.firstinspires.ftc.teamcode.autos;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.pid.PIDControllerPipeline;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;

import java.util.List;

@Autonomous(name = "MecanumAuto", group = "teamcode")
public class MecanumTurnAuto extends CommandOpMode {

    @Override
    public void initialize() {
        List<PIDControllerPipeline> pidControllerPipelines = List.of(
                new PIDControllerPipeline(50, Constants.MecanumConstants.mecanumAngularIMUPIDModules.values(),
                        40, Constants.MecanumConstants.mecanumPIDModules.values())
        );
        for(PIDControllerPipeline pidControllerPipeline : pidControllerPipelines) {
            schedule(new SequentialCommandGroup(
                    pidControllerPipeline.getTurnToCommand(),
                    pidControllerPipeline.getMoveCommand()
            ));
        }
    }
}