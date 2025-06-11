package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ImpZombie extends Zombies{
    public ImpZombie(int x,int y,Pane root) {
        this.x = x;
        this.y = y;
        this.hp=3;
        this.speed=2;
        this.image = new ImageView(new Image(getClass().getResource("/Walking_Imp.gif").toExternalForm()));
        image.setFitHeight(60);
        image.setFitWidth(80);
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
