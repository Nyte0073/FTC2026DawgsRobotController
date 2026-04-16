package org.firstinspires.ftc.teamcode.subsystems.driveables;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.IMU;

import java.util.List;

public interface Driveable {

    /**Drives the robot in a specific way depending on the type
     *  and configuration of the drivetrain extending this interface.*/
    void drive();

    /**Stops all the motors on the drivetrain from moving, including the ones
     * for hardware other than the driving and steering motors of the robot's drivetrain.*/
    void stop();

    /**@return The collection of motors used for the robot's drivetrain.*/
    List<Motor> getMotors();

    /**@return The gyroscope system used to return the current angle the robot is oriented to on the the field relative to a direction called
     * 0 degrees.*/
    IMU getIMU();

    /**Sets whether the encoders of the left side motors of the robot are reversed or not.*/
    void invertLeftSideEncoders(boolean inverted);

    /**Sets whether the encoders of the right side motors of the robot are reversed or not.*/
    void invertRightSideEncoders(boolean inverted);

    /**Sets whether the left side motors of the robot are reversed or not.*/
    void invertLeftSideMotors(boolean inverted);

    /**Sets whether the right side motors of the robot are reevrsed or not.*/
    void invertRightSideMotors(boolean inverted);
}
