package org.firstinspires.ftc.teamcode.subsystems.driveables.swerve;

import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.SwerveConstants.swerveKd;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.SwerveConstants.swerveKi;
import static org.firstinspires.ftc.teamcode.subsystems.driveables.Constants.SwerveConstants.swerveKp;

import android.util.Log;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pid.AngularPIDModule;
import org.firstinspires.ftc.teamcode.pid.PIDModule;
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

    private final PIDModule pidModule;

    /**Constructs a new {@code SwerveModule()} with an initialized {@code rotatingMotor} Motor, {@code drivingMotor}
     * Motor and {@code positionVector} Vector. Sets the distancePerPulse of the rotating motor so that the distance per rotation ticks of the motor
     * is the same as 360 degrees.*/
    @SuppressWarnings("all")
    public SwerveModule(Motor rotatingMotor, Motor drivingMotor, Vector positionVector, Telemetry telemetry) {
        this.rotatingMotor = rotatingMotor;
        this.drivingMotor = drivingMotor;
        this.rotatingMotor.setDistancePerPulse(Constants.SwerveConstants.swerveDistancePerPulse);
        this.positionVector = positionVector.deepCopy();
        this.rotatingMotor.setInverted(true);
        this.rotatingMotor.encoder.setDirection(Motor.Direction.REVERSE);
        pidModule = new AngularPIDModule(telemetry, this.rotatingMotor, swerveKp, swerveKi, swerveKd);
    }

    /**@return The current normalized orientation of the module, within the range of 0 to 360 degrees.*/
    public double getCurrentModuleHeading() {
        return rotatingMotor.getDistance();
    }

    /**Calculates the vector that is going to represent the contribution that this module has to provide in order to meet the needs of the general driving
     * vector of the robot, provided by the human driver's joystick input.
     *
     * @return The rotated and translated vector that represents this modules translation and rotation contribution to the robot's overall
     * driving and rotating vector.*/
    @SuppressWarnings("all")
    public Vector calculateTranslatedAndRotatedMotorVector(boolean rotating, boolean clockwise, Vector driverVector, double rotationPower) {
        Vector positionVector = new Vector(0, 0, 0);
        if(rotating && clockwise) {
            positionVector = Vector.rotate90DegreesClockwise.apply(this.positionVector.getX(), this.positionVector.getY()).times(rotationPower);
        } else if(rotating) {
            positionVector = Vector.rotate90DegreesCounterclockwise.apply(this.positionVector.getX(), this.positionVector.getY()).times(rotationPower);
        }
        return driverVector.plus(positionVector.times(0));
    }

    public void applyTransAndRotVectorToMotor(Vector translatedAndRotatedVector, int currentRobotOrientation) {
        int angle = translatedAndRotatedVector.getMagnitude() <= Constants.SwerveConstants.swerveTolerance ? 0 : (int) (Math.toDegrees(Math.atan2(
                translatedAndRotatedVector.getY(), translatedAndRotatedVector.getX()
        ))) - 90;
        double currentMotorPosition = getCurrentModuleHeading();
        double absoluteHeading = currentMotorPosition + currentRobotOrientation;
        double normalizedHeading = SwerveMath.normalizeHeading(angle, absoluteHeading);
        finalNormalizedHeading = (int) SwerveMath.reverseHeading(normalizedHeading, absoluteHeading, currentMotorPosition + normalizedHeading);
        if(Math.abs(finalNormalizedHeading - previousNormalizedHeading) > Constants.tolerance) {
            previousNormalizedHeading = finalNormalizedHeading;
            pidModule.setTarget(finalNormalizedHeading);
            // controller.setSetPoint(finalNormalizedHeading);
            /*Keep the above for now, just in case we need to set it back.*/
        }
        double calculate = pidModule.calculate();
        rotatingMotor.set(calculate);
        Log.i(getClass().getSimpleName(), "Distance: " + pidModule.getDistance());
        Log.i(getClass().getSimpleName(), "Calculate: " + calculate);
    }
}


