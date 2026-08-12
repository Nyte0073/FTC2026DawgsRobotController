package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.ClawHardware;

public class ClawArmRotaterSystem extends ClawHardware {
    private static int rotationToggle = 1;
    private final String clawArmServoName;

    public ClawArmRotaterSystem(HardwareMap map, String clawArmServoName) {
        super(map, new String[] {clawArmServoName});
        this.clawArmServoName = clawArmServoName;
    }

    @Override
    public void operateClaws() {
        ServoEx servoEx = getServos().get(clawArmServoName);
        if(rotationToggle <= 3) {
            rotationToggle++;
        } else {
            rotationToggle = 1;
        }
        switch(rotationToggle) {
            case 1:
                servoEx.setPosition(Constants.ARM_SYSTEM_POSITION_ONE);
                break;

            case 2:
                servoEx.setPosition(Constants.ARM_SYSTEM_POSITION_TWO);
                break;

            case 3:
                servoEx.setPosition(Constants.ARM_SYSTEM_POSITION_THREE);
                break;
        }
    }

}
