package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Peashooter extends Planet{
    public Peashooter(int x, int y) {
        this.row = x;
        this.col = y;
        this.cost = 100;
        this.watingtime = 6;
        this.image = new Image(getClass().getResource("/peashooter.gif").toExternalForm());
    }

    @Override
    void act(Pane root) {
       Bullet peashooter = new Bullet(this.row,this.col,3);
       peashooter.shoot(root,getX(),800,"NORMAL");

    }
    public int getX(){
        return this.row;
    }
    public int getY(){
        return this.col;
    }
}
