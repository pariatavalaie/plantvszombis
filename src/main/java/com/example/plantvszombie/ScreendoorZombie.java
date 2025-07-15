package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ScreendoorZombie extends Zombies{
 public ScreendoorZombie(int x,int y,Pane root) {
     this.x = x;
     this.y = y;
     this.hp=10;
     this.speed=4;
     this.image = new ImageView(new Image(getClass().getResource("/ScreenZombieWalk.gif").toExternalForm()));
     this.deadZombie = new ImageView(new Image(getClass().getResource("/burntZombie.gif").toExternalForm()));
     deadZombie.setFitHeight(80);
     deadZombie.setFitWidth(100);
     image.setFitHeight(80);
     image.setFitWidth(100);
     double startX = 245 + x * 80 + 5;
     double startY = 60 + y * 100 + 10;
     image.setLayoutX(startX);
     image.setLayoutY(startY);

     root.getChildren().add(image);
     move(root);


 }

}
