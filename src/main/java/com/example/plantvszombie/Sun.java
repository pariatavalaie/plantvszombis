package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Sun {
    private static int collectedpoint = 50;
    private ImageView sunImage;
    private boolean collected ;
    private static ArrayList<Sun> suns = new ArrayList<Sun>();
    private boolean isFalling = false;

    public Sun() {
        setCollected(false);
        Image sun = new Image(getClass().getResource("/sun.png").toExternalForm());
        setSunImage(new ImageView(sun));
        getSunImage().setFitWidth(60);
        getSunImage().setFitHeight(60);
    }

    public static int getCollectedpoint() {
        return collectedpoint;
    }

    public static void setCollectedpoint(int collectedpoint) {
        Sun.collectedpoint = collectedpoint;
    }

    public static ArrayList<Sun> getSuns() {
        return suns;
    }

    public static void setSuns(ArrayList<Sun> suns) {
        Sun.suns = suns;
    }

    public void fallingSun(Pane root, double startX, double startY) {
        getSunImage().setLayoutX(startX);
        getSunImage().setLayoutY(0);
        this.setFalling(true);
        root.getChildren().add(getSunImage());
        TranslateTransition fall = new TranslateTransition(Duration.seconds(5), getSunImage());
        fall.setFromY(startY);
        fall.setToY(400);
        fall.setOnFinished(e -> {
           startLifespanTimer(root);
        });
        fall.play();
        AnimationManager.register(fall);
        getSunImage().setOnMouseClicked(e -> {
            if (!isCollected()) {
                setCollected(true);
                getSuns().remove(this);
                root.getChildren().remove(getSunImage());
                System.out.println("Sun collected!");
                setCollectedpoint(getCollectedpoint() +25);
                System.out.println("collectedpoint: "+ getCollectedpoint());

            }
        });
    }

    private void startLifespanTimer(Pane root) {
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> {
            if (!isCollected()) {
                getSuns().remove(this);
                root.getChildren().remove(getSunImage());
            }
        });
        delay.play();
        AnimationManager.register(delay);
    }

     static void fall(Pane root) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            double randomX = 245 + Math.random() * (9 * 80); // روی زمین
            Sun sun = new Sun();
            sun.setFalling(true);
            getSuns().add(sun);
           sun.fallingSun (root, randomX, 0);
           GameServer.notifySunSpawn(sun.getState());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        AnimationManager.register(timeline);
    }

    public void sunflower(Pane root,double x,double y){
        getSunImage().setLayoutX(x);
        getSunImage().setLayoutY(y);
        root.getChildren().add(getSunImage());
        getSunImage().setOnMouseClicked(e -> {
            if (!isCollected()) {
                setCollected(true);
                getSuns().remove(this);
                root.getChildren().remove(getSunImage());
                System.out.println("Sun collected!");
                setCollectedpoint(getCollectedpoint() +25);
                System.out.println("collectedpoint: "+ getCollectedpoint());
            }
        });
        startLifespanTimer(root);
    }
    public SunState getState() {
        SunState state = new SunState();
        state.setX(getSunImage().getLayoutX());
        state.setY(getSunImage().getLayoutY());
        state.setZ(getSunImage().getTranslateY());
        state.setFalling(isFalling());
        return state;
    }
    public static Sun fromState(SunState state, Pane root) {
        Sun sun = new Sun();
        if (state.isFalling()) {
            sun.fallingSun(root, state.getX(), state.getZ());
        } else {
            sun.sunflower(root, state.getX(), state.getY());
        }
        return sun;
    }

    public ImageView getSunImage() {
        return sunImage;
    }

    public void setSunImage(ImageView sunImage) {
        this.sunImage = sunImage;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }
}

