package com.example.plantvszombie;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.PointLight;
import javafx.scene.effect.ColorAdjust;
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
    ImageView deadZombie;
    Timeline walker=null;
    Timeline eating=null;
    boolean inHouse=false;
    boolean isHypnotized=false;
    boolean fighting=false;
    public int direction = -1;
    abstract void act(Pane root);
    void move(Pane root){
        walker = new Timeline(new KeyFrame(Duration.seconds(speed), e -> {
            if (x <= 0 && direction == -1 || x >= 8 && direction == 1 || hp <= 0) {
                walker.stop();
                root.getChildren().remove(image);
                hp=0;
                System.out.println("Zombie reached the end!");
                return;
            }

            x += direction;

            double newX = 245 + x * 80 + 5;

            TranslateTransition step = new TranslateTransition(Duration.seconds(0.5), image);
            step.setToX(newX - image.getLayoutX());
            step.play();

            System.out.println("Zombie moving to col: " + x);
        }));

        walker.setCycleCount(Timeline.INDEFINITE);
        walker.play();
        AnimationManager.register(walker);
    }
    public void reverseDirection() {
        if(isHypnotized){
        direction *= -1;

        image.setScaleX(image.getScaleX() * -1); // برعکس شدن تصویر

        if (walker != null) {
            walker.stop();
        }

        move((Pane) image.getParent());
            ColorAdjust blueTint = new ColorAdjust();
            blueTint.setHue(-0.6);
            blueTint.setSaturation(1.0);
            blueTint.setBrightness(0.5);

            image.setEffect(blueTint);}
    }



    public void damage  (ArrayList<Planet> planets,Pane root) {
        for (Planet p : planets) {
            Iterator<Bullet> it = p.bullets.iterator();
            while (it.hasNext()) {
                Bullet b = it.next();
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
      if(Math.abs(image.getLayoutX()+image.getTranslateX() - (b.imageBullet.getLayoutX()+b.imageBullet.getTranslateX())) <= 80&&Math.abs(image.getLayoutY()+image.getTranslateY() - (b.imageBullet.getLayoutY()+b.imageBullet.getTranslateY())) <= 100&&y==b.x) {
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

                Image temp=image.getImage();
                Timeline[] eatingRef = new Timeline[1];
                 eating = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
                    if (!isAlive()) {
                        if (walker != null) walker.play();
                        eatingRef[0].stop();
                        return;
                    }


                    p.health--;
                    p.image.setImage(p.eatimage.getImage());


                    if ( p.health< 0) {
                        walker.play();
                        image.setImage(temp);
                        p.remove(root);
                        planets.remove(p);

                    }
                }));
                eating.setCycleCount(p.health);
                eatingRef[0] = eating;
                eating.play();
                AnimationManager.register(eatingRef[0]);
                break;
            }
        }
    }
    public void checkAndEatZombie(ArrayList<Zombies> Zombie, Pane root) {
        if (!isAlive()) return;

        for (Zombies other : Zombie) {
            if (other == this || !other.isAlive()) continue;

            if (this.x==other.x && this.y==other.y) {


                if (!(this.isHypnotized ^ other.isHypnotized)) return;

                if (this.fighting || other.fighting) return;

                this.fighting = true;
                other.fighting = true;

                if (this.walker != null) this.walker.stop();
                if (other.walker != null) other.walker.stop();
                Timeline[] fightRef = new Timeline[1];
                Timeline fight = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                    if (!this.isAlive() || !other.isAlive()) {
                        this.fighting = false;
                        other.fighting = false;

                        if (this.walker != null) this.walker.play();
                        if (other.walker != null) other.walker.play();
                        fightRef[0].stop();
                        return;
                    }

                    this.hp--;
                    other.hp--;
                }));

                fight.setCycleCount(Animation.INDEFINITE);
                fightRef[0] = fight;
                fight.play();
                AnimationManager.register(fightRef[0]);

                break;
            }
        }
    }
    public void init(){
         if(x<0){
             inHouse=true;
         }
    }
    public ZombieState getState() {

        return new ZombieState(
                this.getClass().getSimpleName(),
                this.x,
                this.y,
                this.hp,
                this.direction,
                this.isHypnotized,
                this.inHouse,
                this.fighting

        );
    }





}

