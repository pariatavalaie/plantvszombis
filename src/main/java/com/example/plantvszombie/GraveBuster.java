package com.example.plantvszombie;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GraveBuster extends Planet implements specialAct{

    public GraveBuster(int x,int y) {
        super(x,y);
        this.watingtime = 3;
        this.dayPlanet =false;
        this.health=4;
        this.image = new ImageView( new Image(getClass().getResource("/Transparent_grave_digger.gif").toExternalForm()));
        this.eatimage=new ImageView( new Image(getClass().getResource("/Transparent_grave_digger.gif").toExternalForm()));
    }
    @Override
    public void act(Pane root) {
        active=true;
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
