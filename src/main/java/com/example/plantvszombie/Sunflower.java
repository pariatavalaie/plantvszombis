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

public class Sunflower extends Planet implements specialAct{
    static boolean canplace = true;
    static final int cost=50;
    public Sunflower(int x, int y) {
        watingtime=5;
        row=y;
        health=4;
        col=x;
        this.dayplanet=true;
        image=new ImageView(new Image(getClass().getResource("/sunflower.gif").toExternalForm()));
        eatimage=new ImageView(new Image(getClass().getResource("/sunflower.gif").toExternalForm()));

    }

    @Override
    public void act(Pane root) {
        double gridX = 245.0; // Left anchor of grid
        double gridY = 60.0;  // Top anchor of grid

        double cellWidth = 80.0;
        double cellHeight = 100.0;

        double x = gridX + col * cellWidth + (cellWidth - 70) / 2;
        double y = gridY + row * cellHeight+ (cellHeight - 90) / 2;

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10),event ->{
            if (!dead){
            Sun sun=new Sun();
            Sun.suns.add(sun);
            sun.sunflower(root,x,y);}

        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        AnimationManager.register(timeline);
    }

    @Override
    String gettype() {
        return "Sunflower";
    }
}

