package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Repeater extends Planet {
    public Repeater(int x, int y) {
        this.row = y;
        this.col = x;
        this.cost = 200;
        this.watingtime = 7;
        this.image =new ImageView( new Image(getClass().getResource("/repeater.gif").toExternalForm()));
    }

    @Override
    void act(Pane root) {
        double gridX = 245.0; // Left anchor of grid
        double gridY = 60.0;  // Top anchor of grid

        double cellWidth = 80.0;
        double cellHeight = 100.0;

        double x = gridX + col * 80 + (80 - 70) / 2;
        double y = gridY + row * 100 + (100 - 90) / 2;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Bullet repeater1 = new Bullet(row, col, 3);
            Bullet repeater2 = new Bullet(row, col, 4);
            repeater1.shoot(root, x + 60, 800, "NORMAL", y);
            repeater2.shoot(root, x + 60, 800, "NORMAL", y);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


    }
}
