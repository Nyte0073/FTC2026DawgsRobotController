package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.HashMap;
import java.util.Map;

public class MecanumChassisState implements ChassisState {

    private final MecanumChassis mecanumChassis;
    private final IMU imu;
    private final Map<MotorWrapper, Double> previousDistanceTraveled = new HashMap<>();
    private Vector2d previousFieldPosition = new Vector2d(0, 0);
    private double previousAverageDistance = 0;

    public MecanumChassisState(MecanumChassis mecanumChassis) {
        this.mecanumChassis = mecanumChassis;
        imu = mecanumChassis.getIMU();
        for(MotorWrapper m : mecanumChassis.getSubsystemWheelMotors()) {
            m.resetEncoder();
            previousDistanceTraveled.put(m, 0.0);
        }
    }

    @Override
    public double getCurrentOrientation(AngleUnit angleUnit) {
        return angleUnit.fromDegrees(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
    }

    /*getDistance() method for motors has to return distance in ticks, which is what they are supposed to do anyway.
    * Later, I will add a balancing constant that convert between any measurement by changing value.*/
    @Override
    public double getAverageDistanceTraveledInTicks() {
        return getFieldPosition().magnitude();
    }

    @Override
    public double getAverageMotorPower() {
        double d = 0;
        for(MotorWrapper motorWrapper : mecanumChassis.getSubsystemWheelMotors()) {
            d += motorWrapper.get();
        }
        return d / 4.0;
    }

    @Override
    public double getAverageVelocity() {
        return 0;
    }

    @Override
    public Vector2d getFieldPosition() {
        double currentOrientation = getCurrentOrientation(AngleUnit.DEGREES);
        double leftDistance = 0, rightDistance = 0, mDistance;
        String motorName;
            for(MotorWrapper m : mecanumChassis.getSubsystemWheelMotors()) {
                motorName = m.motorName.toLowerCase();
                mDistance = m.getDistance();
                if(motorName.contains(SubsystemConstants.FRONT_LEFT_MOTOR_UNIVERSAL_IDENTIFIER) ||
                        motorName.contains(SubsystemConstants.BACK_RIGHT_MOTOR_UNIVERSAL_IDENTIFIER)) {
                    leftDistance += mDistance - previousDistanceTraveled.get(m);
                } else if(motorName.contains(SubsystemConstants.FRONT_RIGHT_MOTOR_UNIVERSAL_IDENTIFIER) ||
                        motorName.contains(SubsystemConstants.BACK_LEFT_MOTOR_UNIVERSAL_IDENTIFIER)) {
                    rightDistance += mDistance - previousDistanceTraveled.get(m);
                }
                previousDistanceTraveled.put(m, mDistance);
            }
           Vector2d previousFieldPositionDelta = (ChassisMath.mecanumVectorXAndYComponents.apply(leftDistance, true)
            .plus(ChassisMath.mecanumVectorXAndYComponents.apply(rightDistance, false))).scale(Math.sqrt(2) / 4.0);

            if(!mecanumChassis.isFieldOriented()) {
                previousFieldPositionDelta = previousFieldPositionDelta.plus(previousFieldPositionDelta.rotateBy(currentOrientation));
            }
            previousFieldPosition = previousFieldPosition.plus(previousFieldPositionDelta);
        return previousFieldPosition;
    }
}
