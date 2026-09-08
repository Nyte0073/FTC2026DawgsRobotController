package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Supplier;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;
import java.util.Map;

public abstract class Chassis extends SubsystemBase implements HardwareComponent {

    private final Telemetry telemetry;
    private IMUWrapper imu;
    private final HardwareMap hardwareMap;
    private final List<MotorWrapper> driveMotors;
    private boolean fieldOriented;
    private final Supplier<Double> getX, getY,getRot;

    public Chassis(Telemetry telemetry, HardwareMap hardwareMap, List<MotorWrapper> driveMotors, GamepadExWrapper gamepadEx) {
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;
        this.driveMotors = driveMotors;
        this.getX = gamepadEx::getLeftX;
        this.getY = gamepadEx::getLeftY;
        this.getRot = gamepadEx::getRightX;
    }

    @Override
    public void function() {
        if(drivingEnabled()) {
            try {
                drive();
            } catch(Exception e) {
                shutdown();
                throw new RuntimeException(e);
            }
        } else {
            stopMotors();
        }
    }

    /*Returns the gyroscope device used for determining the orientation (in degrees, prematurely) of the robot
    * things like certain field oriented drive methods for certain drivetrains.*/
    public IMU getIMU() {
        if(imu == null && hardwareMap != null) {
            imu = new IMUWrapper(hardwareMap, SubsystemConstants.IMU_ID);
        } else {
            imu = new IMUWrapper("imu");
        }
        return imu.imu;
    }

    /*Sets the relative orientation of the robot to either the field or the robot itself, depending on how the robot is desired to
    * be driven.*/
    public void setFieldOriented(boolean fieldOriented) {
        this.fieldOriented = fieldOriented;
    }

    /*Gets the state of whether the robot is field oriented or not.*/
    public boolean isFieldOriented() {
        return fieldOriented;
    }

    /*The main driving method for every type of drivetrain*/
    public abstract void drive();

    /*Making sure that driving is enabled before any drive code is called. For this example, depending on the type of drivetrain,
    * this method could be overridden to return true or false for things like if the drivetrain is having an issue, then return false,
    * or if the drivetrain is currently busy doing another drive action and no more drive input is allowed.*/
    public abstract boolean drivingEnabled();

    /*Making sure that the chassis itself has a way to shut itself down, because for example, if a swerve system is using an executor
    * service, if something goes wrong and an exception is called, the executor service must be shut down before the program is shut down.*/
    public abstract void shutdown();

    public abstract void stopMotors();

    /*This will return mapping to information that should be displayed in the telemetry object given by using the CommandOpMode
    * extension class. This method is made abstract to assure that the user doesn't necessarily have to get information from the robot,
    * but can display anything they want by simply filling a map with any values they please as the values for the telemetry output.*/
    public abstract Map<String, Object> getTelemetryInformation();

    /*Method is used for the chassis state class to get the motors of wheels necessary for driving, thus the ChassisState class can
    * return information on the wheels using the motors that control them.*/
    public List<MotorWrapper> getSubsystemWheelMotors() {
        return driveMotors;
    }

    public double getForward() {
        return getY.get();
    }

    public double getSideward() {
        return getX.get();
    }

    public double getRotation() {
        return getRot.get();
    }

    @Override
    public void periodic() {
        telemetry.addLine(getName() + " Subsystem information:");
        for(Map.Entry<String, Object> entry : getTelemetryInformation().entrySet()) {
            telemetry.addData(entry.getKey(), entry.getValue());
        }
    }
}
