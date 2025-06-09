package com.example.plantvszombie;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Bullet {
    public int x;
    public int y;
    private double speed;
    private ImageView imageBullet;

    public Bullet(int x, int y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void shoot(Pane pane, double xPlant, double xZombie, String type, double yPlant) {
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
        move.setFromX(xPlant);
        move.setToX(xZombie);
        move.setFromY(yPlant);
        move.setToY(yPlant);
        move.play();

        AnimationTimer tracker = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double gridX = 245.0; // Left anchor of grid
                double gridY = 60.0;
                double currentX = imageBullet.getTranslateX() + xPlant;
                double currentY = imageBullet.getTranslateY() + yPlant;
               x = (int)(currentX - ((80 - 70)/2)  - gridX / 80);
               y = (int)(currentY - ((100-90)/2) - gridY / 100);


                System.out.println("Bullet Position: X=" + x + ", Y=" + y);

            }
        };
        tracker.start();
    }
}
