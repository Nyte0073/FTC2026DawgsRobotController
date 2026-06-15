package org.firstinspires.ftc.teamcode.androidstudioassignment;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Turn90Degrees", group = "teamcode")
public class Turn90Degrees extends CommandOpMode {

    private Motor leftMotor, rightMotor;

    @Override
    public void initialize() {
        leftMotor = new Motor(hardwareMap, "backLeftMecanumMotor");
        rightMotor = new Motor(hardwareMap, "backRightMecanumMotor");
        leftMotor.setInverted(true);
    }

    @Override
    public void run() {
        /*Put the drive code in here.*/
    }
}
