package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.geometry.Vector2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public interface ChassisState {

    double getCurrentOrientation(AngleUnit angleUnit);

    double getAverageDistanceTraveledInTicks();

    double getAverageMotorPower();

    double getAverageVelocity();

    Vector2d getFieldPosition();
}