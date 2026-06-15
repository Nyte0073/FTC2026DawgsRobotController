package org.firstinspires.ftc.teamcode.subsystems.io;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class AndroidStudioServer {

    private final RobotSpecs specs = new RobotSpecs(new RobotSpecs.MotorSpecs(), new RobotSpecs.OrientationSpecs());

    @SuppressWarnings("all")
    public void launch() {
        try(Socket socket = new Socket("192.168.43.134", 6000)) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            writer.flush();
            Gson gson = new Gson();
            String json;

            Log.i(getClass().getSimpleName(), "Launch has initialized properly.");

            while(!Thread.currentThread().isInterrupted()) {
                json = gson.toJson(specs);
                writer.write(json);
                writer.newLine();
                writer.flush();
            }

            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public RobotSpecs getSpecs() {
        return specs;
    }
}