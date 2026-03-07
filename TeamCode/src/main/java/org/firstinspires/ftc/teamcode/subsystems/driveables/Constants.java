package org.firstinspires.ftc.teamcode.subsystems.driveables;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public final class Constants {

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

                /**Maps each turning motor to their respective calculated rotated and translated vector.*/
        public static final Map<Motor, Vector> motorsAndTheirRotatedAndTranslatedVectors = new HashMap<>();

        /**Maps each turning motor to its respective driving motor within its specific module.*/
        public static final Map<Motor, Motor> turningDrivingMotorsMap = new HashMap<>();

        /*String constants for the id's of all the motors being used with the swerve drivetrain.*/
        public static final String frontLeftDrivingMotorID = "frontLeftDriving", frontRightDrivingMotorID = "frontRightDriving",
                backLeftDrivingMotorID = "backLeftDriving", backRightDrivingMotorID = "backRightDriving", frontLeftRotatingMotorID = "frontLeftRotating",
                frontRightRotatingMotorID = "frontRightRotating", backLeftRotatingMotorID = "backLeftRotating", backRightRotatingMotorID = "backRightRotating";

        /**Initializes the turning and driving motors so that they are ready to go as soon as the SwerveCommand implementation starts
         *executing. This method stops and resets all the motors' encoders, sets the {@code RunMode} of the turning motors to PositionControl
         * for encoder based rotation, and sets the {@code RunMode} for the driving motors to RawPower so that they can rotate freely while
         * depending on motor power for angular speed. This method also fills the {@code turningDrivingMotorsMap} and {@code motorToVectorPositions}
         * hashmaps with maps between the different motors and them and their different motor positions relative to the center of the robot. */
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
                motor.stopAndResetEncoder();
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

    /**Class containing all the constant values for running and initializing a tank drivetrain.*/
    public static final class TankConstants {

        /*String id's for the motors being used in the drivetrain.*/
        public static final String backLeftTankMotor = "backLeftTankMotor", backRightTankMotor = "backRightTankMotor";

        /*References to the back left and back right driving motors on the tank drivetrain.*/
        public static Motor leftMotor, rightMotor;

        /**Represents the x and y coordinates of the first optional position the robot can start at during auto.*/
        public static final Vector FIRST_AUTO_POSITION = new Vector(0, 0),

        /**Represents the x and y coordinates of the second optional position the robot can start at during auto.*/
        SECOND_AUTO_POSITION = new Vector(0, 0);

        /**Initializes the back left and back right driving motors of the tank drivetrain, depending on if they are in auto
         * or teleop. This method will reset the motors' encoders, while also setting their {@code RunMode}'s to either RawPower
         * or PositionControl based on if the stage is Teleop or Auto, and sets the motor tolerances to 5 so the motors have a bit of
         * leeway when rotating to a certain amount of ticks when using PositionControl.*/
        public static void initConstants(Motor leftMotor, Motor rightMotor, boolean auto) {
            TankConstants.leftMotor = leftMotor;
            TankConstants.rightMotor = rightMotor;
            leftMotor.stopAndResetEncoder();
            rightMotor.stopAndResetEncoder();
            rightMotor.setInverted(true);

            if(auto) {
               TankConstants.leftMotor.setRunMode(Motor.RunMode.PositionControl);
               TankConstants.rightMotor.setRunMode(Motor.RunMode.PositionControl);
            } else {
                TankConstants.leftMotor.setRunMode(Motor.RunMode.RawPower);
                TankConstants.rightMotor.setRunMode(Motor.RunMode.RawPower);
            }

            TankConstants.leftMotor.setPositionTolerance(tolerance);
            TankConstants.rightMotor.setPositionTolerance(tolerance);
        }
    }

    /**Class containing all the constant values and mutable objects used to run and initialize a Mecanum
     drivetrain.*/
    public static final class MecanumConstants {

        /*String id's for the motors used on the mecanum drivetrain.*/
        public static final String frontLeftMecanumMotor = "frontLeftMecanumMotor", frontRightMecanumMotor = "frontRightMecanumMotor",
                backLeftMecanumMotor = "backLeftMecanumMotor", backRightMecanumMotor = "backRightMecanumMotor";

        /*References to all four mecanum motors being used to control the drivetrain.*/
        public static Motor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;

        /**Initializes the mecanum motors by setting them all inverted and filling their mutable references.*/
        public static void initConstants(Motor frontLeftMotor, Motor frontRightMotor, Motor backLeftMotor, Motor backRightMotor) {
            MecanumConstants.frontLeftMotor = frontLeftMotor;
            MecanumConstants.frontRightMotor = frontRightMotor;
            MecanumConstants.backLeftMotor = backLeftMotor;
            MecanumConstants.backRightMotor = backRightMotor;
            MecanumConstants.frontLeftMotor.setInverted(true);
            MecanumConstants.backLeftMotor.setInverted(true);
            MecanumConstants.backRightMotor.setInverted(true);
            MecanumConstants.frontRightMotor.setInverted(true);
        }
    }
}


