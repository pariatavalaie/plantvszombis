package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Hypnoshroom extends Planet implements Act{
    boolean active=false;
    static boolean canplace = true;
    static final int cost=75;
    public Hypnoshroom(int x,int y) {
        this.col = x;
        this.row = y;
        this.watingtime = 2;
        this.dayplanet=false;
        this.health=3;
        this.image = new ImageView( new Image(getClass().getResource("/Animated_HypnoShroom.gif").toExternalForm()));
        this.eatimage=new ImageView( new Image(getClass().getResource("/HypnoShroomSleep.gif").toExternalForm()));
        bullets = new ArrayList<>();
    }

    @Override
    public void act(Pane root, ArrayList<Zombies> Zombies) {
        boolean[] firsttime={true};
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100),event -> {
        for (Zombies z : Zombies) {
            if(z.x==col&&z.y==row&&this.dead==true&&this.active==true&&firsttime[0]){
                z.isHypnotized=true;
                z.reverseDirection();
                firsttime[0]=false;
            }
        }}));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        AnimationManager.register(timeline);
    }

    @Override
    String gettype() {
        return"Hypno";
    }

    @Override
    public PlanetState getState() {
        PlanetState baseState = super.getState();
        boolean scaredValue = this.active;

        return new OtherPlanetState(
                baseState.col,
                baseState.row,
                baseState.type,
                baseState.health,
                baseState.dead,
                baseState.bulletStates,
                baseState.remainingCooldown,
                scaredValue
        );
    }

    @Override
    public void loadpplanet(PlanetState planetState, Pane root) {
        super.loadpplanet(planetState, root);
        this.active=((OtherPlanetState)planetState).isOther();
    }
}
