package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.mocktesting.MockGamepadExWrapper;
import org.firstinspires.ftc.teamcode.mocktesting.MockIMU;
import org.firstinspires.ftc.teamcode.mocktesting.MockMotorWrapper;
import org.firstinspires.ftc.teamcode.pid.ChassisPIDKinematics;
import org.firstinspires.ftc.teamcode.pid.PIDControl;
import org.firstinspires.ftc.teamcode.pid.PIDWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UnitTestingClass {

    @Test
    public void testChassisMathXAndYComponents() {
        Vector2d vector2d = ChassisMath.mecanumVectorXAndYComponents.apply(-2.0, true);
        Assertions.assertEquals(2 * SubsystemConstants.NEGATIVE_COS_45, vector2d.getX());
        Assertions.assertEquals(2 * SubsystemConstants.NEGATIVE_SIN_45, vector2d.getY());
    }

    @Test
    public void testFieldPositionSwerveChassisCalculations() throws IOException {
        File file = new File("/Users/braydenjonasrussell/IdeaProjects/UnitTestingFile.txt");
        FileWriter writer = new FileWriter(file);
        double currentOrientation = Math.toRadians(0);
        double totalLeftDistance = 2;
        double totalRightDistance = -totalLeftDistance;
        Vector2d totalLeftDistanceVector = ChassisMath.regularVectorXAndYComponents.apply(totalLeftDistance, currentOrientation);
        Vector2d totalRightDistanceVector = ChassisMath.regularVectorXAndYComponents.apply(totalRightDistance, currentOrientation);
        Vector2d result = totalRightDistanceVector.plus(totalLeftDistanceVector);
        writer.write(new StringBuilder()
                .append("Total Left X: ")
                .append(totalLeftDistanceVector.getX())
                .append("\n")
                .append("Total Left Y: ")
                .append(totalLeftDistanceVector.getY())
                .append("\n")
                .append("Total Right X: ")
                .append(totalRightDistanceVector.getX())
                .append("\n")
                .append("Total Right Y: ")
                .append(totalRightDistanceVector.getY())
                .toString());
        writer.close();
        Assertions.assertEquals(0, result.getX(), 0.001);
        Assertions.assertEquals(0, result.getY(), 0.001);
    }

    @Test
    public void testMockMotorWrapper() throws Exception {
        File file = new File("/Users/braydenjonasrussell/IdeaProjects/UnitTestingFile.txt");
        FileWriter writer = new FileWriter(file);
        MockIMU mockIMU = new MockIMU("something kind of imu");
        PIDWrapper pidWrapper = new PIDWrapper(new MockMotorWrapper("some motor"), ChassisPIDKinematics.PIDKinematicsType.MECANUM_KINEMATICS);
        PIDControl pidControl = new PIDControl(
                List.of(
                    pidWrapper
                ), true
        );
        mockIMU.setYaw(190);
        pidControl.setWheelTargetPosition(pidWrapper, 1200);
        Thread.sleep(6000);
        writer.write(new StringBuilder()
                        .append(mockIMU.getCurrentYaw())
                        .append("\nMotorWrapper current position: ")
                        .append(pidWrapper.getPidControlledMotor().getCurrentPosition())
                .toString());
        writer.close();
    }

    @Test
    public void testSwerveChassisExecutorService() throws Exception {
        File file = new File("/Users/braydenjonasrussell/IdeaProjects/UnitTestingFile.txt");
        FileWriter writer = new FileWriter(file);
        List<PIDWrapper> wrappers = new ArrayList<>();
        SwerveChassis chassis = new SwerveChassis(null, null,
                Map.of(
                        new MockMotorWrapper("frontLeftDriving"), new MockMotorWrapper("frontLeftRotating"),
                        new MockMotorWrapper("frontRightDriving"), new MockMotorWrapper("frontRightRotating"),
                        new MockMotorWrapper("backLeftDriving"), new MockMotorWrapper("backLeftRotating"),
                        new MockMotorWrapper("backRightDriving"), new MockMotorWrapper("backRightRotating")
                ), new GamepadExWrapper());
        for(SwerveChassis.SwerveModule module : chassis.getSwerveModules()) {
            wrappers.add(module.turningMotorWrapper);
        }
        PIDControl pidControl = new PIDControl(wrappers, true);
        Map<PIDWrapper, Double> map = new HashMap<>();
        for(PIDWrapper pidWrapper : wrappers) {
            map.put(pidWrapper, -1805.0);
        }
        pidControl.setWheelsTargetPositions(map);
        StringBuilder stringBuilder = new StringBuilder();
        Thread.sleep(6000);
        for(PIDWrapper pidWrapper : wrappers) {
            stringBuilder.append(pidControl.getSetPoint(pidWrapper) + "\n")
                    .append("current position: " + pidWrapper.getPidControlledMotor().getCurrentPosition() + "\n");
        }
        writer.write(stringBuilder.toString());
        writer.close();
    }

    @Test
    public void testSwerveDrive() throws Exception {
        File file = new File("/Users/braydenjonasrussell/IdeaProjects/UnitTestingFile.txt");
        FileWriter writer = new FileWriter(file);
        SwerveChassis swerveChassis = new SwerveChassis(
                null, null, Map.of(
                new MockMotorWrapper("frontLeftDriving"), new MockMotorWrapper("frontLeftRotating"),
                new MockMotorWrapper("frontRightDriving"), new MockMotorWrapper("frontRightRotating"),
                new MockMotorWrapper("backLeftDriving"), new MockMotorWrapper("backLeftRotating"),
                new MockMotorWrapper("backRightDriving"), new MockMotorWrapper("backRightRotating")
        ), new MockGamepadExWrapper()
        );
        long nanoTime = System.nanoTime();
        while(((System.nanoTime() - nanoTime) / 1e9) <= 6) {
            swerveChassis.function();
        }
        swerveChassis.shutdown();
        StringBuilder stringBuilder = new StringBuilder();
        for(SwerveChassis.SwerveModule swerveModule : swerveChassis.getSwerveModules()) {
            stringBuilder.append("SwerveModule turning motor position: " +
                            swerveModule.turningMotorWrapper.getPidControlledMotor().getCurrentPosition() + "\n");
        }
        writer.write(stringBuilder.toString());
        writer.close();
    }
}