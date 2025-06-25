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

public class Jalapeno extends Planet {
    static boolean canplace = true;
    static int cost = 125;
    public Jalapeno(int x , int y) {
        this.row = y;
        this.col = x;
        this.watingtime = 6;
        this.health = 4;
        this.dayplanet=true;
        this.image = new ImageView( new Image(getClass().getResource("/jalapeno.gif").toExternalForm()));
        this.eatimage=new ImageView( new Image(getClass().getResource("/jalapeno.gif").toExternalForm()));
        bullets = new ArrayList<Bullet>();
    }
    @Override
    void act(Pane pene , ArrayList<Zombies> Zombies){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        for(Zombies z : Zombies){
            if(z.y == this.row&&z.x<=8){
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
    }
    @Override
    void act(Pane pane){
    }

    @Override
    String gettype() {
        return "jalapeno";
    }
}
