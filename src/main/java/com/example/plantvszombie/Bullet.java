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
    public ImageView imageBullet;
    public boolean hit;

    public Bullet(int x, int y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        hit = false;
    }

    public void shoot(Pane pane, double xPlant, double xZombie, String type, double yPlant) {
        Image bullet = null;
        if (type.equals("ICY")) {
            bullet = new Image(getClass().getResource("/snow bullet.png").toExternalForm());
        } else if (type.equals("NORMAL")) {
            bullet = new Image(getClass().getResource("/pea.png").toExternalForm());
        }else if (type.equals("MUSHROOM")) {
            bullet = new Image(getClass().getResource("/bullet_11zon.png").toExternalForm());
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
        move.setOnFinished(e -> {
            if(!hit){
                pane.getChildren().remove(imageBullet);
            }
        });

        AnimationTimer tracker = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double gridX = 245.0; // Left anchor of grid
                double gridY = 60.0;
                double currentX = imageBullet.getLayoutX() + imageBullet.getTranslateX();
                double currentY = imageBullet.getLayoutY() + imageBullet.getTranslateY();
                double cellWidth = 80;
                double cellHeight = 100;

                int x = (int) ((currentX - gridX) / cellWidth);
                int y= (int) ((currentY - gridY) / cellHeight);


                //System.out.println("Bullet Position: X=" + x + ", Y=" + y);

            }
        };
        tracker.start();
    }
}
