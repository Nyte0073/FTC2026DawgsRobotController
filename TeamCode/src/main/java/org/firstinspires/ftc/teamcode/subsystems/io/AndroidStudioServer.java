package org.firstinspires.ftc.teamcode.subsystems.io;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public class AndroidStudioServer extends Thread {

    private static final BufferedWriter outputStream;
    private static final Gson gson = new Gson();

    static {
        try {
            ServerSocket serverSocket = new ServerSocket(1000);
            Socket acceptedSocket = serverSocket.accept();
            outputStream = new BufferedWriter(new OutputStreamWriter(acceptedSocket.getOutputStream(), StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("all")
    public AndroidStudioServer(Supplier<RobotSpecs> robotSpecsSupplier) {
        super(
                () -> {
                    while(!Thread.currentThread().isInterrupted()) {
                        try {
                            RobotSpecs specs = robotSpecsSupplier.get();
                            String g = gson.toJson(specs);
                            outputStream.write(g);
                            outputStream.newLine();
                            outputStream.flush();
                           Thread.sleep(50);
                        } catch(Exception e) {
                            throw new Error(e);
                        }
                    }
                }
        );
        setName("Well no duh.");
    }
}