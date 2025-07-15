package com.example.plantvszombie;

import javafx.animation.*;
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
    TranslateTransition walker=null;
    Timeline eating=null;
    boolean inHouse=false;
    boolean isHypnotized=false;
    boolean fighting=false;
    public int direction = -1;
    abstract void act(Pane root);
    void move(Pane root){
        double distance = 80;
        double durationInSeconds = speed;
        walker = new TranslateTransition(Duration.seconds(durationInSeconds), image);
        walker.setByX(direction * distance);
        walker.setOnFinished(e -> {
            x += direction;

            if((x < 0 && direction == -1)){
                inHouse=true;
                return;
            }
            if ((x >8 && direction == 1) || hp <= 0) {
                hp = 0;
                System.out.println("Zombie reached the end!");
                return;
            }
            walker.playFromStart();
        });
        walker.play();
        AnimationManager.register(walker);
    }


    public void reverseDirection() {
        if(isHypnotized){
        direction *= -1;

        image.setScaleX(image.getScaleX() * -1); // برعکس شدن تصویر

        if (walker != null) {
            walker.pause();
        }

        move((Pane) image.getParent());
            ColorAdjust blueTint = new ColorAdjust();
            blueTint.setHue(-0.6);
            blueTint.setSaturation(1.0);
            blueTint.setBrightness(0.5);

            image.setEffect(blueTint);}
    }



    public void damage (ArrayList<Planet> planets,Pane root) {
        for (Planet p : planets) {
            if(p instanceof Shooter){
            Iterator<Bullet> it =((Shooter)p).bullets.iterator();
            while (it.hasNext()) {
                Bullet b = it.next();
                if (isAlive() && this.collidesWith(b,root)) {
                    hp--;
                    if((b.type).equals("ICY")){
                        this.speed = this.speed / 2;
                        if (walker != null) {
                            walker.setRate(0.5);
                        }
                    }
                    ColorAdjust blueTint = new ColorAdjust();
                    blueTint.setHue(-0.7);
                    blueTint.setSaturation(1.0);
                    blueTint.setBrightness(0.5);
                    image.setEffect(blueTint);
                    PauseTransition pa=new PauseTransition(Duration.seconds(0.1));
                    pa.setOnFinished(e -> {if(!isHypnotized)image.setEffect(null);});
                    pa.play();
                    System.out.println("Zombie HP: " + hp);
                    it.remove();
                }


            }
        }}}

    public boolean collidesWith(Bullet b,Pane root) {
      if(Math.abs(image.getLayoutX()+image.getTranslateX() - (b.imageBullet.getLayoutX()+b.imageBullet.getTranslateX())) <= 80&&Math.abs(image.getLayoutY()+image.getTranslateY() - (b.imageBullet.getLayoutY()+b.imageBullet.getTranslateY())) <= 100&&y==b.y) {
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

                if (walker != null) walker.pause();

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


                if (this.isHypnotized == other.isHypnotized) return;

                if (this.fighting || other.fighting) return;

                this.fighting = true;
                other.fighting = true;

                if (this.walker != null) this.walker.pause();
                if (other.walker != null) other.walker.pause();
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

