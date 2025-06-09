package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Bullet {
    enum type {
        ICY, NORMAL;
    }
    private double x;
    private double y;
    private double speed;
    private type typeBullet;
    private ImageView imageBullet;

    public Bullet(double x, double y, double speed, type type) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.typeBullet = type;
    }

    public void shoot(Pane pane, double xPlanet, double xZombie, String type) {
        Image bullet = null;
        if (type.equals("ICY")) {
            bullet = new Image(getClass().getResource("/snow bullet.png").toExternalForm());
        } else if (type.equals("NORMAL")) {
            bullet = new Image(getClass().getResource("/pea.png").toExternalForm());
        }
        imageBullet = new ImageView(bullet);
        imageBullet.setFitHeight(25);
        imageBullet.setFitWidth(25);
        pane.getChildren().add(imageBullet);
        TranslateTransition move = new TranslateTransition(Duration.seconds(this.speed), imageBullet);
        move.setFromX(xPlanet);
        move.setToX(xZombie);
        move.setFromY(this.y);
        move.setToY(this.y);
        move.setOnFinished(e -> {
            pane.getChildren().remove(imageBullet);
        });
        move.play();
    }
}
