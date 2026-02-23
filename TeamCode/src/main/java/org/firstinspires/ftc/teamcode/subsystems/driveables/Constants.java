package org.firstinspires.ftc.teamcode.subsystems.driveables;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
    public static final List<Motor> drivingMotors = new ArrayList<>(), turningMotors = new ArrayList<>();
    public static final Map<Motor, Vector> motorsAndTheirRotatedAndTranslatedVectors = new HashMap<>();
    public static final Map<Motor, Motor> turningDrivingMotorsMap = new HashMap<>();
    public static final String frontLeftDrivingMotorID = "frontLeftDriving", frontRightDrivingMotorID = "frontRightDriving",
    backLeftDrivingMotorID = "backLeftDriving", backRightDrivingMotorID = "backRightDriving", frontLeftRotatingMotorID = "frontLeftRotating",
    frontRightRotatingMotorID = "frontRightRotating", backLeftRotatingMotorID = "backLeftRotating", backRightRotatingMotorID = "backRightRotating";

    public static void initConstants(List<Motor> drivingMotors, List<Motor> turningMotors) {
        Constants.drivingMotors.addAll(drivingMotors);
        Constants.turningMotors.addAll(turningMotors);
        for(Motor motor : drivingMotors) {
            motor.stopAndResetEncoder();
            motor.setRunMode(Motor.RunMode.RawPower);
        }
        for(Motor motor : turningMotors) {
            motor.stopAndResetEncoder();
            motor.setRunMode(Motor.RunMode.PositionControl);
        }
        turningDrivingMotorsMap.put(turningMotors.get(0), drivingMotors.get(0));
        turningDrivingMotorsMap.put(turningMotors.get(1), drivingMotors.get(1));
        turningDrivingMotorsMap.put(turningMotors.get(2), drivingMotors.get(2));
        turningDrivingMotorsMap.put(turningMotors.get(3), drivingMotors.get(3));

        Vector.motorsToVectorPositions.put(turningMotors.get(0), new Vector(-1, 1));
        Vector.motorsToVectorPositions.put(turningMotors.get(1), new Vector(1, 1));
        Vector.motorsToVectorPositions.put(turningMotors.get(2), new Vector(-1, -1));
        Vector.motorsToVectorPositions.put(turningMotors.get(3), new Vector(1, -1));
    }
    public static final class TankConstants {

        public static final String backLeftTankMotor = "backLeftTankMotor", backRightTankMotor = "backRightTankMotor";

        public static Motor leftMotor, rightMotor;

        public static void initConstants(Motor leftMotor, Motor rightMotor) {
            TankConstants.leftMotor = leftMotor;
            TankConstants.rightMotor = rightMotor;
            leftMotor.stopAndResetEncoder();
            leftMotor.setRunMode(Motor.RunMode.RawPower);
            rightMotor.stopAndResetEncoder();
            rightMotor.setRunMode(Motor.RunMode.RawPower);
        }
    }

    public static final class MecanumConstants {

        public static final String frontLeftMecanumMotor = "frontLeftMecanumMotor", frontRightMecanumMotor = "frontRightMecanumMotor",
        backLeftMecanumMotor = "backLeftMecanumMotor", backRightMecanumMotor = "backRightMecanumMotor";

        public static Motor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;

        public static void initConstants(Motor frontLeftMotor, Motor frontRightMotor, Motor backLeftMotor, Motor backRightMotor) {
            MecanumConstants.frontLeftMotor = frontLeftMotor;
            MecanumConstants.frontRightMotor = frontRightMotor;
            MecanumConstants.backLeftMotor = backLeftMotor;
            MecanumConstants.backRightMotor = backRightMotor;
        }
    }
}
