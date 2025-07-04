package com.example.plantvszombie;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.ArrayList;


public abstract class Planet {
    int watingtime;
    int row;
    int col;
    int health;
    ImageView image;
    ImageView eatimage;
    boolean dead = false;
    PauseTransition cooldown;
    Boolean dayplanet;


    public void remove(Pane root) {
        root.getChildren().remove(image);
        root.getChildren().remove(eatimage);
        dead = true;
    }

    abstract String gettype();

    public PlanetState getState() {
        double remaining = 0;
        if (cooldown != null) {
            remaining = cooldown.getCurrentTime().toSeconds();
        }
        return new PlanetState(col, row, gettype(), health, dead, remaining);
    }

    public void loadpplanet(PlanetState planetState, Pane root) {
        this.dead = planetState.dead;
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
        Peashooter.canplace = true;Repeater.canplace = true;Scaredy.canplace = true;Sunflower.canplace = true;SnowPea.canplace = true;GraveBuster.canplace = true;Puff.canplace = true;Plantern.canplace = true;Blover.canplace = true;TallNut.canplace = true;WallNut.canplace = true;Jalapeno.canplace = true;Cherry.canplace = true;Iceshroom.canplace = true;Doomshroom.canplace = true;Bean.canplace = true;
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
        b.setDisable(true);
        b.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
    }

}
 interface Act {
    void act(Pane root, ArrayList<Zombies> Zombies);
}
interface specialAct {
    void act(Pane root);
}
