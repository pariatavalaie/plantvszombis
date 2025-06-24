package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Iceshroom extends Planet{
    static boolean canplace = true;
    static int cost = 75;
    public Iceshroom(int x,int y) {
        this.row = y;
        this.col = x;
        this.health = 4;
        this.watingtime =5;
        this.dayplanet=false;
        bullets = new ArrayList<Bullet>();
        image=new ImageView(new Image(getClass().getResource("/IceShroom1.gif").toExternalForm()));
        eatimage=new ImageView(new Image(getClass().getResource("/IceShroom2.gif").toExternalForm()));
    }

    @Override
    void act(Pane root) {

    }

    @Override
    void act(Pane root, ArrayList<Zombies> Zombies) {
        for (Zombies z : Zombies) {
            if(z.isAlive()){
                if(z.walker!=null){
                    z.walker.pause();
                }
                if (z.eating!=null) {
                    z.eating.pause();
                }
                ColorAdjust blueTint = new ColorAdjust();
                blueTint.setHue(0.6);
                blueTint.setSaturation(1.0);
                blueTint.setBrightness(0.5);

                z.image.setEffect(blueTint);
            }
        }
        Timeline unfreeze = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
            for (Zombies z : Zombies) {
                if (z.isAlive()) {
                    if (z.walker != null) z.walker.play();
                    if (z.eating != null) z.eating.play();
                    z.image.setEffect(null);
                }
            }
        }));
        unfreeze.play();
        unfreeze.setOnFinished(event -> {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                remove(root);
            }));
            timeline.play();
        });
        AnimationManager.register(unfreeze);


    }

    @Override
    String gettype() {
        return "Ice";
    }
}
