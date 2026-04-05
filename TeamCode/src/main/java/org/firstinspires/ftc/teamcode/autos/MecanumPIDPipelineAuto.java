package org.firstinspires.ftc.teamcode.autos;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.pid.PIDControllerPipeline;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;
import java.util.List;

/**Class for making mecanum-controlled robot turn certain degrees and drive a certain distance in straight lines, using PID
 * controllers and encoders to keep track of the robot's position. This class can chain multiple paths so that the robot can rotate
 * and travel to multiple positions along the field.*/
@Autonomous(name = "MecanumPIDPipelineAuto", group = "teamcode")
public class MecanumPIDPipelineAuto extends CommandOpMode {

    @Override
    public void initialize() {
        Constants.MecanumConstants.initConstants(
                new Motor(hardwareMap, Constants.MecanumConstants.frontLeftMecanumMotor),
                new Motor(hardwareMap, Constants.MecanumConstants.frontRightMecanumMotor),
                new Motor(hardwareMap, Constants.MecanumConstants.backLeftMecanumMotor),
                new Motor(hardwareMap, Constants.MecanumConstants.backRightMecanumMotor),
                true,
                hardwareMap.get(IMU.class, "imu")
        );
        List<PIDControllerPipeline> pidControllerPipelines = List.of(
                new PIDControllerPipeline(-50, Constants.MecanumConstants.mecanumAngularIMUPIDModules.values(),
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