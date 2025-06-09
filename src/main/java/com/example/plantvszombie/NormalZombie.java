package com.example.plantvszombie;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class NormalZombie extends Zombies{

    public NormalZombie(int x , int y,Pane root) {
        this.x = x;
        this.y = y;
        this.hp = 5;
        this.speed = 4;
        this.image = new ImageView(new Image(getClass().getResource("/normalzombie.gif").toExternalForm()));
        image.setFitHeight(80);
        image.setFitWidth(100);
        double startX = 245 + x * 80 + 5;
        double startY = 60 + y * 100 + 10;

        image.setLayoutX(startX);
        image.setLayoutY(startY);

        root.getChildren().add(image);

        move(root);
    }
    @Override
    void act(Pane root){

    }
    @Override
    void move(Pane root) {
        walker = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
            if (x <= 0||hp <= 0) {
                walker.stop();
                root.getChildren().remove(image);
                System.out.println("☠️ Zombie reached the house!");
                return;
            }

            x--;

            double newX = 245 + x * 80 + 5;

            TranslateTransition step = new TranslateTransition(Duration.seconds(0.5), image);
            step.setToX(newX - image.getLayoutX());
            step.play();

            System.out.println("🧟‍♂️ Zombie moving to col: " + x);
        }));

        walker.setCycleCount(Timeline.INDEFINITE);
        walker.play();
    }

    }

