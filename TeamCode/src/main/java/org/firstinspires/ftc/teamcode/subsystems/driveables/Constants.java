package org.firstinspires.ftc.teamcode.subsystems.driveables;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class Constants {
    /**The amount of ticks the motor encoders count every full rotation of the wheel.*/
    public static final int ticksPerRev = 1440;

    /**The length of the field, in inches.*/
    public static final int fieldLength = 144;

    /**Function for calculating the distance in ticks required to make the robot go the target vector, based on the input from the robot's
     * current position vector.*/

    public static final double deadZoneTolerance = 0.12, degreePercentageToDegrees = 360;
    public static final BiFunction<Vector, Vector, Vector> robotCoordinatesAndTargetToCoordinates = (robotCoordinates, targetCoordinates) -> {
        Vector v = new Vector();
        double robotX = robotCoordinates.getX();
        double robotY = robotCoordinates.getY();
        double targetX = targetCoordinates.getX();
        double targetY = targetCoordinates.getY();

        v.setX((targetX - robotX) * ticksPerRev);
        v.setY((targetY - robotY) * ticksPerRev);
        return v;
    };

    /**The angle tolerance allowed for the robot so that the robot can be off by a few degrees and still be considered at angle.*/
    public static final double tolerance = 5;

    /**Class that contains all the constant values and mutable objects used for a swerve drivetrain. */
    public static final class SwerveConstants {

        /**List containing references to the swerve drivetrain's driving motors (the motor that only provide forward and backward motion
         * relative to itself.*/
        public static final List<Motor> drivingMotors = new ArrayList<>(),
        /**List containing references to the swerve drivetrain's turning motors (The motors that provide forward and backward motion that gives the
         driving motor's magnitude (motor power) a direction to then turn into a vector to add to the robot's final translation vector.*/
                turningMotors = new ArrayList<>();

                /***/
        public static final Map<Motor, Vector> motorsAndTheirRotatedAndTranslatedVectors = new HashMap<>();
        public static final Map<Motor, Motor> turningDrivingMotorsMap = new HashMap<>();
        public static final String frontLeftDrivingMotorID = "frontLeftDriving", frontRightDrivingMotorID = "frontRightDriving",
                backLeftDrivingMotorID = "backLeftDriving", backRightDrivingMotorID = "backRightDriving", frontLeftRotatingMotorID = "frontLeftRotating",
                frontRightRotatingMotorID = "frontRightRotating", backLeftRotatingMotorID = "backLeftRotating", backRightRotatingMotorID = "backRightRotating";

        public static void initConstants(List<Motor> drivingMotors, List<Motor> turningMotors) {
            if(!drivingMotors.isEmpty()) {
                SwerveConstants.drivingMotors.addAll(drivingMotors);
            }
            SwerveConstants.turningMotors.addAll(turningMotors);
            for(Motor motor : drivingMotors) {
                motor.stopAndResetEncoder();
                motor.setRunMode(Motor.RunMode.RawPower);
            }
            for(Motor motor : turningMotors) {
                motor.resetEncoder();
                motor.setRunMode(Motor.RunMode.PositionControl);
            }
            if(!drivingMotors.isEmpty()) {
                turningDrivingMotorsMap.put(turningMotors.get(0), drivingMotors.get(0));
                turningDrivingMotorsMap.put(turningMotors.get(1), drivingMotors.get(1));
                turningDrivingMotorsMap.put(turningMotors.get(2), drivingMotors.get(2));
                turningDrivingMotorsMap.put(turningMotors.get(3), drivingMotors.get(3));
            }

            Vector.motorsToVectorPositions.put(turningMotors.get(0), new Vector(-1, 1));
            Vector.motorsToVectorPositions.put(turningMotors.get(1), new Vector(1, 1));
            Vector.motorsToVectorPositions.put(turningMotors.get(2), new Vector(-1, -1));
            Vector.motorsToVectorPositions.put(turningMotors.get(3), new Vector(1, -1));
        }
    }

    public static final class TankConstants {

        public static final String backLeftTankMotor = "backLeftTankMotor", backRightTankMotor = "backRightTankMotor";
        public static Motor leftMotor, rightMotor;
        public static final Vector FIRST_AUTO_POSITION = new Vector(0, 0),
        SECOND_AUTO_POSITION = new Vector(0, 0);

        public static void initConstants(Motor leftMotor, Motor rightMotor) {
            TankConstants.leftMotor = leftMotor;
            TankConstants.rightMotor = rightMotor;
            leftMotor.stopAndResetEncoder();
            rightMotor.stopAndResetEncoder();
            rightMotor.setInverted(true);

            TankConstants.leftMotor.setRunMode(Motor.RunMode.RawPower);
            TankConstants.rightMotor.setRunMode(Motor.RunMode.RawPower);
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


