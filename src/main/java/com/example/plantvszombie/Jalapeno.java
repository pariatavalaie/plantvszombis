package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Jalapeno extends Planet implements Act {
    public Jalapeno(int x , int y) {
        super(x, y);
        this.setWatingtime(6);
        this.setHealth(4);
        this.setDayPlanet(true);
        this.setImage(new ImageView( new Image(getClass().getResource("/jalapeno.gif").toExternalForm())));
        this.setEatimage(new ImageView( new Image(getClass().getResource("/jalapeno.gif").toExternalForm())));
    }
    @Override
    public void act(Pane pene , ArrayList<Zombies> Zombies){
        setActive(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        for(Zombies z : Zombies){
            if(z.y == getRow()&& z.x <= 8){
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(ev-> {
                    z.hp = 0;
                });
                pause.play();
                z.image.setImage(z.deadZombie.getImage());
            }
        }
        }));
        timeline.setCycleCount(1);
        timeline.play();
        AnimationManager.register(timeline);
        Timeline timelineDelete = new Timeline(
                new KeyFrame(Duration.seconds(2), e -> this.setDead(true))
        );
        timelineDelete.setCycleCount(1);
        timelineDelete.play();
        AnimationManager.register(timelineDelete);
    }

    @Override
    String gettype() {
        return "jalapeno";
    }

}
