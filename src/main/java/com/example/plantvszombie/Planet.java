package com.example.plantvszombie;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public abstract class Planet {
    int cost;
    int watingtime;
    int row;
    int col;
    Image image;
   static boolean canplace=true;
    abstract void act(Pane root);
}
