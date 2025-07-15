package com.example.plantvszombie;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final ObjectInputStream in;
    private final Socket socket;

    public ClientHandler(Socket socket, ObjectInputStream in) {
        this.socket = socket;
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object obj = in.readObject();
                if (obj instanceof NetworkMessage msg) {
                    handleMessage(msg);
                }
            }
        } catch (Exception e) {
            System.out.println("Client disconnected: " + socket.getInetAddress());
        }
    }

    private void handleMessage(NetworkMessage msg) {
        switch (msg.type) {
            case "GAME_OVER" -> {
                System.out.println("Client lost! Server wins.");
                boolean gameOver=(Boolean)msg.data;
                GameServer.yard.triggerGameEnd(gameOver);
            }

            case "MY_KILLS" -> {
                int clientKills = (int) msg.data;
                int serverKills = GameServer.yard.killedZombies;

                System.out.println("Client kills: " + clientKills + ", Server kills: " + serverKills);

                if (serverKills > clientKills) {
                    GameServer.notifyGameOver(false);
                    GameServer.yard.triggerGameEnd(true);
                } else if (clientKills > serverKills) {
                    GameServer.notifyGameOver(true);
                    GameServer.yard.triggerGameEnd(true);
                } else {
                    GameServer.broadcast(new NetworkMessage("GAME_OVER", false));
                    javafx.application.Platform.runLater(() -> GameServer.yard.triggerGameEnd(false));
                }
            }

        }
    }
}
