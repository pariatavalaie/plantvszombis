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

public class Puff extends Planet{
    static boolean canplace = true;
    static final int cost=0;
    public Puff(int x , int y) {
        this.row = y;
        this.col = x;
        this.health = 4;
        this.watingtime = 1;
        this.dayplanet = false;
        bullets = new ArrayList<Bullet>();
        image=new ImageView(new Image(getClass().getResource("/PuffShroom1 (10).gif").toExternalForm()));
        eatimage=new ImageView(new Image(getClass().getResource("/PuffShroom1 (10).gif").toExternalForm()));
    }
    @Override
    void act(Pane root,ArrayList<Zombies>zombies){
        double gridX = 245.0; // Left anchor of grid
        double gridY = 60.0;  // Top anchor of grid

        double cellWidth = 80.0;
        double cellHeight = 100.0;
        final double[]XZ={0};
        double x = gridX + col * 80 + (80 - 70) / 2;
        double y = gridY + row * 100 + (100 - 90) / 2;

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            boolean shouldShoot = false;
            for (Zombies z :zombies ) {
                double zombieX = z.image.getLayoutX() + z.image.getTranslateX();
                if (z.y == row && z.x-col<=4 &&z.x<=8&&zombieX>x) {
                    shouldShoot = true;
                    XZ[0] = zombieX;
                    break;
                }
            }

            if (shouldShoot&&!dead) {
                Bullet puff = new Bullet(row, col, 3);
                puff.shoot(root, x + 60,XZ[0], "PUFF", y+40);
                bullets.add(puff);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


    }
    @Override
    void act(Pane root){

    }
    public void cooldown( Button b){
        cooldown = new PauseTransition(Duration.seconds(watingtime));
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
