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
    public Jalapeno(int x , int y) {
        this.row = y;
        this.col = x;
        this.watingtime = 6;
        this.cost = 50;
        this.image = new ImageView( new Image(getClass().getResource("/jalapeno.gif").toExternalForm()));
        bullets = new ArrayList<Bullet>();
    }
    @Override
    void act(Pane pene , ArrayList<Zombies> Zombies){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        for(Zombies z : Zombies){
            if(z.y == this.row){
                z.hp = 0;
            }
        }
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }
    @Override
    void act(Pane pane){
    }

    public void cooldown(Button b){
        PauseTransition cooldown = new PauseTransition(Duration.seconds(watingtime));
        cooldown.setOnFinished(ev -> {
            canplace= true;
            b.setDisable(false);
            b.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
            System.out.println("✅ You can place another Sunflower now");
        });
        cooldown.play();
    }
}
