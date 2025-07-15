package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bean extends Planet{
    static int cost = 75;
    public Bean(int x, int y) {
        this.row = y;
        this.col = x;
        this.watingtime = 2;
        this.health = 4;
        this.dayPlanet =true;
        this.image = new ImageView(new Image(getClass().getResource("/CoffeeBean2.gif").toExternalForm()));
        this.eatimage=new ImageView(new Image(getClass().getResource("/CoffeeBean2.gif").toExternalForm()));
    }

    @Override
    String gettype() {
        return"bean";
    }

}
