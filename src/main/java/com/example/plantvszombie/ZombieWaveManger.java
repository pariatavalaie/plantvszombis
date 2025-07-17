package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ZombieWaveManger {
     private static Timeline maintimeline;
    private Yard yard;
    private static int gameTime;
    private boolean win = false;
    private boolean lose = false;
    ZombieWaveManger(Yard yard) {
        this.setYard(yard);
        setGameTime(0);
        setupTimeline();
    }

    public static Timeline getMaintimeline() {
        return maintimeline;
    }

    public static void setMaintimeline(Timeline maintimeline) {
        ZombieWaveManger.maintimeline = maintimeline;
    }

    public static int getGameTime() {
        return gameTime;
    }

    public static void setGameTime(int gameTime) {
        ZombieWaveManger.gameTime = gameTime;
    }

    private void setupTimeline() {
        setMaintimeline(new Timeline(new KeyFrame(Duration.seconds(1), e -> tick())));
        getMaintimeline().setOnFinished(event -> {
            if(!isWin()){
            setLose(true);}
        });
        getMaintimeline().setCycleCount(240);
    }

    public void start() {
       //yard.fog.enterSlowly();
        getMaintimeline().play();
        AnimationManager.register(getMaintimeline());
    }

    private void tick() {
        setGameTime(getGameTime() + 1);

        if (getGameTime() >=5 && getGameTime() <= 40) waveStage1();
        else if ( getGameTime() >=42 && getGameTime() <= 120 ) waveStage2();
        else if (getGameTime() >= 120 && getGameTime() <= 180) waveStage3();
        // else if (gameTime <= 240) waveStage4();

        // حملات ویژه
        if (getGameTime() >= 50 && getGameTime() <= 70) halfAttack();
        //if (gameTime >= 47 && gameTime <= 60) finalAttack(); // حمله پایانی
       getYard().getFog().bringFogToFront(getYard().getYardPane());
        for (Zombies z : getYard().getZombies()) {
            if (z.isInHouse()) {
                setLose(true);
                GameServer.notifyGameOver(true);
                getMaintimeline().stop();
                System.out.println("Zombie entered the house! You lose.");
                Platform.runLater(() -> getYard().triggerGameEnd(false));
                break;
            }
        }
        if (getGameTime() >= 240 && !isLose()) {
            if(!getYard().isServer()){
            setWin(true);
            getMaintimeline().stop();
            System.out.println("Time's up! You win.");
            Platform.runLater(() -> getYard().triggerGameEnd(true));}
            else {
                GameServer.broadcast(new NetworkMessage("REQUEST_KILLS", null));
            }
        }
    }

    private void waveStage1() {
        if (getGameTime() % 7 == 0) { //5 zombie
            spawnZombie("Normal", 1);
        }
    }

    private void waveStage2() {
        if (getGameTime() % 20 == 0) { //6 zombie
            int x = new Random().nextInt(2);
            switch (x) {
                case 0:
                spawnZombie("Normal", 1);
                break;
                case 1:
                spawnZombie("Conehead", 1);
                break;
            }
        }
    }

    private void waveStage3() {
        if (getGameTime() % 10 == 0) { //18 zombie
            spawnZombie("Normal", 1);
            spawnZombie("Conehead", 1);
            spawnZombie("Screendor", 1);
        }
    }

    private void waveStage4() {
        if (getGameTime() % 12 == 0) {
            spawnZombie("Normal", 2);
            spawnZombie("Conehead", 2);
            spawnZombie("Screendor", 2);
            spawnZombie("Imp", 2);
        }
    }

    private void halfAttack() {
        if (getGameTime() % 10 == 0) {
            for (int i = 0; i < 2; i++) {
                spawnZombie("Normal", 1);
                spawnZombie("Conehead", 1);
            }
        }
    }

    private void finalAttack() {
        if (getGameTime() % 1 == 0) {
            for (int i = 0; i < 15; i++) {
                spawnZombie("Normal", 1);
                spawnZombie("Conehead", 1);
                spawnZombie("Imp", 1);
            }
        }
    }

    private void spawnZombie(String type, int count) {
        Random rand = new Random();
        Set<Integer> usedRows = new HashSet<>();

        for (int i = 0; i < count; i++) {
            int row;
            int attempts = 0;
            do {
                row = rand.nextInt(5); // ردیف 0 تا 4
                attempts++;
            } while (usedRows.contains(row) && attempts < 10);

            if (usedRows.contains(row)) break;

            usedRows.add(row);

            Zombies z = null;

            switch (type) {
                case "Normal":
                    z = new NormalZombie(9, row, getYard().getYardPane());
                    break;
                case "Conehead":
                    z = new ConeheadZombie(9, row, getYard().getYardPane());
                    break;
                case "Screendor":
                    z = new ScreendoorZombie(9, row, getYard().getYardPane());
                    break;
                case "Imp":
                    z = new ImpZombie(9, row, getYard().getYardPane());
                    break;
            }

            if (z != null) {
                getYard().getZombies().add(z);
                GameServer.notifyZombieSpawn(z.getState());
            }
        }
    }

    public Yard getYard() {
        return yard;
    }

    public void setYard(Yard yard) {
        this.yard = yard;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isLose() {
        return lose;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }
}