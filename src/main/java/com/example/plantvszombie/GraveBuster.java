package com.example.plantvszombie;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GraveBuster extends Planet implements specialAct{

    public GraveBuster(int x,int y) {
        super(x,y);
        this.setWatingtime(3);
        this.setDayPlanet(false);
        this.setHealth(4);
        this.setImage(new ImageView( new Image(getClass().getResource("/Transparent_grave_digger.gif").toExternalForm())));
        this.setEatimage(new ImageView( new Image(getClass().getResource("/Transparent_grave_digger.gif").toExternalForm())));
    }
    @Override
    public void act(Pane root) {
        setActive(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            remove(root);
        });
        pause.play();
        AnimationManager.register(pause);
    }

    @Override
    String gettype() {
        return "Grave";
    }
}
