package com.example.plantvszombie;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Sun {
    static int collectedpoint=0;
    private ImageView sunImage;
    private boolean collected ;
    public Sun() {
        collected = false;
    }

    private   void fallingSun(Pane root, double startX, double endY) {
        Image sun = new Image(getClass().getResource("/sun.png").toExternalForm());
        sunImage = new ImageView(sun);
        sunImage.setFitWidth(60);
        sunImage.setFitHeight(60);
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


        sunImage.setOnMouseClicked(e -> {
            if (!collected) {
                collected = true;
                root.getChildren().remove(sunImage);
                System.out.println("Sun collected!");
                collectedpoint=collectedpoint+25;

            }
        });
    }
    private void startLifespanTimer(Pane root) {
        PauseTransition delay = new PauseTransition(Duration.seconds(10));
        delay.setOnFinished(e -> {
            if (!collected) {
                root.getChildren().remove(sunImage);
            }
        });
        delay.play();
    }
}

