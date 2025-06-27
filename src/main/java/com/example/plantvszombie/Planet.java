package com.example.plantvszombie;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.example.plantvszombie.Sunflower.cost;

public abstract class Planet {
    int watingtime;
    int row;
    int col;
    int health;
    ImageView image;
    ImageView eatimage;
    ArrayList<Bullet> bullets;
    boolean dead = false;
    PauseTransition cooldown;
    Boolean dayplanet;

    //abstract void act(Pane root, ArrayList<Zombies> Zombies);

    //abstract void act(Pane root);

    public void remove(Pane root) {
        root.getChildren().remove(image);
        root.getChildren().remove(eatimage);
        dead = true;
    }

    ;

    abstract String gettype();

    public PlanetState getState() {
        double remaining = 0;
        if (cooldown != null) {
            remaining = cooldown.getCurrentTime().toSeconds();
        }
        ArrayList<BulletState> bulletStates = new ArrayList<>();
        for (Bullet b : bullets) {
            bulletStates.add(new BulletState(b.x, b.y, b.speed, b.type, b.imageBullet.getTranslateX() + b.imageBullet.getLayoutX(), b.imageBullet.getTranslateY() + b.imageBullet.getLayoutY(), b.xzombie, b.hit));
        }
        return new PlanetState(col, row, gettype(), health, dead, bulletStates, remaining);
    }

    public void loadpplanet(PlanetState planetState, Pane root) {
        this.dead = planetState.dead;
        for (BulletState bullet : planetState.bulletStates) {
            Bullet bullet1 = new Bullet(bullet.x, bullet.y, bullet.getSpeed(), bullet.getType());
            bullet1.shoot(root, bullet.getTranslateX(), bullet.getXzombie(), bullet.getTranslateY());
            bullets.add(bullet1);
        }
        if (planetState.remainingCooldown != 0) {
            if (cooldown == null) {
                cooldown = new PauseTransition(Duration.seconds(watingtime));
            }
            cooldown.setDuration(Duration.seconds(watingtime));
            cooldown.jumpTo(Duration.seconds(planetState.remainingCooldown));
            cooldown.play();
        }
    }

    static void on() {
        Peashooter.canplace = true;
        Repeater.canplace = true;
        Scaredy.canplace = true;
        Sunflower.canplace = true;
        SnowPea.canplace = true;
        GraveBuster.canplace = true;
        Puff.canplace = true;
        Plantern.canplace = true;
        Blover.canplace = true;
        TallNut.canplace = true;
        WallNut.canplace = true;
        Jalapeno.canplace = true;
        Cherry.canplace = true;
        Iceshroom.canplace = true;
        Doomshroom.canplace = true;
        Bean.canplace = true;
    }


    public void cooldown(Button b, Runnable setCanPlaceTrue, int cost) {

        cooldown = new PauseTransition(Duration.seconds(this.watingtime));
        cooldown.setOnFinished(ev -> {
            if (cost <= Sun.collectedpoint) {
                Platform.runLater(() -> {
                    b.setDisable(false);
                    b.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
                    System.out.println("✅ You can place another Sunflower now");
                });
            }

            setCanPlaceTrue.run();
        });
        cooldown.play();
    }
}
 interface Act {
    void act(Pane root, ArrayList<Zombies> Zombies);
}
interface specialAct {
    void act(Pane root);
}