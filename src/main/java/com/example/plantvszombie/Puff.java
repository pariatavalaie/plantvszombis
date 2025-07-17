package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Puff extends Shooter{
    public Puff(int x , int y) {
        super(x,y);
        this.health = 4;
        this.watingtime = 1;
        this.dayPlanet = false;
        image=new ImageView(new Image(getClass().getResource("/PuffShroom1 (10).gif").toExternalForm()));
        eatimage=new ImageView(new Image(getClass().getResource("/PuffShroom1 (10).gif").toExternalForm()));
    }
    @Override
    public void act(Pane root,ArrayList<Zombies>zombies){
        active=true;
        final double[]XZ={0};
        double x = Yard.GRID_X + getCol() * Yard.CELL_WIDTH + (Yard.CELL_WIDTH - 70) / 2;
        double y = Yard.GRID_Y+ getRow() * Yard.Cell_HEIGHT + (Yard.Cell_HEIGHT- 90) / 2;

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            boolean shouldShoot = false;
            for (Zombies z :zombies ) {
                double zombieX = z.image.getLayoutX() + z.image.getTranslateX();
                if (z.y == getRow() && z.x-getCol()<=4 &&z.x<=8&&zombieX>x) {
                    shouldShoot = true;
                    XZ[0] = zombieX;
                    break;
                }
            }

            if (shouldShoot&&!dead) {
                shoot(root,x,XZ[0], y);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        AnimationManager.register(timeline);
    }

    @Override
    void shoot(Pane root, double x, double xzombie, double y) {
        Bullet puff = new Bullet(getCol(), getRow(), 3,"PUFF");
        puff.shoot(root, x + 60,xzombie , y+40);
        bullets.add(puff);
    }

    @Override
    String gettype() {
        return "Puff";
    }


}
