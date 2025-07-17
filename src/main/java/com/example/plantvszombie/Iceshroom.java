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
        this.health = 4;
        this.watingtime =5;
        this.dayPlanet =false;
        image=new ImageView(new Image(getClass().getResource("/IceShroom1.gif").toExternalForm()));
        eatimage=new ImageView(new Image(getClass().getResource("/IceShroom2.gif").toExternalForm()));
    }

    @Override
    public void act(Pane root, ArrayList<Zombies> Zombies) {
        active=true;
        activate =true;
        Timeline unfreeze = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
            activate =false;

        }));
        unfreeze.play();
        unfreeze.setOnFinished(event -> {
                remove(root);
                dead=true;
        });
        AnimationManager.register(unfreeze);


    }

    @Override
    String gettype() {
        return "Ice";
    }
}
