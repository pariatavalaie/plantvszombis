package com.example.plantvszombie;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class NormalZombie extends Zombies {

    public NormalZombie(int x, int y, Pane root) {
        this.x = x;
        this.y = y;
        this.hp = 5;
        this.speed=4;
        this.image = new ImageView(new Image(getClass().getResource("/normalzombie.gif").toExternalForm()));
        image.setFitHeight(80);
        image.setFitWidth(100);
        double startX = 245 + x * 80 + 5;
        double startY = 60 + y * 100 + 10;

        image.setLayoutX(startX);
        image.setLayoutY(startY);

        root.getChildren().add(image);

        move(root);
    }

    @Override
    void act(Pane root) {

    }
}

