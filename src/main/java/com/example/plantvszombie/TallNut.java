package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TallNut extends Plant {

    public TallNut(int x, int y) {
        super(x, y);
        this.setHealth(16);
        this.setDayPlanet(true);
        setWaitingTime(3);
        this.setEatimage(new ImageView(new Image(getClass().getResource("/Tallnut3.gif").toExternalForm())));
        this.setImage(new ImageView(new Image(getClass().getResource("/TallNut1.gif").toExternalForm())));

    }

    @Override
    String gettype() {
        return "Tall-nut";
    }
}
