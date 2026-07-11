package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pathplanning.Trajectory;

import java.util.Map;

public class MecanumDriveTrain extends Drivetrain {

    private final MecanumDrive drive;
    private final Motor[] motors = new Motor[4];
    private final PIDController controller = new PIDController(Constants.KP, Constants.KI, Constants.KD);

    public MecanumDriveTrain(RobotInput input, HardwareMap hardwareMap) {
        super(input);
        motors[0] = new Motor(hardwareMap, MecanumMotor.FRONT_LEFT.motorID);
        motors[1] = new Motor(hardwareMap, MecanumMotor.FRONT_RIGHT.motorID);
        motors[2] = new Motor(hardwareMap, MecanumMotor.BACK_LEFT.motorID);
        motors[3] = new Motor(hardwareMap, MecanumMotor.BACK_RIGHT.motorID);
        drive = new MecanumDrive(
             motors[0], motors[1], motors[2], motors[3]
        );
    }

    public MecanumDriveTrain(HardwareMap hardwareMap) {
        motors[0] = new Motor(hardwareMap, MecanumMotor.FRONT_LEFT.motorID);
        motors[1] = new Motor(hardwareMap, MecanumMotor.FRONT_RIGHT.motorID);
        motors[2] = new Motor(hardwareMap, MecanumMotor.BACK_LEFT.motorID);
        motors[3] = new Motor(hardwareMap, MecanumMotor.BACK_RIGHT.motorID);
        drive = new MecanumDrive(
                motors[0], motors[1], motors[2], motors[3]
        );
    }

    @Override
    public void drive(boolean fieldOriented, boolean vectorControlled, Trajectory vectorTrajectory) {
        Map<RobotInput.InputType, Double> inputMap = getInput().getRobotDriveBaseInput();
        double sideward = vectorTrajectory.magnitude * Math.cos(vectorTrajectory.rotation) * Constants.DAMPER,
                forward = vectorTrajectory.magnitude * Math.sin(vectorTrajectory.rotation) * Constants.DAMPER;
        if(fieldOriented && vectorControlled) {
            drive.driveFieldCentric(sideward, forward, 0, vectorTrajectory.rotation);
        } else if(vectorControlled) {
            drive.driveRobotCentric(sideward, forward, 0);
        } else if(fieldOriented) {
            drive.driveFieldCentric(inputMap.get(RobotInput.InputType.SIDEWARDS),
                    inputMap.get(RobotInput.InputType.FORWARD), inputMap.get(RobotInput.InputType.ROTATION),
                    inputMap.get(RobotInput.InputType.ORIENTATION_DEGREES));
        } else {
            drive.driveRobotCentric(inputMap.get(RobotInput.InputType.SIDEWARDS), inputMap.get(RobotInput.InputType.FORWARD),
                    inputMap.get(RobotInput.InputType.ROTATION));
        }
    }

    @Override
    public void stop() {
        drive.stop();
    }

    @Override
    public PIDController getPIDController() {
        return controller;
    }

    @Override
    public Motor[] getMotors() {
        return motors;
    }

    public enum MecanumMotor {
        FRONT_LEFT("frontLeft"),
        FRONT_RIGHT("frontRight"),
        BACK_LEFT("backLeft"),
        BACK_RIGHT("backRight");

        public final String motorID;

        MecanumMotor(String motorID) {
            this.motorID = motorID;
        }
    }
}
