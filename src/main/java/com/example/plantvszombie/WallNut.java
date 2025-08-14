package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WallNut extends Planet {

    public WallNut(int x, int y) {
        super(x, y);
        setWaitingTime(6);
        this.setHealth(10);
        this.setDayPlanet(true);
        this.setEatimage(new ImageView(new Image(getClass().getResource("/walnut_half_life.gif").toExternalForm())));
        this.setImage(new ImageView(new Image(getClass().getResource("/walnut_full_life.gif").toExternalForm())));
    }

    @Override
    String gettype() {
        return "Wall-nut";
    }
}
