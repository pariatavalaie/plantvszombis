package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Repeater extends Shooter{

    public Repeater(int x, int y) {
        super(x,y);
        this.setDayPlanet(true);
        this.setWaitingTime(7);
        this.setHealth(4);
        this.setImage(new ImageView( new Image(getClass().getResource("/repeater.gif").toExternalForm())));
        this.setEatimage(new ImageView( new Image(getClass().getResource("/repeater.gif").toExternalForm())));
    }

    @Override
    void shoot(Pane root, double x, double xzombie, double y) {
        double speed=((xzombie-x)/Yard.Cell_HEIGHT)*0.5;
        Bullet repeater1 = new Bullet(getCol(), getRow(), speed, "NORMAL");
        Bullet repeater2 = new Bullet(getCol(),getRow(), speed+0.5, "NORMAL");
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
