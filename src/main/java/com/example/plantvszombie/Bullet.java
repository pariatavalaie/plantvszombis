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
    private int x;
    private int y;
    private double speed;
    private ImageView imageBullet;

    public Bullet(int x, int y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
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
