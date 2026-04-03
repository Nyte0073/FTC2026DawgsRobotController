package org.firstinspires.ftc.teamcode.subsystems.driveables;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pid.IMUAngularModule;
import org.firstinspires.ftc.teamcode.pid.LinearPIDModule;
import org.firstinspires.ftc.teamcode.subsystems.driveables.swerve.SwerveModule;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiFunction;

public final class Constants {

    /**The amount of ticks the motor encoders count every full rotation of the wheel.*/
    public static final int ticksPerRev = 1440;

    /**The length of the field, in inches.*/
    public static final int fieldLength = 144;

    public static final double deadZoneTolerance = 0.12, degreePercentageToDegrees = 360;

    /**Telemetry to use to send information from the robot to the FTCDashboard.*/
    public static final Telemetry telemetry = FtcDashboard.getInstance().getTelemetry();

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
        public static final double swerveTolerance = 0.08,
        swerveKp = 0.01, swerveKi = 0, swerveKd = 0.0005;

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
                rotatingMotor.setRunMode(Motor.RunMode.RawPower);
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

        /**P, I and D coefficients to be used in the PID system for autos to have control over how the motor power is set to
         * the robot depending on the error between it and its target position.*/
        public static final double mecanumKp = 0.08, mecanumKs = 0, mecanumKd = 0.00297;

        /***/
        public static final double distancePerPulseInches = 0.03343806, distancePerPulseAngle = 0.25;
        public static final List<Motor> turningMotors = new ArrayList<>();
        //1.52253517

        public static final LinkedHashMap<Motor, LinearPIDModule> mecanumPIDModules = new LinkedHashMap<>();
        public static final LinkedHashMap<Motor, IMUAngularModule> mecanumAngularIMUPIDModules = new LinkedHashMap<>();

        /**Initializes the mecanum motors by setting them all inverted and filling their mutable references.*/
        public static void initConstants(Motor frontLeftMotor, Motor frontRightMotor, Motor backLeftMotor, Motor backRightMotor, boolean auto, IMU imu) {
            turningMotors.addAll(List.of(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor));
            for(Motor m : turningMotors) {
                m.setInverted(true);
                m.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
                m.stopAndResetEncoder();
                m.setRunMode(Motor.RunMode.RawPower);
                if(auto) {
                    m.setDistancePerPulse(distancePerPulseInches);
                }
                mecanumPIDModules.put(m, new LinearPIDModule(m, mecanumKp, mecanumKs, mecanumKd));
                mecanumAngularIMUPIDModules.put(m, new IMUAngularModule(imu, mecanumKp, mecanumKs, mecanumKd, m));
            }
        }
    }
}


