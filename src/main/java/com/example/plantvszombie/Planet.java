package com.example.plantvszombie;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class Planet {
    int watingtime;
    int row;
    int col;
    int health;
    ImageView image;
    ImageView eatimage;
    boolean dead = false;
    PauseTransition cooldown;
    boolean dayPlanet;
    boolean active=false;
    public static Map<String, Boolean> canPlaceMap = new HashMap<>();
     static  Map<String, Integer> costMap = new HashMap<>();
    static {
        int[] costs = {100, 200, 50, 50,125, 175, 150,125, 125,25,100, 75, 75, 75, 75, 0, 25,};
        String[] names={"Peashooter","Repeater","Sunflower", "Wall-nut","Tall-nut","Snow Pea","Cherry Bomb","jalapeno",
                "Doom", "plantern","blover", "bean", "Ice", "Hypno","Grave","Puff","Scaredy" };

        for (int i = 0; i < costs.length; i++) {
            canPlaceMap.put(names[i], true);
            costMap.put(names[i], costs[i]);
        }
    }


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
        return new PlanetState(col, row, gettype(), health, dead, remaining,active);
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
       for (String name : canPlaceMap.keySet()) {
           canPlaceMap.put(name, true);
       }
    }


    public void cooldown(Button b, int cost) {
        canPlaceMap.put(this.gettype(), false);
        cooldown = new PauseTransition(Duration.seconds(this.watingtime));
        cooldown.setOnFinished(ev -> {
            if (cost <= Sun.collectedpoint) {
                Platform.runLater(() -> {
                    b.setDisable(false);
                    b.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
                    System.out.println("✅ You can place another Sunflower now");
                });
            }

            canPlaceMap.put(this.gettype(), true);
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
