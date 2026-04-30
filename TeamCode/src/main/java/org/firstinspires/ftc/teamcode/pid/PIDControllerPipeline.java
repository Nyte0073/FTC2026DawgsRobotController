package org.firstinspires.ftc.teamcode.pid;

import static org.firstinspires.ftc.teamcode.subsystems.driveables.factories.FactoryConstants.TeleopAndAutoConstants.mecanumKd;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.factories.FactoryConstants.TeleopAndAutoConstants.mecanumKp;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.factories.FactoryConstants.TeleopAndAutoConstants.mecanumKs;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

import java.util.LinkedList;

/**Class that can create a turn -> move sequence based off an inputted angle and a certain amount of inches to travel forward or backward.
 * This class works by taking those values and translating them into turn and move commands (controlled by PID) to make the robot rotate and travel
 * any amount of degrees and inches (using a PID Controller to control the motor power output based on the error of the change in
 * position).*/
public class PIDControllerPipeline {

    /*The command generated to turn the robot to a specific angle.*/
    private final TurnToCommand turnToCommand;

    /*The command generated to move the robot a certain amount of inches forward/backward, or left/right using strafing.*/
    private final MoveCommand moveCommand;

    /**This constructor creates new turn and move commands based on the inputted angle and amount of inches you want the robot to follow
     * along the desired path. This constructor also sets the amount of inches and the degrees of the angle as targets of the PID systems used
     * to control the robot's motor power output, to make sure the robot slows down when reaches its target and speeds up when it's far away.*/
    public PIDControllerPipeline(Vector driverVector, LinkedList<Motor> motors, IMU imu, double angle, double inches, boolean strafe) {
       IMUAngularModule angularModule = new IMUAngularModule(imu, mecanumKp, mecanumKs, mecanumKd);
       LinearPIDModule linearPIDModule = new LinearPIDModule(motors, mecanumKp, mecanumKs, mecanumKd);
       angularModule.setTarget(angle);
       linearPIDModule.setTarget(inches);
       turnToCommand = new TurnToCommand(driverVector, angularModule, motors, imu);
       moveCommand = new MoveCommand(driverVector, linearPIDModule, motors, imu, strafe);
    }

    /**@return The command to make the robot turn a certain amount of degrees.*/
    public TurnToCommand getTurnToCommand() {
        return turnToCommand;
    }

    /**@return The command to make the robot move forward
     * or backward using just plain motor power or by implementing strafing.*/
    public MoveCommand getMoveCommand() {
        return moveCommand;
    }

    /**Class to make robot rotate a certain amount of inches clockwise or counterclockwise depending on the sign of the angle
     * inputted in. This class uses PID to adjust the angular speed of the robot depending on how far away it is from its target angle.*/
    public static final class TurnToCommand extends CommandBase {
        /*Vector used to apply motor powers to the robot.*/
        private final Vector driverVector;

        /*Module used to scale motor powers based on the error between the current target orientation.*/
        private final IMUAngularModule module;

        /*Reference to the motor objects used in the command to reset the encoders of the motors after the command
        * is done.*/
        private final LinkedList<Motor> motors = new LinkedList<>();

        /*IMU reference used to reset the yaw of the gyroscope system that returns the current orientation of the robot.*/
        private final IMU imu;

        public TurnToCommand(Vector driverVector, IMUAngularModule module, LinkedList<Motor> motors, IMU imu) {
            this.driverVector = driverVector;
            this.imu = imu;
            this.module = module;
            this.motors.addAll(motors);
        }


        @Override
        public void execute() {
            double calculate = module.calculate();
            boolean calculateIsBigger = Math.abs(calculate) > 0.45;
            driverVector.setZ(calculateIsBigger ? -0.45 * Math.signum(calculate) : -calculate);
        }

        @Override
        public boolean isFinished() {
            return module.atTarget();
        }

        @Override
        public void end(boolean interrupted) {
            driverVector.setZ(0);
            for(Motor m : motors) {
                m.resetEncoder();
            }
            imu.resetYaw();
        }
    }

    public static final class MoveCommand extends CommandBase {

        /*Vector that controls setting the power of the drive motors of the robot.*/
        private final Vector driverVector;

        /*PID controlled module with built in PID system to control the output of the robot's drive motor powers depending
        * on the difference between the target and current position (usually in inches).*/
        private final LinearPIDModule module;

        /*Reference to the drive motors of the robot, used in this code to gain access to the function that resets their encoders after
        * every use of an instance of this Command base.*/
        private final LinkedList<Motor> motors = new LinkedList<>();

        /*Reference to the gyroscope system of the robot, used in this command base to gain access to the option to
        * reset the yaw of the system so that the robot's current position gets reset to zero.*/
        private final IMU imu;

        /*Boolean state of whether the robot will strafe a certain distance or not.*/
        private final boolean strafe;

        public MoveCommand(Vector driverVector, LinearPIDModule module, LinkedList<Motor> motors, IMU imu, boolean strafe) {
            this.driverVector = driverVector;
            this.strafe = strafe;
            this.imu = imu;
            this.module = module;
            this.motors.addAll(motors);
        }

        @Override
        public void initialize() {
            if(strafe) {
                /*todo back left encoder needs to be reversed,
                *  front right encoder needs to be reversed as well.*/
                for(Motor m : motors) {
                    if(m == motors.get(2) || m == motors.get(3)) {
                        m.encoder.setDirection(Motor.Direction.REVERSE);
                    }
                }
            }
        }

        @Override
        public void execute() {
            double calculate = module.calculate();
            boolean calculateIsBigger = Math.abs(calculate) > 0.6;
            double finalCalculate = calculateIsBigger ? 0.6 * Math.signum(calculate) : calculate;
            if(strafe) {
                driverVector.setX(finalCalculate);
            } else {
                driverVector.setY(finalCalculate);
            }
        }

        @Override
        public boolean isFinished() {
            return module.atTarget();
        }

        @Override
        public void end(boolean interrupted) {
            driverVector.setY(0);
            for(Motor m : motors) {
                m.resetEncoder();
            }
            imu.resetYaw();
            if(strafe) {
                /*todo back left encoder needs to be reversed,
                 *  front right encoder needs to be reversed as well.*/
                motors.get(1).encoder.setDirection(Motor.Direction.FORWARD);
                motors.get(2).encoder.setDirection(Motor.Direction.FORWARD);
            }
        }
    }
}