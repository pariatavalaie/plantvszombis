package com.example.plantvszombie;

import javafx.animation.Timeline;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;

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
    public void damage  (ArrayList<Planet> planets,Pane root) {
        for (Planet p : planets) {
            Iterator<Bullet> it = p.bullets.iterator(); // برای حذف گلوله بعد برخورد
            while (it.hasNext()) {
                Bullet b = it.next();
                // اگر زامبی زنده است و با گلوله برخورد داشته
                if (isAlive() && this.collidesWith(b,root)) {
                    hp--;
                    System.out.println("Zombie HP: " + hp);
                    it.remove();
                }

            }
        }}
    public void remove(Pane root,ArrayList<Zombies>zombies) {
        if(!isAlive()) {
            root.getChildren().remove(image);
            zombies.remove(this);

        }
    }
    public boolean collidesWith(Bullet b,Pane root) {
      if(Math.abs(image.getLayoutX()+image.getTranslateX() - (b.imageBullet.getLayoutX()+b.imageBullet.getTranslateX())) <= 10) {
          System.out.println("Q");
          root.getChildren().remove(b.imageBullet);
          b.hit=true;
          return true;

      }
      return false;
    }

    public boolean isAlive() {
        return hp>0;
    }
}

