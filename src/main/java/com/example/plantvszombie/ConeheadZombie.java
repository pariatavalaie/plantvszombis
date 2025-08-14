package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ConeheadZombie extends Zombies {

    public ConeheadZombie(int x, int y, Pane root) {
        this.setX(x);
        this.setY(y);
        this.setHp(7);
        this.setSpeed(4);
        this.setImage(new ImageView(new Image(getClass().getResource("/Conehead_Zombie.gif").toExternalForm())));
        getImage().setFitHeight(80);
        getImage().setFitWidth(100);
        this.setDeadZombie(new ImageView(new Image(getClass().getResource("/burntZombie.gif").toExternalForm())));
        getDeadZombie().setFitHeight(80);
        getDeadZombie().setFitWidth(100);
        double startX = 245 + x * 80 + 5;
        double startY = 60 + y * 100 + 10;
        getImage().setLayoutX(startX);
        getImage().setLayoutY(startY);
        root.getChildren().add(getImage());
        move();
    }
}
