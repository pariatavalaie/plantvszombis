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
    public static void notifyZombieSpawn(ZombieState zs) {
        broadcast(new NetworkMessage("SPAWN_ZOMBIE", zs));
    }

    public static void notifySunSpawn(SunState ss) {
        broadcast(new NetworkMessage("SPAWN_SUN", ss));
    }


    public static void broadcast(NetworkMessage msg) {
        for (ObjectOutputStream out : clients) {
            try {
                out.reset(); // جلوگیری از کش serialization
                out.writeObject(msg);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
 class NetworkMessage implements Serializable {
    public String type;  // مثل "SPAWN_ZOMBIE", "REMOVE_ZOMBIE" و ...
    public Object data;

    public NetworkMessage(String type, Object data) {
        this.type = type;
        this.data = data;
    }
}
