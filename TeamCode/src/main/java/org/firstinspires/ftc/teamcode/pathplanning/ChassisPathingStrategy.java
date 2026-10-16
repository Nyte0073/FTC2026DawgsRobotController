package org.firstinspires.ftc.teamcode.pathplanning;


import org.firstinspires.ftc.teamcode.pid.PIDControl;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;

public interface ChassisPathingStrategy {
   void rotate(double degrees) throws Exception;
   void driveForwardOrBackward(double magnitude) throws Exception;
   Chassis getChassis();
   default PIDControl getPIDControl() {
      return getChassis().getPIDControlForTurningMotors();
   }
}
