package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Peashooter extends Shooter {

    public Peashooter(int x, int y) {
        super(x, y);
        this.setWaitingTime(6);
        this.setHealth(4);
        this.setDayPlanet(true);
        this.setImage(new ImageView(new Image(getClass().getResource("/peashooter.gif").toExternalForm())));
        this.setEatimage(new ImageView(new Image(getClass().getResource("/peashooter.gif").toExternalForm())));
    }

    @Override
    void shoot(Pane root, double x, double xzombie, double y) {
        double speed = ( ( xzombie - x ) / Yard.Cell_HEIGHT ) * 0.5;
        Bullet repeater1 = new Bullet(getCol(), getRow(), speed, "NORMAL");
        repeater1.shoot(root, x + 60, xzombie, y);
        bullets.add(repeater1);
    }

    @Override
    String gettype() {
        return "Peashooter";
    }
}

