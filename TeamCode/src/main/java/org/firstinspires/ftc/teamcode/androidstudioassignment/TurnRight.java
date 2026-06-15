package org.firstinspires.ftc.teamcode.androidstudioassignment;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@SuppressWarnings("all")
@Autonomous(name = "TurnRight", group = "teamcode")
public class TurnRight extends CommandOpMode {

    private Motor leftMotor, rightMotor;
    private IMU imu;
    private boolean isStopped = false;
    private int phase = 0;
    private double yaw = 0;

    @Override
    public void initialize() {
        leftMotor = new Motor(hardwareMap, "backLeftMecanumMotor");
        rightMotor = new Motor(hardwareMap, "backRightMecanumMotor");
        leftMotor.setInverted(true);
        imu = hardwareMap.get(IMU.class, "imu");
        imu.resetYaw();
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.DOWN));
        imu.initialize(parameters);
    }

    @Override
    public void run() {
        yaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        switch(phase) {
            case 0:
                leftMotor.set(0.5);
                rightMotor.set(0.5);
                sleep(1000);
                leftMotor.set(0.5);
                rightMotor.set(-0.5);
                phase++;
                break;

            case 1:
                leftMotor.set(0.5);
                rightMotor.set(-0.5);
                phase++;
                break;

            case 2:
                if(yaw <= -5 && yaw >= -15) {
                    phase++;
                }
                break;

            case 3:
                leftMotor.stopMotor();
                rightMotor.stopMotor();
        }
    }
}
