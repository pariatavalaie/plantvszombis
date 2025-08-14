package com.example.plantvszombie;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Bullet {
    public int x;
    public int y;
    public double speed;
    public double xpalnet;
    public double ypalnet;
    public double xzombie;
    public ImageView imageBullet;
    public boolean hit;
    public String type;

    public Bullet(int x, int y, double speed, String type) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        hit = false;
        this.type = type;
    }

    public void shoot(Pane pane, double xPlant, double xZombie, double yPlant) {
        Image bullet = null;
        if (type.equals("ICY")) {
            bullet = new Image(getClass().getResource("/snow bullet.png").toExternalForm());
        } else if (type.equals("NORMAL")) {
            bullet = new Image(getClass().getResource("/pea.png").toExternalForm());
        } else if (type.equals("MUSHROOM")) {
            bullet = new Image(getClass().getResource("/bullet_11zon.png").toExternalForm());
        } else if (type.equals("PUFF")) {
            bullet = new Image(getClass().getResource("/mushroom.bullet_11zon.png").toExternalForm());
        }
        imageBullet = new ImageView(bullet);
        imageBullet.setFitHeight(25);
        imageBullet.setFitWidth(25);
        pane.getChildren().add(imageBullet);
        this.xpalnet = xPlant;
        this.ypalnet = yPlant;
        this.xzombie = xZombie;
        TranslateTransition move = new TranslateTransition(Duration.seconds(speed), imageBullet);
        move.setFromX(xPlant);
        move.setToX(xZombie);
        move.setFromY(yPlant);
        move.setToY(yPlant);
        move.play();
        move.setOnFinished(e -> {
            if (!hit) {
                pane.getChildren().remove(imageBullet);
            }
        });
        AnimationManager.register(move);
    }
}
