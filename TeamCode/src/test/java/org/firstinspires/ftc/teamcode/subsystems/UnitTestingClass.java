package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.mocktesting.MockGamepadExWrapper;
import org.firstinspires.ftc.teamcode.mocktesting.MockIMU;
import org.firstinspires.ftc.teamcode.mocktesting.MockMotorWrapper;
import org.firstinspires.ftc.teamcode.pid.PIDControl;
import org.firstinspires.ftc.teamcode.pid.PIDIMUWrapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

public class UnitTestingClass {

    @Test
    public void testSwerveDriveWithIMUPID() throws Exception {
        PIDIMUWrapper wrapper = new PIDIMUWrapper(new MockIMU("imu"));
        SwerveChassis swerveChassis = new SwerveChassis(
              null, null, Map.of(
                      new MockMotorWrapper(SubsystemConstants.FRONT_LEFT_SWERVE_DRIVING_MOTOR_ID),
                new MockMotorWrapper(SubsystemConstants.FRONT_LEFT_SWERVE_TURNING_MOTOR_ID),
                new MockMotorWrapper(SubsystemConstants.FRONT_RIGHT_SWERVE_DRIVING_MOTOR_ID),
                new MockMotorWrapper(SubsystemConstants.FRONT_RIGHT_SWERVE_TURNING_MOTOR_ID),
                new MockMotorWrapper(SubsystemConstants.BACK_LEFT_SWERVE_DRIVING_MOTOR_ID),
                new MockMotorWrapper(SubsystemConstants.BACK_LEFT_SWERVE_TURNING_MOTOR_ID),
                new MockMotorWrapper(SubsystemConstants.BACK_RIGHT_SWERVE_DRIVING_MOTOR_ID),
                new MockMotorWrapper(SubsystemConstants.BACK_RIGHT_SWERVE_TURNING_MOTOR_ID)
        ), new MockGamepadExWrapper(), wrapper
        );

        PIDControl control = swerveChassis.getPIDControlForTurningMotors(),
        control2 = swerveChassis.getPIDControlForDrivingMotors();

        swerveChassis.setRotate(true);
        control.setPIDForIMU(false);
        control2.setPIDForIMU(false);

        long time = System.nanoTime();
        while(!Thread.currentThread().isInterrupted() && (System.nanoTime() - time) < 3e9) {
            swerveChassis.function();
            try {
                Thread.sleep(10);
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Ran the first loop. Going to the second one.");

        control2.setIMUTargetPosition(245.0, wrapper);
        control2.setPIDForIMU(true);
        long time2 = System.nanoTime();
        while(!Thread.currentThread().isInterrupted() && (System.nanoTime() - time2) < 10e9) {
            try {
                Thread.sleep(10);
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }

        File file = new File("UnitTestingFile.txt");
        FileWriter writer = new FileWriter(file);
        StringBuilder builder = new StringBuilder();

        for(SwerveChassis.SwerveModule module : swerveChassis.getSwerveModules()) {
            MotorWrapper drivingMotor = module.drivingMotor,
            turningMotor = module.turningMotorWrapper.getPidControlledMotor();

            builder.append(drivingMotor.getMotorName()).append(" distance: ").append(drivingMotor.getDistance()).append("\n");
            builder.append(turningMotor.getMotorName()).append(" current position: ").append(turningMotor.getCurrentPosition()).append("\n");
        }
        writer.write(builder.toString());
        writer.close();
    }
}