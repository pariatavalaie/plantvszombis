package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WallNut extends Planet{
    public WallNut(int x,int y){
        super(x,y);
        watingtime=5;
        this.health=10;
        this.dayPlanet =true;
        this.eatimage=new ImageView(new Image(getClass().getResource("/walnut_half_life.gif").toExternalForm()));
        this.image=new ImageView(new Image(getClass().getResource("/walnut_full_life.gif").toExternalForm()));
    }

    @Override
    String gettype() {
        return "Wall-nut";
    }
}
