package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Sunflower extends Planet implements specialAct{
    public Sunflower(int x, int y) {
        super(x, y);
        watingtime=5;
        health=4;
        this.dayPlanet =true;
        image=new ImageView(new Image(getClass().getResource("/sunflower.gif").toExternalForm()));
        eatimage=new ImageView(new Image(getClass().getResource("/sunflower.gif").toExternalForm()));

    }
    @Override
    public void act(Pane root) {
        double x = Yard.GRID_X+ getCol() * Yard.CELL_WIDTH+ (Yard.CELL_WIDTH - 70) / 2;
        double y = Yard.GRID_Y + getRow()*Yard.Cell_HEIGHT+ (Yard.Cell_HEIGHT - 90) / 2;
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

