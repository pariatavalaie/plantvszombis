package com.example.plantvszombie;

import java.io.*;
import java.net.*;
import java.util.*;

public class GameServer {
    public static final int PORT = 54321;
    public static Yard yard;
    public static List<ObjectOutputStream> clients = new ArrayList<>();

    public static void start(Yard hostYard, Runnable onClientConnected) {
        yard = hostYard;

        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Server started on port " + PORT);
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client connected: " + socket.getInetAddress());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    out.writeObject(new NetworkMessage("initial", yard.selected));
                    out.flush();
                    out.writeObject(new NetworkMessage("initialday", yard.day));
                    out.flush();
                    clients.add(out);
                    new ClientHandler(socket, in).start();
                    sendInitialState(out);
                    javafx.application.Platform.runLater(onClientConnected);

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
    public static void sendInitialState(ObjectOutputStream out) {
        try {
            List<ZombieState> currentZombies = new ArrayList<>();
            for (Zombies z : yard.Zombies) {
                currentZombies.add(z.getState());
            }
            List<SunState> currentSuns = new ArrayList<>();
            for (Sun s:Sun.suns) {
                if(s.isFalling){
                    currentSuns.add(s.getState());
                }
            }
            NetworkMessage initialStateMsg = new NetworkMessage("INITIAL_ZOMBIES", currentZombies);
            out.writeObject(initialStateMsg);
            out.flush();
            NetworkMessage sunMsg = new NetworkMessage("INITIAL_SUNS", currentSuns);
            out.writeObject(sunMsg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void notifyGameOver(boolean gameOver) {
        broadcast(new NetworkMessage("GAME_OVER", gameOver));
    }



    public static void broadcast(NetworkMessage msg) {
        for (ObjectOutputStream out : clients) {
            try {
                out.reset();
                out.writeObject(msg);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
 class NetworkMessage implements Serializable {
    public String type;
    public Object data;

    public NetworkMessage(String type, Object data) {
        this.type = type;
        this.data = data;
    }
}
