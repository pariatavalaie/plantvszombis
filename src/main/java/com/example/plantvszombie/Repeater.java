package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.ArrayList;

public class Repeater extends Shooter implements Act{
    static boolean canplace = true;
    static final int cost=200;
    public Repeater(int x, int y) {
        this.row = y;
        this.col = x;
        this.dayplanet=true;
        this.watingtime = 7;
        this.health=4;
        bullets=new ArrayList<>();
        this.image =new ImageView( new Image(getClass().getResource("/repeater.gif").toExternalForm()));
        this.eatimage =new ImageView( new Image(getClass().getResource("/repeater.gif").toExternalForm()));
    }
    @Override
    public void act(Pane root,ArrayList<Zombies>Zombies) {
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
                Bullet repeater1 = new Bullet(col, row, 3, "NORMAL");
                Bullet repeater2 = new Bullet(col,row, 4, "NORMAL");
                repeater1.shoot(root, x + 60,XZ[0], y);
                repeater2.shoot(root, x + 60,XZ[0],  y);
                bullets.add(repeater1);
                bullets.add(repeater2);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        AnimationManager.register(timeline);


    }

    @Override
    String gettype() {
        return "Repeater";
    }
}
