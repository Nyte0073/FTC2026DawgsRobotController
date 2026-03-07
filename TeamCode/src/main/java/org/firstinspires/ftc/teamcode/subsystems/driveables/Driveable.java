package org.firstinspires.ftc.teamcode.subsystems.driveables;

public interface Driveable {

    /**Drives the robot in a specific way depending on the type
     *  and configuration of the drivetrain extending this interface.*/
    void drive();

    /**Stops all the motors on the drivetrain from moving, including the ones
     * for hardware other than the driving and steering motors of the robot's drivetrain.*/
    void stop();
}
