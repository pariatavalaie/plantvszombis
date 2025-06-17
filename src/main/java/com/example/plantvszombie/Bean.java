package com.example.plantvszombie;

import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Bean extends Planet{
    static boolean canplace = true;
    static int cost = 75;
    public Bean(int x, int y) {
        this.row = y;
        this.col = x;
        this.watingtime = 2;
        this.health = 4;
        this.dayplanet=true;
        this.image = new ImageView(new Image(getClass().getResource("/CoffeeBean2.gif").toExternalForm()));
        this.eatimage=new ImageView(new Image(getClass().getResource("/CoffeeBean2.gif").toExternalForm()));
        bullets = new ArrayList<Bullet>();
    }

    @Override
    void act(Pane root, ArrayList<Zombies> Zombies) {}

    @Override
    void act(Pane root) {}
    public void cooldown( Button b){
        cooldown = new PauseTransition(Duration.seconds(watingtime));
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
