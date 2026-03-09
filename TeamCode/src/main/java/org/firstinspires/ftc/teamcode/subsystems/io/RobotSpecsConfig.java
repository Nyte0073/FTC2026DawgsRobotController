package org.firstinspires.ftc.teamcode.subsystems.io;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public final class RobotSpecsConfig<E extends Serializable> {

    private final List<E> params;

    public RobotSpecsConfig(List<E> params) {
        this.params = params;
    }

    public void sendSpecs() throws Exception {
        if(!AndroidStudioServer.socketConnected()) {
            return;
        }
        ObjectOutputStream outputStream = new ObjectOutputStream(AndroidStudioServer.acceptedSocket.getOutputStream());
        for (E e : params) {
            outputStream.writeObject(e);
        }
    }
}
