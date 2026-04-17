package org.firstinspires.ftc.teamcode.teleops;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;

@Autonomous(name = "Auto Encoder And Motor Test", group = "teamcode")
public class ServoTest extends CommandOpMode {

    ServoEx servoEx;

    @Override
    public void initialize() {
        servoEx = new SimpleServo(hardwareMap, "servo", -180, 180, AngleUnit.DEGREES);
        servoEx.setInverted(true);
        Constants.telemetry.addLine("Servo has initialized properly.");
        Constants.telemetry.update();
    }

    @Override
    public void run() {
        servoEx.turnToAngle(0);
    }
}
