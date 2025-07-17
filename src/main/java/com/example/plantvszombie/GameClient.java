package com.example.plantvszombie;

import java.io.*;
import java.net.*;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

public class GameClient {
    private ObjectOutputStream out;
     private Yard clientYard;

    public GameClient(String host, int port) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(host, port);

        setOut(new ObjectOutputStream(socket.getOutputStream()));
        getOut().flush();
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

        this.setClientYard(new Yard(selectedPlants, day));
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
                    Zombies z = ZombieFactory.createFromState(zs, getClientYard().getYardPane());
                    getClientYard().getZombies().add(z);
                }

                case "SPAWN_SUN" -> {
                    SunState ss = (SunState) msg.data;
                     Sun S=new Sun();
                     Sun.getSuns().add(S);
                     // متد انیمیشن خورشید
                    S.fallingSun(getClientYard().getYardPane(), ss.getX(),0);
                }
                case "INITIAL_ZOMBIES" -> {
                    List<ZombieState> zsList = (List<ZombieState>) msg.data;
                    for (ZombieState zs : zsList) {
                            Zombies z = ZombieFactory.createFromState(zs, getClientYard().getYardPane());
                            getClientYard().getZombies().add(z);

                    }

                }
                case ("INITIAL_SUNS") ->{
                    List<SunState> sunsList = (List<SunState>) msg.data;
                    for (SunState sun : sunsList) {
                        Sun s=new Sun();
                        Sun.getSuns().add(s);
                        s.fallingSun(getClientYard().getYardPane(), sun.getX(),sun.getZ());
                    }

                } case("GAME_OVER") ->{
                    boolean gameOver = (Boolean) msg.data;
                    getClientYard().triggerGameEnd(gameOver);
                }
                case "REQUEST_KILLS" -> {
                    int clientKills = getClientYard().getKilledZombies();
                    sendMessage(new NetworkMessage("MY_KILLS", clientKills));
                }case "FOG"->{
                    getClientYard().getFog().enterSlowly();
                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0.5), event -> {
                        getClientYard().getFog().bringFogToFront(getClientYard().getYardPane());
                    }));
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    timeline.play();
                    AnimationManager.register(timeline);
                }
            }
        });
    }
    private void startClientLoseChecker() {
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(500);
                    for (Zombies z : getClientYard().getZombies()) {
                        if (z.isInHouse()) {
                            System.out.println("Client lost!");
                            sendMessage(new NetworkMessage("GAME_OVER",true));
                            Platform.runLater(() -> getClientYard().triggerGameEnd(false));
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
            getOut().writeObject(msg);
            getOut().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public Yard getClientYard() {
        return clientYard;
    }

    public void setClientYard(Yard clientYard) {
        this.clientYard = clientYard;
    }
}
