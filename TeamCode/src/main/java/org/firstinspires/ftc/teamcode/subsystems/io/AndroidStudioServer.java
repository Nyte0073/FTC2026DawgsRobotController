package org.firstinspires.ftc.teamcode.subsystems.io;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AndroidStudioServer {
    private final Gson gson = new Gson();
    private final Supplier<RobotSpecs> robotSpecsSupplier;
    private final List<Thread> threads = new ArrayList<>();

    @SuppressWarnings("all")
    public AndroidStudioServer(Supplier<RobotSpecs> robotSpecsSupplier) {
        this.robotSpecsSupplier = robotSpecsSupplier;
    }

    @SuppressWarnings("all")
    public void launchSendingServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            Socket acceptedSocket = serverSocket.accept();
            BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(acceptedSocket.getOutputStream(), StandardCharsets.UTF_8));
            outputStream.flush();
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(acceptedSocket.getInputStream(), StandardCharsets.UTF_8));

            threads.add(Thread.currentThread());
            new Thread(() -> launchReceivingServer(inputStream)).start();

            while (!Thread.currentThread().isInterrupted()) {
                RobotSpecs specs = robotSpecsSupplier.get();
                String g = gson.toJson(specs);
                outputStream.write(g);
                outputStream.newLine();
                outputStream.flush();
                Thread.sleep(50);
            }
            serverSocket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("all")
    public void launchReceivingServer(BufferedReader inputStream) {
        threads.add(Thread.currentThread());
        while(!Thread.currentThread().isInterrupted()) {
            try {
                String message = inputStream.readLine();
                Log.i(getClass().getSimpleName(), "AndroidStudioServer.launchReceivingServer() has recieved message: " +
                        message);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("all")
    public void interruptAllThreads() {
        for(Thread t : threads) {
            t.interrupt();
        }
    }
}

