package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Random;

public class ZombieWaveManger {
    Timeline maintimeline;
    Yard yard;
    int gameTime;

    ZombieWaveManger(Yard yard) {
        this.yard = yard;
        gameTime = 0;
        setupTimeline();
    }

    private void setupTimeline() {
        maintimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> tick()));
        maintimeline.setCycleCount(480); // بازی 60 ثانیه است
    }

    public void start() {
        maintimeline.play();
    }

    private void tick() {
        gameTime++;
        yard.startMovingAndDetecting();



        if (gameTime <= 120) waveStage1();
        else if (gameTime <= 240) waveStage2();
        // else if (gameTime <= 65) waveStage3();
        // else if (gameTime <= 100) waveStage4();

        // حملات ویژه
        //if (gameTime >= 26 && gameTime <= 33) halfAttack();
        //if (gameTime >= 47 && gameTime <= 60) finalAttack();
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
        if (gameTime % 2 == 0) {
            spawnZombie("Normal", 1);
            spawnZombie("Conehead", 1);
            spawnZombie("Screendor", 1);
        }
    }

    private void waveStage4() {
        if (gameTime % 2 == 0) {
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
                    yard.Zombies.add(new NormalZombie(9, row, yard.yardPane));
                    break;
                case "Conehead":
                    yard.Zombies.add(new ConeheadZombie(9, row, yard.yardPane));
                    break;
                case "Screendor":
                    yard.Zombies.add(new ScreendoorZombie(9, row, yard.yardPane));
                    break;
                case "Imp":
                    yard.Zombies.add(new ImpZombie(9, row, yard.yardPane));
                    break;

            }
        }
    }


}