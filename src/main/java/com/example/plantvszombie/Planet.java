package com.example.plantvszombie;
import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.example.plantvszombie.Sunflower.cost;

public abstract class Planet {
    int watingtime;
    int row;
    int col;
    int health;
    ImageView image;
    ImageView eatimage;
    ArrayList<Bullet> bullets;
    boolean dead=false;
    PauseTransition cooldown;
    Boolean dayplanet;

    abstract void act(Pane root,ArrayList<Zombies>Zombies);

    abstract void act(Pane root);
    public void remove(Pane root){
        root.getChildren().remove(image);
        root.getChildren().remove(eatimage);
        dead=true;
    };



}