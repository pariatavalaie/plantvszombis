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

public class Doomshroom extends Planet{
    static boolean canplace = true;
    static int cost = 125;
    boolean explode = false;

    public Doomshroom(int x,int y) {
        this.row = y;
        this.col = x;
        this.health = 4;
        this.watingtime =5;
        bullets = new ArrayList<Bullet>();
        image=new ImageView(new Image(getClass().getResource("/DoomShroom1.gif").toExternalForm()));
        eatimage=new ImageView(new Image(getClass().getResource("/DoomShroom1.gif").toExternalForm()));
    }

    @Override
    void act(Pane root) {}

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
                if (distanceX <= cellWidth / 2 * 4 && distanceY <= cellHeight / 2 * 4) {
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(ev-> {
                        z.hp = 0;
                    });
                    pause.play();
                    z.image.setImage(z.deadZombie.getImage());
                }
            } }));

        timeline.setCycleCount(1);
        timeline.play();
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
