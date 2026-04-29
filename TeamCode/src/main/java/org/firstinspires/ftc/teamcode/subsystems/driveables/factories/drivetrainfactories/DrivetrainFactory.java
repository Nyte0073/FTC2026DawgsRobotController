package org.firstinspires.ftc.teamcode.subsystems.driveables.factories.drivetrainfactories;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.driveables.Driveable;
import org.firstinspires.ftc.teamcode.subsystems.driveables.externalhardware.externalhardwareactions.HardwareAction;

import java.util.Map;

/**Blueprint to be used to create all hardware and command ware according to the use of specific drivetrains wanting to be used.*/
public interface DrivetrainFactory {

    /**Method to call to construct a new drivetrain of your choosing depending on what you cast the returned {@link Driveable} object
     * to. This is method is hassle free because all it needs is just a simple {@link HardwareMap} object to get it started and it will handle
     * all the other initialization internally so that nothing gets messed up. All the constant values that it pulls are from the
     * {@link org.firstinspires.ftc.teamcode.subsystems.driveables.factories.FactoryConstants} file so that it can initialize everything inside itself
     * and nothing has to be created externally in the actual teleop/autonomous classes.
     * */
    Driveable createDrivetrain(HardwareMap hardwareMap);

    /**Method to call to construct a new command you can use to control your robot's drivetrain and/or external hardware systems. This method takes in
     * just three parameters, all of which only need to be used if you are creating a regular {@link CommandBase} object, otherwise it is really only
     * the driveable and boolean state that will be used to create the command.*/
    CommandBase createDrivetrainCommand(Driveable driveable, GamepadEx gamepadEx, boolean vectorControlled);

    /**Binds actions to specific hardware on the robot, allowing for specific control of the robot through certain button presses on the
     * {@link GamepadEx} object.*/
    void addGameActions(Map<GamepadKeys.Button, HardwareAction> buttonToRunnable);

    /**@return The actions bound to specific buttons on a {@link GamepadEx} object execute specific tasks with certain button presses.*/
    Map<GamepadKeys.Button, HardwareAction> getGameActions();
}
