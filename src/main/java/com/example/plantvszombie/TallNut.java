package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class TallNut extends Planet {
    static boolean canplace=true;
    static final int cost=125;
    public TallNut(int x,int y) {
        this.row = y;
        this.col = x;
        this.health = 16;
        this.dayplanet = true;
        watingtime = 7;
        bullets = new ArrayList<>();
        this.eatimage = new ImageView(new Image(getClass().getResource("/Tallnut3.gif").toExternalForm()));
        this.image = new ImageView(new Image(getClass().getResource("/TallNut1.gif").toExternalForm()));

    }
    @Override
    String gettype() {
        return"Tall-nut";
    }
}
