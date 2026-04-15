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

    List<Motor> getMotors();

    IMU getIMU();

    void invertLeftSideEncoders(boolean inverted);

    void invertRightSideEncoders(boolean inverted);

    void invertLeftSideMotors(boolean inverted);

    void invertRightSideMotors(boolean inverted);
}
