package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Scaredy extends Shooter{
    public boolean scared;
    public Scaredy(int x,int y) {
       super(x,y);
        this.setWatingtime(2);
        this.setDayPlanet(false);
        this.setHealth(3);
        this.setImage(new ImageView( new Image(getClass().getResource("/Scaredy-shroom.png").toExternalForm())));
        this.setEatimage(new ImageView( new Image(getClass().getResource("/Scaredy-Shroom_Hiding.png").toExternalForm())));
        scared=false;
    }
    @Override
    public void act(Pane root , ArrayList<Zombies> zombies) {
        setActive(true);
        double x = Yard.GRID_X + getCol() * Yard.CELL_WIDTH+ (Yard.CELL_WIDTH - 70) / 2;
        double y = Yard.GRID_Y + getRow()* Yard.Cell_HEIGHT + (Yard.Cell_HEIGHT - 90) / 2;
        final double[] XZ = {0};
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            boolean shouldShoot = false;
            for (Zombies z : zombies) {
                double zombieX = z.image.getLayoutX() + z.image.getTranslateX();
                if (z.y == getRow() && z.x - getCol() <= 2 && z.x <= 8 && zombieX > x) {
                    scared = true;
                    this.getImage().setImage(this.getEatimage().getImage());
                } else if (!scared && z.y == getRow() && z.x > getCol() && z.x <= 8) {
                    shouldShoot = true;
                    XZ[0] = zombieX;
                    break;
                }
            }

            if (shouldShoot && !isDead()) {
                shoot(root,x,XZ[0],y);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        AnimationManager.register(timeline);
    }

    @Override
    void shoot(Pane root, double x, double xzombie, double y) {
        Bullet scary = new Bullet(getCol(), getRow(), 3, "MUSHROOM");
        scary.shoot(root, x + 60,xzombie, y + 20);
        bullets.add(scary);
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
                scaredValue,
                isActive()
        );
    }
    @Override
    public void loadpplanet(PlanetState planetState, Pane root) {
        super.loadpplanet(planetState, root);
        this.scared=((scardyState)planetState).scardy;
    }
}
