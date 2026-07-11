package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.pathplanning.PathPlanner;
import org.firstinspires.ftc.teamcode.pathplanning.Trajectory;

import java.util.HashMap;
import java.util.Map;

public abstract class Drivetrain extends SubsystemBase {
    private final RobotInput input;
    private final Map<Hardware, RobotInput.InputType> hardware = new HashMap<>();
    public Drivetrain(RobotInput input) {
        this.input = input;
    }
    public Drivetrain() {
        input = null;
    }
    public abstract void drive(boolean fieldOriented, boolean vectorControlled, Trajectory vectorTrajectory);
    public void driveAutonomous(PathPlanner pathPlanner) {
        CommandScheduler.getInstance().schedule(
               pathPlanner.buildCommands()
        );
    }
    public abstract void stop();
    public abstract Motor[] getMotors();
    public abstract PIDController getPIDController();

    public RobotInput getInput() {
        return input;
    }

    @Override
    public void periodic() {

    }

    public Map<Hardware, RobotInput.InputType> getHardware() {
        return hardware;
    }
}
