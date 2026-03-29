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

/**Class for communicating between Android Studio and IntelliJ. This class acts like a networking bridge to send information about the robot's current
 * state to a class in IntelliJ that can transform the data into UI in a Java Swing class, which itself can be used for monitoring the robot's functions
 * performance.*/
public class AndroidStudioServer {

    /**Tool to use transform and serialize all RobotSpecs-related classes into JSON Strings that can be sent over to a receiver class in
     * IntelliJ IDEA.*/
    private final Gson gson = new Gson();

    /**Reference to the supplier of the information about the robot to send over a network to a connected socket in IntelliJ.*/
    private final Supplier<RobotSpecs> robotSpecsSupplier;

    /**Collection of al the threads used to make the networking system function, in terms of both sending and receiving.*/
    private final List<Thread> threads = new ArrayList<>();

    @SuppressWarnings("all")
    public AndroidStudioServer(Supplier<RobotSpecs> robotSpecsSupplier) {
        this.robotSpecsSupplier = robotSpecsSupplier;
    }

    /**Launches the server that IntelliJ will connect to once the receiver class starts its main method. This method initializes the
     * server socket that is going be used as the network bridge, and then initializes the Reader and Writer objects to write and read to and from
     * IntelliJ. */
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

    /**Launches the part of the server that will receive and log messages sent from IntelliJ to Android Studio, possibly about
     * any success with sending over classes containing information about the robot.*/
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

    /**Stops all threads in the case of an emergency.*/
    @SuppressWarnings("all")
    public void interruptAllThreads() {
        for(Thread t : threads) {
            t.interrupt();
        }
    }
}

