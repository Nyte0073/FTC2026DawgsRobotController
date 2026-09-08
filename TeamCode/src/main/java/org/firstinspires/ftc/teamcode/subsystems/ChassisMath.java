package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.geometry.Vector2d;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ChassisMath {

    public static final Function<List<Double>, Double> sum = doubles -> {
        double d = 0;
        for (double aDouble : doubles) {
            d += aDouble;
        }
        return d;
    };

    public static final BiFunction<Double, Double, Double> vectorMagnitude = Math::hypot,
            currentMotorPositionTicksToDegrees = (currentMotorPositionTicks, motorTicksPerRev) ->
                    360 * (currentMotorPositionTicks / motorTicksPerRev),
            calculateCurrentTargetHeadingDifference = (targetHeading, currentHeading) ->
                    (targetHeading - currentHeading + 540) % 360 - 180,
            calculateTargetHeading = (forward, sideward) ->
                    Math.toDegrees(Math.atan2(forward, sideward)) - 90,
            optimizeHeading = (totalHeading, currentHeading) -> {
                double optimizedHeading;
                if(Math.abs(totalHeading) > 90 && totalHeading < 0) {
                  optimizedHeading = totalHeading + 180;
                } else if(Math.abs(totalHeading) > 90){
                   optimizedHeading = totalHeading - 180;
                } else {
                    optimizedHeading = totalHeading;
                }
                return optimizedHeading;
            },
    degreesToTicks = (positionDegrees, motorTicksPerRev) ->
            positionDegrees / 360 * motorTicksPerRev;
    public static final BiFunction<Double, Boolean, Vector2d> mecanumVectorXAndYComponents = (vectorMagnitude, frontLeftBackRight) -> {
        double x, y;
         y = vectorMagnitude < 0 ? Math.abs(vectorMagnitude) * SubsystemConstants.NEGATIVE_SIN_45 : vectorMagnitude * SubsystemConstants.POSITIVE_SIN_45;
        if(frontLeftBackRight) {
            x = vectorMagnitude < 0 ? Math.abs(vectorMagnitude) * SubsystemConstants.NEGATIVE_COS_45 : vectorMagnitude * SubsystemConstants.POSITIVE_COS_45;
        } else {
            x = vectorMagnitude < 0 ? Math.abs(vectorMagnitude) * SubsystemConstants.POSITIVE_COS_45 : vectorMagnitude * SubsystemConstants.NEGATIVE_COS_45;
        }
        return new Vector2d(x, y);
    };

    public static final BiFunction<Double, Double, Vector2d> regularVectorXAndYComponents = (vectorMagnitude, theta) ->
            new Vector2d(Math.abs(vectorMagnitude) * (Math.cos(vectorMagnitude < 0 ? theta + Math.PI : theta)), Math.abs(vectorMagnitude)
            * Math.sin(vectorMagnitude < 0 ? theta + Math.PI : theta));

}
