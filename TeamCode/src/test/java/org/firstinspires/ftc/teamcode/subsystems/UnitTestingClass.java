package org.firstinspires.ftc.teamcode.subsystems;


import org.firstinspires.ftc.teamcode.math.AngularOperations;
import org.firstinspires.ftc.teamcode.math.VectorOperations;
import org.firstinspires.ftc.teamcode.mocktesting.MockMotorExWrapper;
import org.junit.jupiter.api.Test;

public class UnitTestingClass {

    @Test
    public void testSomething() throws Exception {
        VectorOperations.Vector unitVector = VectorOperations.getUnitBaseVector(60, VectorOperations.DimensionType.R2);
        System.out.println(AngularOperations.angleInRelationToXAxis(unitVector));
        VectorOperations.Vector rotatedVector = unitVector.rotateAroundZAxis(100);
        System.out.println(AngularOperations.angleInRelationToXAxis(rotatedVector));
        Thread.sleep(8000);
    }

    @Test
    public void testSwerveChassisComponentWithOneModule() throws Exception {
        GamepadExWrapper gamepadExWrapper = new GamepadExWrapper();
        gamepadExWrapper.setLeftY(-1);
        gamepadExWrapper.setLeftX(0);

        SwerveChassisComponent.SwerveModule[] modules = new SwerveChassisComponent.SwerveModule[30];
        ChassisComponentConstraints.Builder builder = new ChassisComponentConstraints.Builder()
                .setGamepadExWrapper(gamepadExWrapper);

        for (int i = 0; i < 30; i++) {
            MockMotorExWrapper turning = new MockMotorExWrapper("turningMotor_" + i);
            MockMotorExWrapper driving = new MockMotorExWrapper("drivingMotor_" + i);
            modules[i] = new SwerveChassisComponent.SwerveModule(turning, driving);
            builder.addTurningMotor(turning).addDrivingMotor(driving);
        }

        ChassisComponent chassis = new SwerveChassisComponent(modules);
        chassis.setChassisComponentConstraints(builder.build());

        chassis.function();
        chassis.function();
        Thread.sleep(100000000);
    }

}