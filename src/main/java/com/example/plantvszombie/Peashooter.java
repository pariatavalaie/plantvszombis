package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Peashooter extends Shooter{
    public Peashooter(int x, int y) {
        super(x,y);
        this.watingtime = 6;
        this.health=4;
        this.dayPlanet =true;
        this.image = new ImageView(new Image(getClass().getResource("/peashooter.gif").toExternalForm()));
        this.eatimage = new ImageView(new Image(getClass().getResource("/peashooter.gif").toExternalForm()));
    }

    @Override
    void shoot(Pane root, double x, double xzombie, double y) {
        Bullet repeater1 = new Bullet(getCol(), getRow(), 3,"NORMAL");
        repeater1.shoot(root, x + 60,xzombie,  y);
        bullets.add(repeater1);
    }

    @Override
    String gettype() {
        return "Peashooter";
    }
}

