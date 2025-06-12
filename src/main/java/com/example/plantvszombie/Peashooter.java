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

public class Peashooter extends Planet{
    static boolean canplace = true;
    static final int cost=100;
    public Peashooter(int x, int y) {
        this.row = y;
        this.col = x;
        watingtime = 6;
        health=4;
        bullets = new ArrayList<Bullet>();
        this.image = new ImageView(new Image(getClass().getResource("/peashooter.gif").toExternalForm()));
        this.image = new ImageView(new Image(getClass().getResource("/peashooter.gif").toExternalForm()));
    }
    public void act(Pane root) {

    }

    @Override
    void act(Pane root,ArrayList<Zombies>Zombies) {
        double gridX = 245.0; // Left anchor of grid
        double gridY = 60.0;  // Top anchor of grid

        double cellWidth = 80.0;
        double cellHeight = 100.0;
        final double[]XZ={0};

        double x = gridX + col * 80 + (80 - 70) / 2;
        double y = gridY + row * 100 + (100 - 90) / 2;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            boolean shouldShoot = false;
            for (Zombies z :Zombies ) {
                double zombieX = z.image.getLayoutX() + z.image.getTranslateX();
                if (z.y == row && zombieX > x&&z.x<=8) {
                    shouldShoot = true;
                    XZ[0] = zombieX; // نزدیک‌ترین زامبی
                    break;
                }
            }

            if (shouldShoot&&!dead) {
                Bullet repeater1 = new Bullet(row, col, 3);
                repeater1.shoot(root, x + 60,XZ[0], "NORMAL", y);
                bullets.add(repeater1);
            }
        }));
          timeline.setCycleCount(Timeline.INDEFINITE);
          timeline.play();




    }
    public void cooldown( Button b){
        PauseTransition cooldown = new PauseTransition(Duration.seconds(watingtime));
        cooldown.setOnFinished(ev -> {
            canplace= true;
            if(cost<=Sun.collectedpoint){
                b.setDisable(false);
                b.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
                System.out.println("✅ You can place another Sunflower now");}
        });
        cooldown.play();
    }

}


