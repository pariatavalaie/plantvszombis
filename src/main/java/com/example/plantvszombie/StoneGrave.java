package com.example.plantvszombie;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class StoneGrave {
    private int x;
    private int y;
    private ImageView image;
    private boolean zombieSpawned;
    private Pane pane;
    private PauseTransition stop;

    public StoneGrave(int x, int y, Pane root) {
        this.setX(x);
        this.setY(y);
        setZombieSpawned(false);
        setImage(new ImageView( new Image(getClass().getResource("/Grave8-removebg-preview.png").toExternalForm())));
        getImage().setFitHeight(70);
        getImage().setFitWidth(90);
        double startX = 245 + x * 80 + 5;
        double startY = 60 + y * 100 + 10;
        getImage().setLayoutX(startX);
        getImage().setLayoutY(startY);
        this.setPane(root);
        root.getChildren().add(getImage());
    }

    public void spawnZombie(ArrayList<Zombies>z,ArrayList<StoneGrave>graves) {
        Random random = new Random();

      if (isZombieSpawned()) return;
      setStop(new PauseTransition(Duration.seconds(random.nextInt(5)+5)));
        getStop().setOnFinished(event -> {
        setZombieSpawned(true);
        Zombies zombie;
        double chance = random.nextFloat();
            if (chance < 0.6) {
                zombie = new ImpZombie(getX(), getY(), getPane());
                z.add(zombie);
                remove(graves);
            } else if (chance < 0.7) {
                zombie = new ConeheadZombie(getX(), getY(), getPane());
                z.add(zombie);
                remove(graves);
            } else if (chance < 0.8) {
                zombie = new ScreendoorZombie(getX(), getY(), getPane());
                z.add(zombie);
                remove(graves);
            } else if (chance < 0.9) {
                zombie = new NormalZombie(getX(), getY(), getPane());
                z.add(zombie);
                remove(graves);
            }
        });
        getStop().play();
        AnimationManager.register(getStop());
    }

    public stoneGraveState getState() {
        return new stoneGraveState(getX(), getY());
    }

    public void remove(ArrayList<StoneGrave>graves) {
        getPane().getChildren().remove(getImage());
        graves.remove(this);
        getStop().stop();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public boolean isZombieSpawned() {
        return zombieSpawned;
    }

    public void setZombieSpawned(boolean zombieSpawned) {
        this.zombieSpawned = zombieSpawned;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public PauseTransition getStop() {
        return stop;
    }

    public void setStop(PauseTransition stop) {
        this.stop = stop;
    }
}
