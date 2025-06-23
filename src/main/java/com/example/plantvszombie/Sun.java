package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Sun {
    static int collectedpoint=200;
    private ImageView sunImage;
    private boolean collected ;
    public Sun() {
        collected = false;
        Image sun = new Image(getClass().getResource("/sun.png").toExternalForm());
        sunImage = new ImageView(sun);
        sunImage.setFitWidth(60);
        sunImage.setFitHeight(60);
    }

    public void fallingSun(Pane root, double startX, double endY) {
        sunImage.setLayoutX(startX);
        sunImage.setLayoutY(0);
        root.getChildren().add(sunImage);
        TranslateTransition fall = new TranslateTransition(Duration.seconds(5), sunImage);
        fall.setFromY(0);
        fall.setToY(endY);
        fall.setOnFinished(e -> {
           startLifespanTimer(root);
        });
        fall.play();
        AnimationManager.register(fall);


        sunImage.setOnMouseClicked(e -> {
            if (!collected) {
                collected = true;
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
                root.getChildren().remove(sunImage);
            }
        });
        delay.play();
        AnimationManager.register(delay);
    }
     static void fall(Pane root) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            double randomX = 245 + Math.random() * (9 * 80); // روی زمین
            new Sun().fallingSun (root, randomX, 400); // y = 400 یعنی تا پایین زمین
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
                root.getChildren().remove(sunImage);
                System.out.println("Sun collected!");
                collectedpoint=collectedpoint+25;
                System.out.println("collectedpoint: "+collectedpoint);

            }
        });
        startLifespanTimer(root);




    }

}

