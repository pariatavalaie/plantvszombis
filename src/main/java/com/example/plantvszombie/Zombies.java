package com.example.plantvszombie;

import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public abstract class Zombies {
    int x;
    int y;
    int hp;
    int speed;
    Image image;
    static boolean alive = true;
    abstract void act(Pane root);
    public ArrayList<Bullet>bullet = new ArrayList();
    public boolean isDead(ArrayList<Planet> planets){
        for(Planet p : planets){
            p.
        }
    }
}
