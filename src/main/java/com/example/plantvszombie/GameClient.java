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

        // 🔹 دریافت initial و initialday قبل از ساخت یارد
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

        // 🔹 ساخت یارد بعد از دریافت اطلاعات
        this.clientYard = new Yard(selectedPlants, day);
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

                }
            }
        });
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
