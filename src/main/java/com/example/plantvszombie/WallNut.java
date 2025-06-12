package com.example.plantvszombie;

import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class WallNut extends Planet{
    static boolean canplace=true;
    public WallNut(int x,int y){
        this.row=y;
        this.col=x;
        this.cost=50;
        this.watingtime=5;
        this.health=10;
        this.bullets=new ArrayList<>();
        this.eatimage=new ImageView(new Image(getClass().getResource("/walnut_half_life.gif").toExternalForm()));
        this.image=new ImageView(new Image(getClass().getResource("/walnut_full_life.gif").toExternalForm()));
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
            b.setDisable(false);
            b.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
            System.out.println("✅ You can place another Sunflower now");
        });
        cooldown.play();
    }
}
