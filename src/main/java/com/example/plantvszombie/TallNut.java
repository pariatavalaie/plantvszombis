package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TallNut extends Planet {

    public TallNut(int x,int y) {
        this.row = y;
        this.col = x;
        this.health = 16;
        this.dayPlanet = true;
        watingtime = 7;
        this.eatimage = new ImageView(new Image(getClass().getResource("/Tallnut3.gif").toExternalForm()));
        this.image = new ImageView(new Image(getClass().getResource("/TallNut1.gif").toExternalForm()));

    }
    @Override
    String gettype() {
        return"Tall-nut";
    }
}
