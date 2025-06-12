package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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
     void move(Pane root){
        walker = new Timeline(new KeyFrame(Duration.seconds(speed), e -> {
            if (x <= 0||hp <= 0) {
                walker.stop();
                root.getChildren().remove(image);
                System.out.println(" Zombie reached the house!");
                return;
            }

            x--;

            double newX = 245 + x * 80 + 5;

            TranslateTransition step = new TranslateTransition(Duration.seconds(0.5), image);
            step.setToX(newX - image.getLayoutX());
            step.play();

            System.out.println("Zombie moving to col: " + x);
        }));

        walker.setCycleCount(Timeline.INDEFINITE);
        walker.play();
    }


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
      if(Math.abs(image.getLayoutX()+image.getTranslateX() - (b.imageBullet.getLayoutX()+b.imageBullet.getTranslateX())) <= 80&&Math.abs(image.getLayoutY()+image.getTranslateY() - (b.imageBullet.getLayoutY()+b.imageBullet.getTranslateY())) <= 100) {
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
    public void checkAndEatPlant(ArrayList<Planet> planets, Pane root) {
        for (Planet p : planets) {
            if (p.row==this.y && p.col==this.x) {

                if (walker != null) walker.stop();


                final int[] bites = {0};
                Image temp=image.getImage();
                Timeline eating = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
                    bites[0]++;
                    p.image.setImage(p.eatimage.getImage());

                    if (bites[0] >= p.health) {
                        walker.play();
                        image.setImage(temp);
                        p.remove(root);
                        planets.remove(p);



                    }
                }));
                eating.setCycleCount(p.health);
                eating.play();

                break;
            }
        }
    }







}

