package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bean extends Planet{
    public Bean(int x, int y) {
        super(x,y);
        this.setWatingtime(2);
        this.setHealth(4);
        this.setDayPlanet(true);
        this.setImage(new ImageView(new Image(getClass().getResource("/CoffeeBean2.gif").toExternalForm())));
        this.setEatimage(new ImageView(new Image(getClass().getResource("/CoffeeBean2.gif").toExternalForm())));
    }

    @Override
    String gettype() {
        return"bean";
    }

}
