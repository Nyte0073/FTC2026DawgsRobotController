package org.firstinspires.ftc.teamcode.subsystems.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AndroidStudioServer {

    public static ServerSocket serverSocket;
    public static Socket acceptedSocket;

    static {
        try {
            serverSocket = new ServerSocket(8000);
            acceptedSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean socketConnected() {
        return acceptedSocket != null;
    }
}
