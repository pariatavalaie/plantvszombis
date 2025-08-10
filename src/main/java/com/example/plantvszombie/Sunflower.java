package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Random;

public class Sunflower extends Planet implements specialAct{

    public Sunflower(int x, int y) {
        super(x, y);
        setWaitingTime(5);
        setHealth(4);
        this.setDayPlanet(true);
        setImage(new ImageView(new Image(getClass().getResource("/sunflower.gif").toExternalForm())));
        setEatimage(new ImageView(new Image(getClass().getResource("/sunflower.gif").toExternalForm())));
    }

    @Override
    public void act(Pane root) {
        double x = Yard.GRID_X+ getCol() * Yard.CELL_WIDTH+ (Yard.CELL_WIDTH - 70) / 2;
        double y = Yard.GRID_Y + getRow()*Yard.Cell_HEIGHT+ (Yard.Cell_HEIGHT - 90) / 2;
        int timeFall =new Random().nextInt(11)+20;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(timeFall), event ->{
            if (!isDead()){
            Sun sun=new Sun();
            Sun.getSuns().add(sun);
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

