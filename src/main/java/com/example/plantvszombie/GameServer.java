package com.example.plantvszombie;

import java.io.*;
import java.net.*;
import java.util.*;

public class GameServer {
    public static final int PORT = 54321;
    public static Yard yard; // فقط در سرور
    public static List<ObjectOutputStream> clients = new ArrayList<>();

    public static void start(Yard hostYard) {
        yard = hostYard;

        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Server started on port " + PORT);
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client connected: " + socket.getInetAddress());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    clients.add(out);
                    new Thread((Runnable) new RequestHandler (out)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void broadcast(GameState state) {
        for (ObjectOutputStream out : clients) {
            try {
                out.reset(); // جلوگیری از کش serialization
                out.writeObject(state);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
