package com.example.plantvszombie;

import java.io.*;
import java.net.*;
import java.util.List;

import javafx.application.Platform;

public class GameClient {
    private ObjectOutputStream out;
    private Yard clientYard; // یارد اختصاصی کلاینت

    public GameClient(String host, int port, Yard yard) throws IOException {
        this.clientYard = yard;

        Socket socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        // دریافت پیام‌ها از سرور
        new Thread(() -> {
            try {
                while (true) {
                    Object obj = in.readObject();
                    if (obj instanceof NetworkMessage msg) {
                        handleMessage(msg); // پیام دریافتی را مدیریت کن
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


                // می‌تونی پیام‌های دیگه مثل حذف زامبی یا خورشید رو هم اضافه کنی
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
