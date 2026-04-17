package org.firstinspires.ftc.teamcode.teleops;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.subsystems.driveables.factories.FactoryConstants;

@Autonomous(name = "Servo Test", group = "teamcode")
public class ServoTest extends CommandOpMode {

    ServoEx servoEx, servoEx2;

    @Override
    public void initialize() {
        servoEx = new SimpleServo(hardwareMap, "servo", -180, 180, AngleUnit.DEGREES);
        servoEx.setInverted(true);
        servoEx2 = new SimpleServo(hardwareMap, "servo2", -180, 180, AngleUnit.DEGREES);
        FactoryConstants.SensorConfig.DASHBOARD_TELEMETRY.addLine("Servo has initialized properly.");
        FactoryConstants.SensorConfig.DASHBOARD_TELEMETRY.update();
    }

    @Override
    public void run() {
//        servoEx.turnToAngle(90);
//        servoEx2.turnToAngle(110);

        servoEx.turnToAngle(25);
        servoEx2.turnToAngle(40);
    }
}
