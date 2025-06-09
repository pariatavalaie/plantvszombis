package com.example.plantvszombie;

import javafx.animation.Timeline;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public abstract class Zombies {
    int x;
    int y;
    int hp;
    int speed;
    ImageView image;
    Timeline walker;
    abstract void act(Pane root);
    abstract void move(Pane root);
    public ArrayList<Bullet> bullet = new ArrayList();
    public void isDead(ArrayList<Planet> planets) {
        for (Planet p : planets) {
            for (Bullet b : p.bullets) {
                if (b.x==x&&b.y==y) {
                    hp--;
                }
            }
        }
    }
    public boolean isAlive() {
        return hp>0;
    }
}

