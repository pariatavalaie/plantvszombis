package com.example.plantvszombie;

import javafx.animation.*;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Zombies {
    private int x;
    private int y;
    private int hp;
    private int speed;
    private ImageView image;
    private ImageView deadZombie;
    private TranslateTransition walker=null;
    private Timeline eating=null;
    private boolean inHouse=false;
    private boolean isHypnotized=false;
    private boolean fighting=false;
    private int direction = -1;
    void move(Pane root){
        double distance = 78;
        double durationInSeconds = getSpeed();
        setWalker(new TranslateTransition(Duration.seconds(durationInSeconds), getImage()));
        getWalker().setByX(getDirection() * distance);
        getWalker().setOnFinished(e -> {
             setX(getX() + getDirection());
            System.out.println(getX());

            if(( getX() < 0 && getDirection() == -1)){
                setInHouse(true);
                getWalker().stop();
                return;
            }
            if (( getX() >8 && getDirection() == 1) || getHp() <= 0) {
                setHp(0);
                System.out.println("Zombie reached the end!");
                getWalker().stop();
                return;
            }
            getWalker().playFromStart();
        });
        getWalker().play();
        AnimationManager.register(getWalker());
    }

    public void reverseDirection() {
        if(isHypnotized()){
        setDirection(getDirection() * -1);
        getImage().setScaleX(getImage().getScaleX() * -1);

        if (getWalker() != null) {
            getWalker().pause();
        }

        move((Pane) getImage().getParent());
            ColorAdjust blueTint = new ColorAdjust();
            blueTint.setHue(-0.6);
            blueTint.setSaturation(1.0);
            blueTint.setBrightness(0.5);
            getImage().setEffect(blueTint);}
    }

    public void damage (ArrayList<Planet> planets,Pane root) {
        for (Planet p : planets) {
            if(p instanceof Shooter){
            Iterator<Bullet> it =((Shooter)p).bullets.iterator();
            while (it.hasNext()) {
                Bullet b = it.next();
                if (isAlive() && this.collidesWith(b,root)) {
                    setHp(getHp() - 1);
                    if((b.type).equals("ICY")){
                        this.setSpeed(this.getSpeed() / 2);
                        if (getWalker() != null) {
                            getWalker().setRate(0.5);
                        }
                    }
                    ColorAdjust blueTint = new ColorAdjust();
                    blueTint.setHue(-0.7);
                    blueTint.setSaturation(1.0);
                    blueTint.setBrightness(0.5);
                    getImage().setEffect(blueTint);
                    PauseTransition pa=new PauseTransition(Duration.seconds(0.1));
                    pa.setOnFinished(e -> {if(!isHypnotized()) getImage().setEffect(null);});
                    pa.play();
                    System.out.println("Zombie HP: " + getHp());
                    it.remove();
                }
            }
        }}}

    public boolean collidesWith(Bullet b,Pane root) {
      if(Math.abs(getImage().getLayoutX()+ getImage().getTranslateX() - (b.imageBullet.getLayoutX()+b.imageBullet.getTranslateX())) <= 80&&Math.abs(getImage().getLayoutY()+ getImage().getTranslateY() - (b.imageBullet.getLayoutY()+b.imageBullet.getTranslateY())) <= 100&& getY() ==b.y) {
          root.getChildren().remove(b.imageBullet);
          b.hit=true;
          return true;
      }
      return false;
    }

    public boolean isAlive() {
        return getHp() >0;
    }

    public void checkAndEatPlant(ArrayList<Planet> planets, Pane root) {
        for (Planet p : planets) {
            if (p.getRow()== this.getY() && p.getCol()== this.getX()) {

                if (getWalker() != null) getWalker().pause();

                Image temp= getImage().getImage();
                Timeline[] eatingRef = new Timeline[1];
                 setEating(new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
                    if (!isAlive()) {
                        if (getWalker() != null) getWalker().play();
                        eatingRef[0].stop();
                        return;
                    }
                    p.setHealth(p.getHealth() - 1);
                    p.getImage().setImage(p.getEatimage().getImage());
                    if ( p.getHealth() < 0) {
                        getWalker().play();
                        getImage().setImage(temp);
                        p.remove(root);
                        planets.remove(p);
                    }
                })));
                getEating().setCycleCount(p.getHealth());
                eatingRef[0] = getEating();
                getEating().play();
                AnimationManager.register(eatingRef[0]);
                break;
            }
        }
    }

    public void checkAndEatZombie(ArrayList<Zombies> Zombie, Pane root) {
        if (!isAlive()) return;
        for (Zombies other : Zombie) {
            if (other == this || !other.isAlive()) continue;

            if (this.getX() == other.getX() && this.getY() == other.getY()) {

                if (this.isHypnotized() == other.isHypnotized()) return;

                if (this.isFighting() || other.isFighting()) return;

                this.setFighting(true);
                other.setFighting(true);

                if (this.getWalker() != null) this.getWalker().pause();
                if (other.getWalker() != null) other.getWalker().pause();
                Timeline[] fightRef = new Timeline[1];
                Timeline fight = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                    if (!this.isAlive() || !other.isAlive()) {
                        this.setFighting(false);
                        other.setFighting(false);

                        if (this.getWalker() != null) this.getWalker().play();
                        if (other.getWalker() != null) other.getWalker().play();
                        fightRef[0].stop();
                        return;
                    }
                    this.setHp(this.getHp() - 1);
                    other.setHp(other.getHp() - 1);
                }));
                fight.setCycleCount(Animation.INDEFINITE);
                fightRef[0] = fight;
                fight.play();
                AnimationManager.register(fightRef[0]);
                break;
            }
        }
    }

    protected ZombieState getState() {
        return new ZombieState(
                this.getClass().getSimpleName(),
                this.getX(),
                this.getY(),
                this.getHp(),
                this.getDirection(),
                this.isHypnotized(),
                this.isInHouse(),
                this.isFighting()

        );
    }

    public void freezeZombie() {
        if(!Iceshroom.activate){
            if (this.isAlive()) {
                if (this.getWalker() != null&&!AnimationManager.isPaused) this.getWalker().play();
                if (this.getEating() != null&&!AnimationManager.isPaused) this.getEating().play();
                this.getImage().setEffect(null);
            }
        }else{
            if(this.isAlive()) {
                if (this.getWalker() != null) {
                    this.getWalker().pause();
                }
                if (this.getEating() != null) {
                    this.getEating().pause();
                }

                ColorAdjust blueTint = new ColorAdjust();
                blueTint.setHue(0.6);
                blueTint.setSaturation(1.0);
                blueTint.setBrightness(0.5);
                this.getImage().setEffect(blueTint);
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public ImageView getDeadZombie() {
        return deadZombie;
    }

    public void setDeadZombie(ImageView deadZombie) {
        this.deadZombie = deadZombie;
    }

    public TranslateTransition getWalker() {
        return walker;
    }

    public void setWalker(TranslateTransition walker) {
        this.walker = walker;
    }

    public Timeline getEating() {
        return eating;
    }

    public void setEating(Timeline eating) {
        this.eating = eating;
    }

    public boolean isInHouse() {
        return inHouse;
    }

    public void setInHouse(boolean inHouse) {
        this.inHouse = inHouse;
    }

    public boolean isHypnotized() {
        return isHypnotized;
    }

    public void setHypnotized(boolean hypnotized) {
        isHypnotized = hypnotized;
    }

    public boolean isFighting() {
        return fighting;
    }

    public void setFighting(boolean fighting) {
        this.fighting = fighting;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}

