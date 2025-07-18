package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Iceshroom extends Planet implements Act{
    static boolean activate = false;

    public Iceshroom(int x,int y) {
        super(x,y);
        this.setHealth(4);
        this.setWaitingTime(5);
        this.setDayPlanet(false);
        setImage(new ImageView(new Image(getClass().getResource("/IceShroom1.gif").toExternalForm())));
        setEatimage(new ImageView(new Image(getClass().getResource("/IceShroom2.gif").toExternalForm())));
    }

    @Override
    public void act(Pane root, ArrayList<Zombies> Zombies) {
        setActive(true);
        activate =true;
        Timeline unfreeze = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
            activate =false;

        }));
        unfreeze.play();
        unfreeze.setOnFinished(event -> {
                remove(root);
                setDead(true);
        });
        AnimationManager.register(unfreeze);
    }

    @Override
    String gettype() {
        return "Ice";
    }
}
