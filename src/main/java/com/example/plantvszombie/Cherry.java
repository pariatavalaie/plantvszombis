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

public class Cherry extends Planet {
    static boolean canplace = true;
    static int cost = 150;

    public Cherry(int x, int y) {
        this.row = y;
        this.col = x;
        this.watingtime = 2;
        this.health = 4;
        this.dayplanet=true;
        this.image = new ImageView(new Image(getClass().getResource("/newCherryBomb.gif").toExternalForm()));
        this.eatimage=new ImageView(new Image(getClass().getResource("/newCherryBomb.gif").toExternalForm()));
        bullets = new ArrayList<Bullet>();
    }


    @Override
    void act(Pane root, ArrayList<Zombies> Zombies) {
        double gridX = 245.0;
        double gridY = 60.0;
        double cellWidth = 80.0;
        double cellHeight = 100.0;

        double cherryX = gridX + col * cellWidth + (cellWidth - 70) / 2;
        double cherryY = gridY + row * cellHeight + (cellHeight - 90) / 2;

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            for (Zombies z : Zombies) {
                double zombieX = z.image.getLayoutX() + z.image.getTranslateX();
                double zombieY = z.image.getLayoutY() + z.image.getTranslateY();

                // محاسبه فاصله بین زامبی و Cherry
                double distanceX = Math.abs(zombieX - cherryX);
                double distanceY = Math.abs(zombieY - cherryY);

                // بررسی اینکه آیا زامبی در محدوده 3x3 قرار دارد یا خیر
                // باید مطمئن بشیم که زامبی حداکثر یک خونه با Cherry فاصله داره
                if (distanceX <= cellWidth / 2 * 3 && distanceY <= cellHeight / 2 * 3) {
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(ev-> {
                        z.hp = 0;
                    });
                    pause.play();
                    z.image.setImage(z.deadZombie.getImage());
                }
            } }));
        AnimationManager.register(timeline);

         timeline.setCycleCount(1);
        timeline.play();
    }


    @Override
    void act(Pane root) {

    }


    @Override
    String gettype() {
        return "Cherry Bomb";
    }
}

