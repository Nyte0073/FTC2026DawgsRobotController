package org.firstinspires.ftc.teamcode.subsystems.driveables.swerve;

import android.util.Log;

import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.subsystem_math.SwerveMath;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Constants;
import org.firstinspires.ftc.teamcode.subsystems.driveables.Vector;

/**Class representing a drive motor-turn motor pair and the calculations necessary in order to
 * make module travel in the right direction to make the robot translate and/or rotate to where it wants.*/
public class SwerveModule {

    /**Reference to the module's rotating motor.*/
    public final Motor rotatingMotor,
    /**Reference to the module's driving motor.*/
    drivingMotor;

    /**Reference to the modules position relative to the center of the robot.*/
    public final Vector positionVector;

    /**The final normalized heading for the module to turn once all the calculations are done to calculate the target angle, which will
     * will be stored as the value of this variable.*/
    private volatile int finalNormalizedHeading = 0;

    private int previousNormalizedHeading = 0;

    /**Constructs a new {@code SwerveModule()} with an initialized {@code rotatingMotor} Motor, {@code drivingMotor}
     * Motor and {@code positionVector} Vector. Sets the distancePerPulse of the rotating motor so that the distance per rotation ticks of the motor
     * is the same as 360 degrees.*/
    @SuppressWarnings("all")
    public SwerveModule(Motor rotatingMotor, Motor drivingMotor, Vector positionVector) {
        this.rotatingMotor = rotatingMotor;
        this.drivingMotor = drivingMotor;
        this.rotatingMotor.setDistancePerPulse(Constants.SwerveConstants.swerveDistancePerPulse);
        this.positionVector = positionVector.deepCopy();
    }

    /**@return The current normalized orientation of the module, within the range of 0 to 360 degrees.*/
    public int getCurrentModuleHeading() {
        double position = rotatingMotor.getDistance();
        int angle = (int) position;
        return (angle % 360 + 360) % 360;
    }

    /**Calculates the vector that is going to represent the contribution that this module has to provide in order to meet the needs of the general driving
     * vector of the robot, provided by the human driver's joystick input.
     *
     * @return The rotated and translated vector that represents this modules translation and rotation contribution to the robot's overall
     * driving and rotating vector.*/
    @SuppressWarnings("all")
    public Vector calculateTranslatedAndRotatedMotorVector(boolean rotating, boolean clockwise, Vector driverVector, double rotationPower) {
        Vector positionVector = Vector.motorsToVectorPositions.get(this);
        if(rotating && clockwise) {
            positionVector = Vector.rotate90DegreesClockwise.apply(this.positionVector.getX(), this.positionVector.getY()).times(rotationPower);
        } else if(rotating) {
            positionVector = Vector.rotate90DegreesCounterclockwise.apply(this.positionVector.getX(), this.positionVector.getY()).times(rotationPower);
        } else {
            positionVector = new Vector(0, 0, 0);
        }
        return driverVector.plus(positionVector.times(0));
    }

    public void applyTransAndRotVectorToMotor(Vector translatedAndRotatedVector, int currentRobotOrientation) {
        int angle = translatedAndRotatedVector.getMagnitude() <= Constants.SwerveConstants.swerveTolerance ? 0 : (int) (Math.toDegrees(Math.atan2(
                translatedAndRotatedVector.getY(), translatedAndRotatedVector.getX()
        ))) - 90;
        int currentMotorPosition = getCurrentModuleHeading();
        int absoluteHeading = currentMotorPosition + currentRobotOrientation;
        double normalizedHeading = SwerveMath.normalizeHeading(angle, absoluteHeading);
        finalNormalizedHeading = (int) SwerveMath.reverseHeading(normalizedHeading, absoluteHeading, currentMotorPosition + normalizedHeading);
        if(Math.abs(finalNormalizedHeading - previousNormalizedHeading) > Constants.tolerance) {
            rotatingMotor.setTargetDistance(finalNormalizedHeading);
            previousNormalizedHeading = finalNormalizedHeading;
        }
        Log.i(getClass().getSimpleName(), "Normalized heading: " + finalNormalizedHeading);
        rotatingMotor.set(0.1);
//        drivingMotor.set(Math.min(1, translatedAndRotatedVector.getMagnitude()));
    }
}


