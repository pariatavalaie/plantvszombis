package com.example.plantvszombie;

import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class NormalZombie extends Zombies{
    public NormalZombie(int x , int y) {
        this.x = x;
        this.y = y;
        this.hp = 5;
        this.speed = 4;
        this.image = new ImageView(new Image(getClass().getResource("/normalzombie.gif").toExternalForm()));
    }
    @Override
    void act(Pane root){

    }
    @Override
    void move(Pane root) {
        TranslateTransition move = new TranslateTransition(Duration.millis(30), root);
        move.setFromX(x);
        move.setToX(y);

    }
}
