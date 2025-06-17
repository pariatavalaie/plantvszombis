package com.example.plantvszombie;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class StoneGrave {
    int x;
    int y;
    ImageView image;
    boolean zombieSpawned;
    Pane pane;
    PauseTransition stop;
    public StoneGrave(int x, int y, Pane root) {
        this.x = x;
        this.y = y;
        zombieSpawned = false;
        image =new ImageView( new Image(getClass().getResource("/Lost_City_Gravestone_HD.png").toExternalForm()));
        image.setFitHeight(80);
        image.setFitWidth(100);
        double startX = 245 + x * 80 + 5;
        double startY = 60 + y * 100 + 10;

        image.setLayoutX(startX);
        image.setLayoutY(startY);
        this.pane = root;
        root.getChildren().add(image);


    }
    public void spawnZombie(ArrayList<Zombies>z,ArrayList<StoneGrave>graves) {
        Random random = new Random();

      if (zombieSpawned) return;
      stop = new PauseTransition(Duration.seconds(random.nextInt(5)+5));

        stop.setOnFinished(event -> {

        zombieSpawned = true;

        Zombies zombie;

        double chance = random.nextFloat();
        if (chance < 0.9) {
            zombie=new NormalZombie(x,y,pane);
            z.add(zombie);
            remove(graves);
        }else if (chance < 0.8) {
            zombie=new ScreendoorZombie(x,y,pane);
            z.add(zombie);
            remove(graves);
        }else if (chance < 0.7) {
            zombie=new ConeheadZombie(x,y,pane);
            z.add(zombie);
            remove(graves);
        }else if (chance < 0.6) {
            zombie=new ImpZombie(x,y,pane);
            z.add(zombie);
            remove(graves);
        }});
        stop.play();

    }
    public void remove(ArrayList<StoneGrave>graves) {
        pane.getChildren().remove(image);
        graves.remove(this);
         stop.stop();
            }


}
