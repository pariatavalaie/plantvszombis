package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Blover extends Planet implements specialAct{
    static boolean canplace = true;
    static final int cost = 100;
    private Fog fog;
    public Blover(int x, int y, Fog fog) {
        this.row = y;
        this.col = x;
        this.fog = fog;
        this.watingtime = 3;
        this.dayplanet=false;
        this.health = 4;
        this.image = new ImageView(new Image(getClass().getResource("/75f44f529822720e5a77af436ccb0a46f31fabd6.gif").toExternalForm()));
        this.eatimage = new ImageView(new Image(getClass().getResource("/75f44f529822720e5a77af436ccb0a46f31fabd6.gif").toExternalForm()));
    }

    @Override
   public void act(Pane root) {
        fog.hideTemporarily();

        Timeline restoreTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> fog.restore()));
        restoreTimeline.play();
        restoreTimeline.setOnFinished(e -> {
            this.remove(root);
        });
        AnimationManager.register(restoreTimeline);
    }


    @Override
    String gettype() {
        return  "blover";
    }
}
