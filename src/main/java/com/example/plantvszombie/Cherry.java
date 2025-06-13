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

public class Cherry extends Planet{
    static boolean canplace = true;
    public Cherry(int x , int y) {
        this.cost = 200;
        this.row = y;
        this.col = x;
        this.watingtime = 2;
        this.image = new ImageView( new Image(getClass().getResource("/newCherryBomb.gif").toExternalForm()));
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
                if (distanceX <= cellWidth / 2 * 3 && distanceY <= cellHeight / 2 * 3){
                    //z.image = new ImageView( new Image(getClass().getResource("/burntZombie.gif").toExternalForm()));
                        z.hp = 0; // کشتن زامبی بعد از یک ثانیه
            }}
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }



    @Override
    void act(Pane root){

    }

    public void cooldown(Button b){
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

