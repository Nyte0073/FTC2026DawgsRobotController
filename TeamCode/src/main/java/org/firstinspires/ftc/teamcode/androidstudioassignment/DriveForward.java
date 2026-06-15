package org.firstinspires.ftc.teamcode.androidstudioassignment;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**AKA Aidan's version of coding in Android Studio. Just so you know, Aidan, this Java file is redundant and not scalable, which
 * is why I know this was created for beginner-level programming. Once you travel in more advanced areas of Java, you will start to realize
 * that code being scalable and organized is very important for creating good quality code.*/
@Autonomous(name = "DriveForward", group = "teamcode")
public class DriveForward extends CommandOpMode {

    private Motor leftMotor, rightMotor;

    @Override
    public void initialize() {
        leftMotor = new Motor(hardwareMap, "backLeftMecanumMotor");
        rightMotor = new Motor(hardwareMap, "backRightMecanumMotor");
        leftMotor.setInverted(true);
    }

    @Override
    public void run() {
        /*Put the code to drive the motors in here.*/
    }
}
