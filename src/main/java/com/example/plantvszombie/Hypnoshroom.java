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

public class Hypnoshroom extends Planet{
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
    void act(Pane root) {
    }

    @Override
    void act(Pane root, ArrayList<Zombies> Zombies) {
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
    public void cooldown(Button b){
        cooldown = new PauseTransition(Duration.seconds(watingtime));
        cooldown.setOnFinished(ev -> {
            canplace= true;
            if(Sun.collectedpoint>=cost){
                b.setDisable(false);
                b.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
                System.out.println("✅ You can place another Sunflower now");}
        });
        cooldown.play();
    }
}
