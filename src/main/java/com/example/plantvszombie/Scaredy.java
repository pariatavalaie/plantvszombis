package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Scaredy extends Shooter {
    public boolean scared;

    public Scaredy(int x, int y) {
        super(x, y);
        this.setWaitingTime(3);
        this.setDayPlanet(false);
        this.setHealth(3);
        this.setImage(new ImageView(new Image(getClass().getResource("/Scaredy-shroom.png").toExternalForm())));
        this.setEatimage(new ImageView(new Image(getClass().getResource("/Scaredy-Shroom_Hiding.png").toExternalForm())));
        scared = false;
    }

    @Override
    public void act(Pane root, ArrayList<Zombies> zombies) {
        setActive(true);
        double x = Yard.GRID_X + getCol() * Yard.CELL_WIDTH + ( Yard.CELL_WIDTH - 70 ) / 2;
        double y = Yard.GRID_Y + getRow() * Yard.Cell_HEIGHT + ( Yard.Cell_HEIGHT - 90 ) / 2;
        final double[] XZ = {0};
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            boolean shouldShoot = false;
            for (Zombies z : zombies) {
                double zombieX = z.getImage().getLayoutX() + z.getImage().getTranslateX();
                if (z.getY() == getRow() && z.getX() - getCol() <= 2 && z.getX() <= 8 && zombieX > x) {
                    scared = true;
                    this.getImage().setImage(this.getEatimage().getImage());
                } else if (!scared && z.getY() == getRow() && z.getX() > getCol() && z.getX() <= 8) {
                    shouldShoot = true;
                    XZ[0] = zombieX;
                    break;
                }
            }
            if (shouldShoot && !isDead()) {
                shoot(root, x, XZ[0], y);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        AnimationManager.register(timeline);
    }

    @Override
    void shoot(Pane root, double x, double xzombie, double y) {
        double speed = ( ( xzombie - x ) / Yard.Cell_HEIGHT ) * 0.5;
        Bullet scary = new Bullet(getCol(), getRow(), speed, "MUSHROOM");
        scary.shoot(root, x + 60, xzombie, y + 20);
        bullets.add(scary);
    }

    @Override
    String gettype() {
        return "Scaredy";
    }

    @Override
    public PlanetState getState() {
        PlanetState baseState = super.getState();
        boolean scaredValue = this.scared;

        return new scardyState(
                baseState.getCol(),
                baseState.getRow(),
                baseState.getType(),
                baseState.getHealth(),
                baseState.isDead(),
                ( (ShooterState) baseState ).getBulletStates(),
                baseState.getRemainingCooldown(),
                scaredValue,
                isActive()
        );
    }

    @Override
    public void loadplanet(PlanetState planetState, Pane root) {
        super.loadplanet(planetState, root);
        this.scared = ( (scardyState) planetState ).isScardy();
    }
}
