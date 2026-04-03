package org.firstinspires.ftc.teamcode.autos;

import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.MecanumConstants.mecanumKd;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.MecanumConstants.mecanumKp;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.MecanumConstants.mecanumKs;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.MecanumConstants.turningMotors;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.pid.LinearPIDModule;
import org.firstinspires.ftc.teamcode.pid.PIDModule;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;

import java.util.ArrayList;
import java.util.List;

/**Class for making mecanum robot travel to a specified distance forward or backward.
 * This class is used with its own built in PID system so the robot will speed up when it is far away from its target position
 * and slow down once it gets closer and closer to reaching it.*/
@Autonomous(name = "MecanumAuto", group = "teamcode")
public class MecanumAuto extends CommandOpMode {

    /**List of PIDControllers that will be used on each motor to get them to their target positions.*/
    private final List<PIDModule> pidModules = new ArrayList<>();

    @Override
    public void initialize() {
        List<Motor> motors = new ArrayList<>(
                List.of(new Motor(hardwareMap, Constants.MecanumConstants.frontLeftMecanumMotor),
                        new Motor(hardwareMap, Constants.MecanumConstants.frontRightMecanumMotor),
                        new Motor(hardwareMap, Constants.MecanumConstants.backLeftMecanumMotor),
                        new Motor(hardwareMap, Constants.MecanumConstants.backRightMecanumMotor))
        );
        IMU imu = hardwareMap.get(IMU.class, "imu");
        Constants.MecanumConstants.initConstants(
                motors.get(0), motors.get(1), motors.get(2), motors.get(3), true, imu
        );
        turningMotors.get(0).setInverted(false);
        turningMotors.get(2).setInverted(false);
        for(Motor m : motors) {
            pidModules.add(new LinearPIDModule(m, mecanumKp, mecanumKs, mecanumKd));
        }
        for(PIDModule module : pidModules) {
            module.setTarget(39.37);
            module.getMotor().encoder.setDirection(Motor.Direction.REVERSE);
        }
    }

    @Override
    public void run() {
        for(PIDModule module : pidModules) {
            Motor m = module.getMotor();
            double calculate = module.calculate();
            m.set(Math.min(0.5, calculate / 2.0));
        }
    }
}