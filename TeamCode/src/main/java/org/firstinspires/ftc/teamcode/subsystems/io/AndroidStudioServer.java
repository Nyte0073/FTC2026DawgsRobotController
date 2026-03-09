package org.firstinspires.ftc.teamcode.subsystems.io;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class AndroidStudioServer {

    public static ServerSocket serverSocket;
    public static Socket acceptedSocket;
    public static final ObjectOutputStream outputStream;

    static {
        try {
            serverSocket = new ServerSocket(8000);
            acceptedSocket = serverSocket.accept();
            outputStream = new ObjectOutputStream(acceptedSocket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean socketConnected() {
        return acceptedSocket != null;
    }

    public static void sendData(RobotSpecs<? extends Serializable> robotSpecs) throws Exception {
        outputStream.writeObject(robotSpecs);
        outputStream.flush();
    }
}
