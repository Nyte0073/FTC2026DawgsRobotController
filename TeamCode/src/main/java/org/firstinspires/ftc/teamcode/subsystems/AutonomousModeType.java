package org.firstinspires.ftc.teamcode.subsystems;

public enum AutonomousModeType {
    /*Once you have specific autonomous routes that you want to take, simply input the name here, and then
    * depending on the map configuration in the PathPlanners class, one of the enum constant types here will map
    * to a specific Consumer Object taking a HardwareComponent object and doing something with it.
    *
    * And because all the autonomous routes will be contained in SequentialCommandGroup's, You will be able to string multiple of these
    * consumers together, but you need to make this a feature.
    *
    * Basically, each Consumer, once having taken in the HardwareComponent instance it is working with, will then use it to generate
    * CommandBase extensions that manipulate it inside certain autonomous functions that can be stringed together into a SequentialCommandGroup
    * that will then be scheduled to the CommandScheduler. */
}
