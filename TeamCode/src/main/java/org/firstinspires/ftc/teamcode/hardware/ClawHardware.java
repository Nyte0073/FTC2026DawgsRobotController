package org.firstinspires.ftc.teamcode.hardware;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Constants;
import java.util.HashMap;
import java.util.Map;

public abstract class ClawHardware implements Hardware {

    private boolean isInit = false;
    private final Map<String, ServoEx> servos = new HashMap<>();

    public ClawHardware(HardwareMap map, String[] servoNames) {
        for(String s : servoNames) {
            servos.put(s, new SimpleServo(map, s, Constants.SERVO_MIN, Constants.SERVO_MAX));
        }
    }

    public void init() {
        if(isInit) {
            return;
        }
        for(ServoEx servo : servos.values()) {
            servo.setPosition(0);
        }
        isInit = true;
    }

    public abstract void operateClaws();

    @Override
    public void operate() {
        init();
        operateClaws();
    }

    public Map<String, ServoEx> getServos() {
        return servos;
    }
}
