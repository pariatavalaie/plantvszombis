package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class SnowPea extends Planet{
    public SnowPea(int x , int y) {
        this.row = y;
        this.col = x;
        this.cost = 175;
        this.watingtime = 8;
        this.image = new Image(getClass().getResource("/SnowPea.gif").toExternalForm());
    }

    @Override
    void act(Pane root) {
        double gridX = 245.0; // Left anchor of grid
        double gridY = 60.0;  // Top anchor of grid

        double cellWidth = 80.0;
        double cellHeight = 100.0;

        double x = gridX + col * 80 + (80 - 70) / 2;
        double y = gridY + row * 100 + (100 - 90) / 2;
        //Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Bullet repeater1 = new Bullet(row, col, 3);

            repeater1.shoot(root, x + 60, 800, "ICY", y);

        //}));
        //timeline.setCycleCount(Timeline.INDEFINITE);
        //timeline.play();


    }
}

