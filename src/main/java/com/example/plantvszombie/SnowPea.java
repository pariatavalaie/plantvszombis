package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class SnowPea extends Shooter{
    public SnowPea(int x , int y) {
       super(x,y);
        this.setHealth(4);
        this.setWatingtime(8);
        this.setDayPlanet(true);
        this.setImage(new ImageView( new Image(getClass().getResource("/SnowPea.gif").toExternalForm())));
        this.setEatimage(new ImageView( new Image(getClass().getResource("/SnowPea.gif").toExternalForm())));
    }

    @Override
    void shoot(Pane root, double x, double xzombie, double y) {
        Bullet repeater1 = new Bullet(getCol(),getRow(), 3, "ICY");
        repeater1.shoot(root, x + 60,xzombie, y);
        bullets.add(repeater1);
    }

    @Override
    String gettype() {
        return "Snow Pea";
    }
}

