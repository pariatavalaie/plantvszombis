package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Hypnoshroom extends Plant implements Act {

    public Hypnoshroom(int x, int y) {
        super(x, y);
        this.setWaitingTime(5);
        this.setDayPlanet(false);
        this.setHealth(3);
        this.setImage(new ImageView(new Image(getClass().getResource("/Animated_HypnoShroom.gif").toExternalForm())));
        this.setEatimage(new ImageView(new Image(getClass().getResource("/HypnoShroomSleep.gif").toExternalForm())));
    }

    @Override
    public void act(Pane root, ArrayList<Zombies> Zombies) {
        setActive(true);
        boolean[] firsttime = {true};
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            for (Zombies z : Zombies) {
                if (z.getX() == getCol() && z.getY() == getRow() && this.isDead() == true && this.isActive() == true && firsttime[0]) {
                    z.setHypnotized(true);
                    z.reverseDirection();
                    firsttime[0] = false;
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        AnimationManager.register(timeline);
    }

    @Override
    String gettype() {
        return "Hypno";
    }

    @Override
    public PlanetState getState() {
        PlanetState baseState = super.getState();
        boolean scaredValue = this.isActive();

        return new OtherPlanetState(
                baseState.getCol(),
                baseState.getRow(),
                baseState.getType(),
                baseState.getHealth(),
                baseState.isDead(),
                baseState.getRemainingCooldown(),
                scaredValue,
                isActive()
        );
    }

    @Override
    public void loadplanet(PlanetState planetState, Pane root) {
        super.loadplanet(planetState, root);
        this.setActive(( (OtherPlanetState) planetState ).isOther());
    }
}
