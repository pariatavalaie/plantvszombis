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
    static int collectedpoint=200;
    private ImageView sunImage;
    private boolean collected ;
    static ArrayList<Sun> suns = new ArrayList<Sun>();
    public Sun() {
        collected = false;
        Image sun = new Image(getClass().getResource("/sun.png").toExternalForm());
        sunImage = new ImageView(sun);
        sunImage.setFitWidth(60);
        sunImage.setFitHeight(60);
    }

    public void fallingSun(Pane root, double startX, double startY) {
        sunImage.setLayoutX(startX);
        sunImage.setLayoutY(0);
        root.getChildren().add(sunImage);
        TranslateTransition fall = new TranslateTransition(Duration.seconds(5), sunImage);
        fall.setFromY(startY);
        fall.setToY(400);
        fall.setOnFinished(e -> {
           startLifespanTimer(root);
        });
        fall.play();
        AnimationManager.register(fall);


        sunImage.setOnMouseClicked(e -> {
            if (!collected) {
                collected = true;
                suns.remove(this);
                root.getChildren().remove(sunImage);
                System.out.println("Sun collected!");
                collectedpoint=collectedpoint+25;
                System.out.println("collectedpoint: "+collectedpoint);

            }
        });
    }
    private void startLifespanTimer(Pane root) {
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> {
            if (!collected) {
                suns.remove(this);
                root.getChildren().remove(sunImage);
            }
        });
        delay.play();
        AnimationManager.register(delay);
    }
     static void fall(Pane root) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            double randomX = 245 + Math.random() * (9 * 80); // روی زمین
            Sun sun = new Sun();
            suns.add(sun);
           sun.fallingSun (root, randomX, 0); // y = 400 یعنی تا پایین زمین
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // بی‌نهایت اجرا بشه
        timeline.play();
        AnimationManager.register(timeline);
    }

    public void sunflower(Pane root,double x,double y){
        sunImage.setLayoutX(x);
        sunImage.setLayoutY(y);
        root.getChildren().add(sunImage);
        sunImage.setOnMouseClicked(e -> {
            if (!collected) {
                collected = true;
                suns.remove(this);
                root.getChildren().remove(sunImage);
                System.out.println("Sun collected!");
                collectedpoint=collectedpoint+25;
                System.out.println("collectedpoint: "+collectedpoint);

            }
        });
        startLifespanTimer(root);




    }
    public SunState getState() {
        SunState state = new SunState();
        state.x = sunImage.getLayoutX();
        state.y = sunImage.getLayoutY();
        state.z=sunImage.getTranslateY();
        state.isFalling = (sunImage.getTranslateY() > 0); // یا با فلگ جدا دقیق‌تر
        return state;
    }
    public static Sun fromState(SunState state, Pane root) {
        Sun sun = new Sun();
        if (state.isFalling) {
            sun.fallingSun(root, state.x, state.z);
        } else {
            sun.sunflower(root, state.x, state.y);
        }
        return sun;
    }


}

