package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.geometry.Vector2d;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


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
}