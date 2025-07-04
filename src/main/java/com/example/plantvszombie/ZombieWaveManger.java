package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Random;

public class ZombieWaveManger {
    Timeline maintimeline;
    Yard yard;
    static int gameTime;
    boolean win = false;
    boolean lose = false;
    ZombieWaveManger(Yard yard) {
        this.yard = yard;
        gameTime = 0;
        setupTimeline();
    }

    private void setupTimeline() {
        maintimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> tick()));
        maintimeline.setOnFinished(event -> {
            if(!win){
            lose = true;}
        });
        maintimeline.setCycleCount(480);
    }

    public void start() {
       yard.fog.enterSlowly();
        maintimeline.play();
        AnimationManager.register(maintimeline);
    }

    private void tick() {
        gameTime++;


        // اجرای امواج مختلف
        if (gameTime <= 120) waveStage1();
        //else if (gameTime <= 240) waveStage2();
        // else if (gameTime <= 65) waveStage3();
        // else if (gameTime <= 100) waveStage4();

        // حملات ویژه
        //if (gameTime >= 26 && gameTime <= 33) halfAttack(); // حمله نیمه بازی
        //if (gameTime >= 47 && gameTime <= 60) finalAttack(); // حمله پایانی
       yard.fog.bringFogToFront(yard.yardPane);

    }

    private void waveStage1() {
        if (gameTime % 6 == 0) { //20 zombie
            spawnZombie("Normal", 1);
        }
    }

    private void waveStage2() {
        if (gameTime % 4 == 0) { //30 zombie
            spawnZombie("Normal", 1);
            spawnZombie("Conehead", 1);
        }
    }

    private void waveStage3() {
        if (gameTime % 12 == 0) { //10 zombie
            spawnZombie("Normal", 1);
            spawnZombie("Conehead", 1);
            spawnZombie("Screendor", 1);
        }
    }

    private void waveStage4() {
        if (gameTime % 12 == 0) { //10 zombie
            spawnZombie("Normal", 2);
            spawnZombie("Conehead", 2);
            spawnZombie("Screendor", 2);
            spawnZombie("Imp", 2); // بچه زامبی
        }
    }

    private void halfAttack() {
        if (gameTime % 1 == 0) {
            for (int i = 0; i < 10; i++) {
                spawnZombie("Normal", 1);
                spawnZombie("Conehead", 1);
            }
        }
    }

    private void finalAttack() {
        if (gameTime % 1 == 0) {
            for (int i = 0; i < 15; i++) {
                spawnZombie("Normal", 1);
                spawnZombie("Conehead", 1);
                spawnZombie("Imp", 1);
            }
        }
    }

    private void spawnZombie(String type, int count) {
        for (int i = 0; i < count; i++) {
            int row = new Random().nextInt(5); // پنج ردیف
            switch (type) {
                case "Normal":
                    Zombies z=new NormalZombie(9, row, yard.yardPane);
                    yard.Zombies.add(z);
                    GameServer.notifyZombieSpawn(z.getState());
                    break;
                case "Conehead":
                    Zombies q=new ConeheadZombie(9, row, yard.yardPane);
                    yard.Zombies.add(q);
                    GameServer.notifyZombieSpawn(q.getState());
                    break;
                case "Screendor":
                    Zombies c=new ScreendoorZombie(9, row, yard.yardPane);
                    yard.Zombies.add(c);
                    GameServer.notifyZombieSpawn(c.getState());
                    break;
                case "Imp":
                    Zombies d=new ImpZombie(9, row, yard.yardPane);
                    yard.Zombies.add(new ImpZombie(9, row, yard.yardPane));
                    GameServer.notifyZombieSpawn(d.getState());
                    break;

            }
        }
    }
    private void endgame(){
        for (Zombies zombie: yard.Zombies) {
            if(zombie.inHouse&&!win){
            lose = true;}
        }

    }



}