package org.firstinspires.ftc.teamcode.subsystems;

import android.util.Log;

import com.arcrobotics.ftclib.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.HashMap;
import java.util.Map;

public class SwerveChassisState implements ChassisState {

    private final SwerveChassis swerveChassis;
    private final IMU imu;
    private Vector2d previousFieldPosition = new Vector2d(0, 0);
    private final Map<MotorWrapper, Double> previousCalculatedDistance = new HashMap<>();

    public SwerveChassisState(SwerveChassis swerveChassis) {
        this.swerveChassis = swerveChassis;
        imu = swerveChassis.getIMU();
        for(MotorWrapper motorWrapper : swerveChassis.getSubsystemWheelMotors()) {
            motorWrapper.resetEncoder();
            previousCalculatedDistance.put(motorWrapper, 0.0);
        }
    }

    @Override
    public double getCurrentOrientation(AngleUnit angleUnit) {
        return angleUnit.fromDegrees(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
    }

    @Override
    public double getAverageDistanceTraveledInTicks() {
        return getFieldPosition().magnitude();
    }

    @Override
    public double getAverageMotorPower() {
        double totalMotorPower = 0;
        for(MotorWrapper motorWrapper : swerveChassis.getSubsystemWheelMotors()) {
            totalMotorPower += motorWrapper.get();
        }
        return totalMotorPower / 4.0;
    }

    @Override
    public double getAverageVelocity() {
        double totalVelocity = 0;
        for(MotorWrapper motorWrapper : swerveChassis.getSubsystemWheelMotors()) {
            totalVelocity += motorWrapper.getVelocity();
        }
        return totalVelocity / 4.0;
    }

    @Override
    public Vector2d getFieldPosition() {
        double frontLeftDistance = 0, frontRightDistance = 0, backLeftDistance = 0, backRightDistance = 0,
        frontLeftOrientation = 0, frontRightOrientation = 0, backLeftOrientation = 0, backRightOrientation = 0;
        String motorName;
        double currentRobotOrientation = getCurrentOrientation(AngleUnit.DEGREES);
        for(SwerveChassis.SwerveModule module : swerveChassis.swerveModules) {
            MotorWrapper drivingMotor = module.drivingMotor, turningMotor = module.turningMotor;
            motorName = drivingMotor.motorName.toLowerCase();
            double mDistance = drivingMotor.getDistance();
            double distanceDifference = mDistance - previousCalculatedDistance.get(drivingMotor);
            previousCalculatedDistance.put(drivingMotor, mDistance);
            double positionTicksToDegrees = ChassisMath.currentMotorPositionTicksToDegrees.apply((double) turningMotor.getCurrentPosition(),
                    turningMotor.getCPR());
            switch(getMotor(motorName)) {
                case 0:
                    frontLeftDistance = distanceDifference;
                    frontLeftOrientation = positionTicksToDegrees;
                    break;

                case 1:
                    frontRightDistance = distanceDifference;
                    frontRightOrientation = positionTicksToDegrees;
                    break;

                case 2:
                    backLeftDistance = distanceDifference;
                    backLeftOrientation = positionTicksToDegrees;
                    break;

                case 3:
                    backRightDistance = distanceDifference;
                    backRightOrientation = positionTicksToDegrees;
                    break;

                default:
                    Log.i(getClass().getSimpleName(), "Monkey. Wrong input. Monkey.");
            }
        }
        Vector2d fieldPositionDelta = (ChassisMath.regularVectorXAndYComponents.apply(frontLeftDistance, frontLeftOrientation)
                .plus(ChassisMath.regularVectorXAndYComponents.apply(frontRightDistance, frontRightOrientation)).plus(
                        ChassisMath.regularVectorXAndYComponents.apply(backLeftDistance, backLeftOrientation)
                ).plus(ChassisMath.regularVectorXAndYComponents.apply(backRightDistance, backRightOrientation))).scale(0.25);

            fieldPositionDelta = fieldPositionDelta.rotateBy(currentRobotOrientation);
        previousFieldPosition = previousFieldPosition.plus(fieldPositionDelta);
        return previousFieldPosition;
    }

    private int getMotor(String motorName) {
        if(motorName.contains(SubsystemConstants.FRONT_LEFT_MOTOR_UNIVERSAL_IDENTIFIER)) {
            return 0;
        } else if(motorName.contains(SubsystemConstants.FRONT_RIGHT_MOTOR_UNIVERSAL_IDENTIFIER)) {
            return 1;
        } else if(motorName.contains(SubsystemConstants.BACK_LEFT_MOTOR_UNIVERSAL_IDENTIFIER)) {
            return 2;
        } else {
          return 3;
        }
    }
}
