package com.example.plantvszombie;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Bullet {
    private int x;
    private int y;
    private double speed;
    private double xpalnet;
    private double ypalnet;
    private double xzombie;
    private ImageView imageBullet;
    private boolean hit;
    private String type;

    public Bullet(int x, int y, double speed, String type) {
        this.setX(x);
        this.setY(y);
        this.setSpeed(speed);
        setHit(false);
        this.setType(type);
    }

    public void shoot(Pane pane, double xPlant, double xZombie, double yPlant) {
        Image bullet = null;
        if (getType().equals("ICY")) {
            bullet = new Image(getClass().getResource("/snow bullet.png").toExternalForm());
        } else if (getType().equals("NORMAL")) {
            bullet = new Image(getClass().getResource("/pea.png").toExternalForm());
        } else if (getType().equals("MUSHROOM")) {
            bullet = new Image(getClass().getResource("/bullet_11zon.png").toExternalForm());
        } else if (getType().equals("PUFF")) {
            bullet = new Image(getClass().getResource("/mushroom.bullet_11zon.png").toExternalForm());
        }
        setImageBullet(new ImageView(bullet));
        getImageBullet().setFitHeight(25);
        getImageBullet().setFitWidth(25);
        pane.getChildren().add(getImageBullet());
        this.setXpalnet(xPlant);
        this.setYpalnet(yPlant);
        this.setXzombie(xZombie);
        TranslateTransition move = new TranslateTransition(Duration.seconds(getSpeed()), getImageBullet());
        move.setFromX(xPlant);
        move.setToX(xZombie);
        move.setFromY(yPlant);
        move.setToY(yPlant);
        move.play();
        move.setOnFinished(e -> {
            if (!isHit()) {
                pane.getChildren().remove(getImageBullet());
            }
        });
        AnimationManager.register(move);
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }


    public void setXpalnet(double xpalnet) {
        this.xpalnet = xpalnet;
    }

    public void setYpalnet(double ypalnet) {
        this.ypalnet = ypalnet;
    }

    public double getXzombie() {
        return xzombie;
    }

    public void setXzombie(double xzombie) {
        this.xzombie = xzombie;
    }

    public ImageView getImageBullet() {
        return imageBullet;
    }

    public void setImageBullet(ImageView imageBullet) {
        this.imageBullet = imageBullet;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
