package org.firstinspires.ftc.teamcode.autos;

import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.TankConstants.leftMotor;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.TankConstants.rightMotor;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

/**Class for running the autonomous period for the tank drivetrain. This class should be tuned so that
 * the auto positions reflect the robot's true starting position, so that the program can calculate
 * the correct distances for the robot to get to target positions.*/
@Autonomous(name = "TankAuto", group = "teamcode")
public final class TankAuto extends CommandOpMode {
    @Override
    public void initialize() {
        IMU imu = hardwareMap.get(IMU.class, "imu");
        Constants.TankConstants.initConstants(leftMotor, rightMotor, true);
        schedule(new SequentialCommandGroup(
                new TravelTo(new Vector(2, 2), imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES), imu),
                new TravelTo(new Vector(3, 7), imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES), imu),
                new TravelTo(new Vector(5, 7), imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES), imu)
        ));
    }

    /**Command base extension for making the robot travel to the correct target position based on
     * the calculate the vector magnitude and angle to get there.
     *
     * What happens is that the based on the x and y coordinates of the target vector, the robot will
     * first turn to the calculated angle of the vector based on the calculated distance to the target
     * position (which is calculated by figuring out the hypotenuse/magnitude of the vector).
     * Then the robot turns to the correct angle and then travels in a straight along the edge of the
     * hypotenuse of the vector until it reaches its target position.*/
    public static final class TravelTo extends CommandBase {

        /**X and Y position that the robot needs to travel to get to its target
         * position.*/
        private final Vector targetVector,
                /**The X and Y position of where the robot currently is located on the field.
                 * This measures in inches, so the x and y positions would be relative to a 144 x 144 inch
                 * field.*/
        currentRobotVector = new Vector(0, 0);

        /**The current angle the robot is oriented to.*/
        private final double currentRobotOrientation;

        /**The calculated X and Y position that gives the hypotenuse line the robot will travel
         * along relative to its direction.*/
        private Vector calculatedTargetPositionVector;

        /**Reference to the robot's internal gyroscope, returning the angle the robot is currently
         * oriented to.*/
        private final IMU imu;

        /**Constructs a new {@code TravelTo()} with an initialized {@code targetVector} position vector,
         * {@code currentRobotOrientation} orientation double, and {@code imu} IMU variable.*/
        public TravelTo(Vector targetVector, double currentRobotOrientation, IMU imu) {
           this.targetVector = targetVector;
           this.currentRobotOrientation = currentRobotOrientation;
           this.imu = imu;
        }

        @Override
        public void initialize() {
            stopMotors();
            resetEncoders();
            calculatedTargetPositionVector = Constants.robotCoordinatesAndTargetToCoordinates.apply(Constants.TankConstants.FIRST_AUTO_POSITION
                    , targetVector);
            turnTo(Math.toDegrees(Math.atan2(calculatedTargetPositionVector.getY(), calculatedTargetPositionVector.getX())));
            stopMotors();
            resetEncoders();
            int targetPosition = (int) calculatedTargetPositionVector.getX();
            leftMotor.setTargetPosition(targetPosition);
            rightMotor.setTargetPosition(targetPosition);
            leftMotor.set(0.7);
            rightMotor.set(0.7);
        }

        /**Makes the robot turn to an angle that is the direction of the target vector calculated from the {@code initialize()}
         * method. Depending on if the difference the between the target angle and the current robot angle is negative or positive,
         * this method will set the power of the motors in a specific configuration to make the robot turn either left or right until
         *it reaches the target angle.*/
        public void turnTo(double angle) {
            double normalizedHeading = normalizeHeading(angle, imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            int leftMotorPower = normalizedHeading < 0 ? 1 : -1;
            int rightMotorPower = -leftMotorPower;
            leftMotor.set(leftMotorPower);
            rightMotor.set(rightMotorPower);
            double currentTimeMillis = System.currentTimeMillis();
            while(!Thread.currentThread().isInterrupted() && (System.currentTimeMillis() - currentTimeMillis) <= 5) {
                double currentAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
                double lowerBound = angle - 1;
                double upperBound = angle + 1;
                if(currentAngle >= lowerBound && currentAngle <= upperBound) {
                    break;
                }
            }
            rightMotor.stopMotor();
            leftMotor.stopMotor();
         }

         /**Returns the shortest between the target angle and the current angle of the robot and limits this difference to be
          * within -180 to 180 degrees.*/
        public double normalizeHeading(double current, double target) {
            return (target - current + 540) % 360 - 180;
        }

        @Override
        public boolean isFinished() {
            return leftMotor.atTargetPosition();
        }

        public void stopMotors() {
            rightMotor.stopMotor();
            leftMotor.stopMotor();
        }

        public void resetEncoders() {
            leftMotor.resetEncoder();
            rightMotor.resetEncoder();
        }
    }

    /*TODO This class is still in progress, and still needs to be
       finished with real hardware-software connection.*/
    public static final class ScorePiece extends CommandBase {

        @Override
        public void initialize() {
            super.initialize();
        }

        @Override
        public void execute() {
            super.execute();
        }

        @Override
        public boolean isFinished() {
            return super.isFinished();
        }
    }
}