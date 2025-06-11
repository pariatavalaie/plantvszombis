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
    ArrayList<Bullet> bullets;

    abstract void act(Pane root,ArrayList<Zombies>Zombies);

    abstract void act(Pane root);


}