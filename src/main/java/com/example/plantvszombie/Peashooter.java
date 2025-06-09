package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Peashooter extends Planet{
    public Peashooter(int x, int y) {
        this.row = y;
        this.col = x;
        this.cost = 100;
        this.watingtime = 6;
        this.image = new ImageView(new Image(getClass().getResource("/peashooter.gif").toExternalForm()));
    }

    @Override
    void act(Pane root) {
        double gridX = 245.0; // Left anchor of grid
        double gridY = 60.0;  // Top anchor of grid

        double cellWidth = 80.0;
        double cellHeight = 100.0;

        double x = gridX + col * 80 + (80 - 70) / 2;
        double y = gridY + row * 100+ (100 - 90) / 2;
          Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),event ->{
              Bullet peashooter = new Bullet(row,col,3);
              peashooter.shoot(root,x+60,800,"NORMAL",y);
          } ));
          timeline.setCycleCount(Timeline.INDEFINITE);
          timeline.play();




    }

}
