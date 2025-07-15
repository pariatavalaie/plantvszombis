package com.example.plantvszombie;

import java.io.*;
import java.net.*;
import java.util.List;

import javafx.application.Platform;

public class GameClient {
    private ObjectOutputStream out;
     Yard clientYard;

    public GameClient(String host, int port) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(host, port);

        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        List<String> selectedPlants = null;
        boolean day = true;

        for (int i = 0; i < 2; i++) {
            NetworkMessage msg = (NetworkMessage) in.readObject();
            switch (msg.type) {
                case "initial":
                    selectedPlants = (List<String>) msg.data;
                    break;
                case "initialday":
                    day = (Boolean) msg.data;
                    break;
            }
        }

        this.clientYard = new Yard(selectedPlants, day);
        startClientLoseChecker();
        new Thread(() -> {
            try {
                while (true) {
                    Object obj = in.readObject();
                    if (obj instanceof NetworkMessage msg) {
                        handleMessage(msg);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleMessage(NetworkMessage msg) {
        Platform.runLater(() -> {
            switch (msg.type) {
                case "SPAWN_ZOMBIE" -> {
                    ZombieState zs = (ZombieState) msg.data;
                    Zombies z = ZombieFactory.createFromState(zs, clientYard.yardPane);
                    clientYard.Zombies.add(z);
                }

                case "SPAWN_SUN" -> {
                    SunState ss = (SunState) msg.data;
                     Sun S=new Sun();
                     Sun.suns.add(S);
                     // متد انیمیشن خورشید
                    S.fallingSun(clientYard.yardPane, ss.getX(),0);
                }
                case "INITIAL_ZOMBIES" -> {
                    List<ZombieState> zsList = (List<ZombieState>) msg.data;
                    for (ZombieState zs : zsList) {
                            Zombies z = ZombieFactory.createFromState(zs, clientYard.yardPane);
                            clientYard.Zombies.add(z);

                    }

                }
                case ("INITIAL_SUNS") ->{
                    List<SunState> sunsList = (List<SunState>) msg.data;
                    for (SunState sun : sunsList) {
                        Sun s=new Sun();
                        Sun.suns.add(s);
                        s.fallingSun(clientYard.yardPane, sun.getX(),sun.getZ());
                    }

                } case("GAME_OVER") ->{
                    boolean gameOver = (Boolean) msg.data;
                    clientYard.triggerGameEnd(gameOver);
                }
                case "REQUEST_KILLS" -> {
                    int clientKills = clientYard.killedZombies;
                    sendMessage(new NetworkMessage("MY_KILLS", clientKills));
                }
            }
        });
    }
    private void startClientLoseChecker() {
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(500);
                    for (Zombies z : clientYard.Zombies) {
                        if (z.inHouse) {
                            System.out.println("Client lost!");
                            sendMessage(new NetworkMessage("GAME_OVER",true));
                            Platform.runLater(() -> clientYard.triggerGameEnd(false));
                            return;
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void sendMessage(NetworkMessage msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
