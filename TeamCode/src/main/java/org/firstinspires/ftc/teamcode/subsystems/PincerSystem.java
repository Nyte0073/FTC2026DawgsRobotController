package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.ClawHardware;

import java.util.HashMap;
import java.util.Map;

public class PincerSystem extends ClawHardware {

    private boolean pincerSystemToggle = false;
    private final String[] names;
    private final Map<String, Double> servoNamesToMaxPositions = new HashMap<>();

    public PincerSystem(HardwareMap map, String leftPincerName, String rightPincerName) {
        super(map, new String[]{leftPincerName, rightPincerName});
        names = new String[] {leftPincerName, rightPincerName};
        servoNamesToMaxPositions.put(leftPincerName, Constants.PINCER_SYSTEM_LEFT_SERVO_GRAB);
        servoNamesToMaxPositions.put(rightPincerName, Constants.PINCER_SYSTEM_RIGHT_SERVO_GRAB);
    }

    @Override
    public void operateClaws() {
        pincerSystemToggle = !pincerSystemToggle;
        Map<String, ServoEx> servos = getServos();
           for(String name : names) {
               ServoEx s = servos.get(name);
               if(pincerSystemToggle) {
                   s.setPosition(servoNamesToMaxPositions.get(name));
               } else {
                   s.setPosition(Constants.SERVO_MIN);
               }
           }
    }

    @Override
    public void periodic() {

    }
}
