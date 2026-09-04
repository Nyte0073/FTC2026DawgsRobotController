package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MecanumChassis extends Chassis {

    private final MecanumDrive mecanumChassis;
    private final ChassisState state;
    private long systemTimeMillis;

    public MecanumChassis(Telemetry telemetry, HardwareMap hardwareMap, List<MotorWrapper> driveMotors, GamepadEx gamepadEx) {
        super(telemetry, hardwareMap, driveMotors, gamepadEx);
        mecanumChassis = new MecanumDrive(
                driveMotors.get(0), driveMotors.get(1), driveMotors.get(2), driveMotors.get(3)
        );
        state = new MecanumChassisState(this);
    }

    @Override
    public void drive() {
        double forwards = getForward(), sidewards = getSideward(), rot = getRotation();
        double currentOrientation = state.getCurrentOrientation(AngleUnit.DEGREES);
        if(isFieldOriented()) {
            mecanumChassis.driveFieldCentric(sidewards, forwards, rot, currentOrientation);
        } else {
            mecanumChassis.driveRobotCentric(sidewards, forwards, rot);
        }
    }

    @Override
    public boolean drivingEnabled() {
        for(MotorWrapper motorWrapper : getSubsystemWheelMotors()) {
            double power = Math.abs(motorWrapper.get());
            double velocity = motorWrapper.getVelocity();
            if(power >= SubsystemConstants.WHEEL_STALLING_THRESHOLD && velocity <= SubsystemConstants.STALLING_VELOCITY_THRESHOLD) {
                systemTimeMillis = System.currentTimeMillis();
                return false;
            } else if((System.currentTimeMillis() - systemTimeMillis) <= SubsystemConstants.MOTOR_STALL_WAIT_TIME_MILLIS) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void shutdown() {
        mecanumChassis.stop();
    }

    @Override
    public Map<String, Object> getTelemetryInformation() {
        Map<String, Object> map = new HashMap<>();
        for(MotorWrapper wrapper : getSubsystemWheelMotors()) {
            map.put(wrapper.motorName + " velocity", wrapper.getVelocity());
            map.put(wrapper.motorName + " motor power", wrapper.get());
            map.put(wrapper.motorName + " at target position", wrapper.atTargetPosition());
        }
        return map;
    }
}
