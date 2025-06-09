package com.example.plantvszombie;
import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public abstract class Planet {
    int cost;
    int watingtime;
    int row;
    int col;
    ImageView image;
   static boolean canplace=true;
   ArrayList<Bullet> bullets ;
   abstract void act(Pane root);

    public void cooldown( Button b){
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

