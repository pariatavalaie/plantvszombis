package com.example.plantvszombie;

import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class TallNut extends Planet{
    static boolean canplace=true;
    static final int cost=125;
    public TallNut(int x,int y) {
        this.row=y;
        this.col=x;
        health=16;
        watingtime=7;
        bullets=new ArrayList<>();
        this.eatimage=new ImageView(new Image(getClass().getResource("/Tallnut3.gif").toExternalForm()));
        this.image=new ImageView(new Image(getClass().getResource("/TallNut1.gif").toExternalForm()));

    }

    @Override
    void act(Pane root) {

    }

    @Override
    void act(Pane root, ArrayList<Zombies> Zombies) {

    }
    public void cooldown( Button b){
        PauseTransition cooldown = new PauseTransition(Duration.seconds(watingtime));
        cooldown.setOnFinished(ev -> {
            canplace= true;
            if(cost<=Sun.collectedpoint){
                b.setDisable(false);
                b.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
                System.out.println("✅ You can place another Sunflower now");}
        });
        cooldown.play();
    }
}
