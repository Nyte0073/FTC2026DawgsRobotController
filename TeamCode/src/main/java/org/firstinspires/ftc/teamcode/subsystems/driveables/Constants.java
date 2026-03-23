package org.firstinspires.ftc.teamcode.subsystems.driveables;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.subsystems.driveables.swerve.SwerveModule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public final class Constants {

    /**The amount of ticks the motor encoders count every full rotation of the wheel.*/
    public static final int ticksPerRev = 1440;

    /**The length of the field, in inches.*/
    public static final int fieldLength = 144;

    public static final double deadZoneTolerance = 0.12, degreePercentageToDegrees = 360;

    public static final List<GamepadKeys.Button> buttons = List.of(
            GamepadKeys.Button.DPAD_DOWN,
            GamepadKeys.Button.DPAD_LEFT,
            GamepadKeys.Button.DPAD_RIGHT,
            GamepadKeys.Button.DPAD_UP,
            GamepadKeys.Button.A,
            GamepadKeys.Button.B,
            GamepadKeys.Button.X,
            GamepadKeys.Button.Y
    );
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
    public static final double tolerance = 6;

    /**Class that contains all the constant values and mutable objects used for a swerve drivetrain. */
    public static final class SwerveConstants {

        public static final double swerveDistancePerPulse = 0.25;

        /*String constants for the id's of all the motors being used with the swerve drivetrain.*/
        public static final String frontLeftDrivingMotorID = "frontLeftDriving", frontRightDrivingMotorID = "frontRightDriving",
                backLeftDrivingMotorID = "backLeftDriving", backRightDrivingMotorID = "backRightDriving", frontLeftRotatingMotorID = "frontLeftRotating",
                frontRightRotatingMotorID = "frontRightRotating", backLeftRotatingMotorID = "backLeftRotating", backRightRotatingMotorID = "backRightRotating";

        public static SwerveModule frontLeftModule, frontRightModule, backLeftModule, backRightModule;
        public static final List<SwerveModule> swerveModules = new ArrayList<>();
        public static final double swerveTolerance = 0.08;

        public static void initConstants(SwerveModule frontLeftModule, SwerveModule frontRightModule, SwerveModule backLeftModule, SwerveModule backRightModule) {
            SwerveConstants.frontLeftModule = frontLeftModule;
            SwerveConstants.frontRightModule = frontRightModule;
            SwerveConstants.backLeftModule = backLeftModule;
            SwerveConstants.backRightModule = backRightModule;
            swerveModules.addAll(List.of(frontLeftModule, frontRightModule, backLeftModule, backRightModule));

            for(SwerveModule module : swerveModules) {
                Motor rotatingMotor = module.rotatingMotor;
                Motor drivingMotor = module.drivingMotor;

                rotatingMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
                rotatingMotor.stopAndResetEncoder();
                rotatingMotor.setRunMode(Motor.RunMode.PositionControl);
                rotatingMotor.setPositionTolerance(4);

//                drivingMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//                drivingMotor.setRunMode(Motor.RunMode.RawPower);
            }
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

            MecanumConstants.frontLeftMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            MecanumConstants.frontRightMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            MecanumConstants.backLeftMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            MecanumConstants.backRightMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

            MecanumConstants.frontLeftMotor.stopAndResetEncoder();
            MecanumConstants.frontRightMotor.stopAndResetEncoder();
            MecanumConstants.backLeftMotor.stopAndResetEncoder();
            MecanumConstants.backRightMotor.stopAndResetEncoder();

        }
    }
}


