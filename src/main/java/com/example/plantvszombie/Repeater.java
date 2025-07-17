package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.ArrayList;

public class Repeater extends Shooter{
    public Repeater(int x, int y) {
        super(x,y);
        this.dayPlanet =true;
        this.watingtime = 7;
        this.health=4;
        this.image =new ImageView( new Image(getClass().getResource("/repeater.gif").toExternalForm()));
        this.eatimage =new ImageView( new Image(getClass().getResource("/repeater.gif").toExternalForm()));
    }

    @Override
    void shoot(Pane root, double x, double xzombie, double y) {
        Bullet repeater1 = new Bullet(getCol(), getRow(), 3, "NORMAL");
        Bullet repeater2 = new Bullet(getCol(),getRow(), 4.5, "NORMAL");
        repeater1.shoot(root, x + 60,xzombie, y);
        repeater2.shoot(root, x + 60,xzombie,  y);
        bullets.add(repeater1);
        bullets.add(repeater2);
    }

    @Override
    String gettype() {
        return "Repeater";
    }
}
