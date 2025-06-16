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

public class Scaredy extends Planet{
    static boolean canplace=true;
    static final int cost=25;
    public Scaredy(int x,int y) {
        this.col = x;
        this.row = y;
        this.watingtime = 2;
        this.health=3;
        this.image = new ImageView( new Image(getClass().getResource("/Scaredy-shroom.png").toExternalForm()));
        this.eatimage=new ImageView( new Image(getClass().getResource("/Scaredy-Shroom_Hiding.png").toExternalForm()));
        bullets = new ArrayList<>();
    }
    @Override
    void act(Pane root , ArrayList<Zombies> zombies) {
        double gridX = 245.0; // Left anchor of grid
        double gridY = 60.0;  // Top anchor of grid

        double cellWidth = 80.0;
        double cellHeight = 100.0;
        double x = gridX + col * 80 + (80 - 70) / 2;
        double y = gridY + row * 100 + (100 - 90) / 2;
        final double[]XZ={0};



        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            boolean shouldShoot = false;
            for (Zombies z :zombies ) {
                double zombieX = z.image.getLayoutX() + z.image.getTranslateX();
                if (z.y == row && zombieX > col&&z.x<=8) {
                    shouldShoot = true;
                    XZ[0] = zombieX; // نزدیک‌ترین زامبی
                    break;
                }
            }

            if (shouldShoot&&!dead) {
                Bullet scary = new Bullet(row, col, 3);
                scary.shoot(root, x + 60,XZ[0], "MUSHROOM", y);
                bullets.add(scary);
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
