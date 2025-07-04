package com.example.plantvszombie;

import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class WallNut extends Planet{
    static boolean canplace=true;
    static final int cost=50;
    public WallNut(int x,int y){
        this.row=y;
        this.col=x;
        watingtime=5;
        this.health=10;
        this.dayplanet=true;
        this.eatimage=new ImageView(new Image(getClass().getResource("/walnut_half_life.gif").toExternalForm()));
        this.image=new ImageView(new Image(getClass().getResource("/walnut_full_life.gif").toExternalForm()));
    }

    @Override
    String gettype() {
        return "Wall-nut";
    }
}
