package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ImpZombie extends Zombies{

    public ImpZombie(int x,int y,Pane root) {
        this.setX(x);
        this.setY(y);
        this.setHp(3);
        this.setSpeed(2);
        this.setImage(new ImageView(new Image(getClass().getResource("/Walking_Imp.gif").toExternalForm())));
        getImage().setFitHeight(60);
        getImage().setFitWidth(80);
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
