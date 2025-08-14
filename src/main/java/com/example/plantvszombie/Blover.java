package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Blover extends Plant implements specialAct {
    private Fog fog;

    public Blover(int x, int y, Fog fog) {
        super(x, y);
        this.fog = fog;
        this.setWaitingTime(6);
        this.setDayPlanet(false);
        this.setHealth(4);
        this.setImage(new ImageView(new Image(getClass().getResource("/75f44f529822720e5a77af436ccb0a46f31fabd6.gif").toExternalForm())));
        this.setEatimage(new ImageView(new Image(getClass().getResource("/75f44f529822720e5a77af436ccb0a46f31fabd6.gif").toExternalForm())));
    }

    @Override
    public void act(Pane root) {
        setActive(true);
        fog.hideTemporarily();
        Timeline restoreTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> fog.restore()));
        restoreTimeline.play();
        restoreTimeline.setOnFinished(e -> this.remove(root));
        AnimationManager.register(restoreTimeline);
    }

    @Override
    String gettype() {
        return "blover";
    }
}
