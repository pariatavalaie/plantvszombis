package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Scaredy extends Shooter implements Act{
    public boolean scared;
    public Scaredy(int x,int y) {
        this.col = x;
        this.row = y;
        this.watingtime = 2;
        this.dayPlanet =false;
        this.health=3;
        this.image = new ImageView( new Image(getClass().getResource("/Scaredy-shroom.png").toExternalForm()));
        this.eatimage=new ImageView( new Image(getClass().getResource("/Scaredy-Shroom_Hiding.png").toExternalForm()));
        bullets = new ArrayList<>();
        scared=false;
    }
    @Override
    public void act(Pane root , ArrayList<Zombies> zombies) {
        double gridX = 245.0; // Left anchor of grid
        double gridY = 60.0;  // Top anchor of grid
        double cellWidth = 80.0;
        double cellHeight = 100.0;
        double x = gridX + col * 80 + (80 - 70) / 2;
        double y = gridY + row * 100 + (100 - 90) / 2;
        final double[] XZ = {0};


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            boolean shouldShoot = false;
            for (Zombies z : zombies) {
                double zombieX = z.image.getLayoutX() + z.image.getTranslateX();
                if (z.y == row && z.x - col <= 2 && z.x <= 8 && zombieX > x) {
                    scared = true;
                    this.image.setImage(this.eatimage.getImage());
                } else if (!scared && z.y == row && z.x > col && z.x <= 8) {
                    shouldShoot = true;
                    XZ[0] = zombieX; // نزدیک‌ترین زامبی
                    break;
                }
            }

            if (shouldShoot && !dead) {
                Bullet scary = new Bullet(col, row, 3, "MUSHROOM");
                scary.shoot(root, x + 60, XZ[0], y + 20);
                bullets.add(scary);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        AnimationManager.register(timeline);


    }
    @Override
    String gettype() {
        return  "Scaredy";
    }

    @Override
    public PlanetState getState() {
        PlanetState baseState = super.getState();
        boolean scaredValue = this.scared;

        return new scardyState(
                baseState.col,
                baseState.row,
                baseState.type,
                baseState.health,
                baseState.dead,
                ((ShooterState)baseState).bulletStates,
                baseState.remainingCooldown,
                scaredValue
        );
    }

    @Override
    public void loadpplanet(PlanetState planetState, Pane root) {
        super.loadpplanet(planetState, root);
        this.scared=((scardyState)planetState).scardy;
    }
}
