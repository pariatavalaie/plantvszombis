package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class GraveBuster extends Planet implements specialAct{
    static boolean canplace = true;
    static final int cost=75;
    public GraveBuster(int x,int y) {
        this.col = x;
        this.row = y;
        this.watingtime = 3;
        this.dayplanet=false;
        this.health=4;
        this.image = new ImageView( new Image(getClass().getResource("/Transparent_grave_digger.gif").toExternalForm()));
        this.eatimage=new ImageView( new Image(getClass().getResource("/Transparent_grave_digger.gif").toExternalForm()));
    }
    @Override
    public void act(Pane root) {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            remove(root);
        });
        pause.play();
        AnimationManager.register(pause);
    }

    @Override
    String gettype() {
        return "Grave";
    }
}
