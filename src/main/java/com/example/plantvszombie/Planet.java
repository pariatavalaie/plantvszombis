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
    private int waitingTime;
    private final int row;
    private final int col;
    private int health;
    private ImageView image;
    private ImageView eatImage;
    private boolean dead = false;
    private PauseTransition cooldown;
    private boolean dayPlanet;
    private boolean active=false;

    public Planet(int x, int y) {
        this.row = y;
        this.col = x;
    }

    public static Map<String, Boolean> canPlaceMap = new HashMap<>();
    public static  Map<String, Integer> costMap = new HashMap<>();

    static {
        int[] costs = {100, 200, 50, 50,125, 175, 150,125, 125,25,100, 75, 75, 75, 75, 0, 25,};
        String[] names={"Peashooter","Repeater","Sunflower", "Wall-nut","Tall-nut","Snow Pea","Cherry Bomb","jalapeno",
                "Doom", "plantern","blover", "bean", "Ice", "Hypno","Grave","Puff","Scaredy" };

        for (int i = 0; i < costs.length; i++) {
            canPlaceMap.put(names[i], true);
            costMap.put(names[i], costs[i]);
        }
    }

    public int getCol() {return col;}

    public int getRow() {return row;}

    public void remove(Pane root) {
        root.getChildren().remove(getImage());
        root.getChildren().remove(getEatimage());
        setDead(true);
    }

    abstract String gettype();

    public PlanetState getState() {
        double remaining = 0;
        if (getCooldown() != null) {
            remaining = getCooldown().getCurrentTime().toSeconds();
        }
        return new PlanetState(col, row, gettype(), getHealth(), isDead(), remaining, isActive());
    }

    public void loadplanet(PlanetState planetState, Pane root) {
        this.setDead(planetState.isDead());
        if (planetState.getRemainingCooldown() != 0) {
            if (getCooldown() == null) {
                setCooldown(new PauseTransition(Duration.seconds(getWaitingTime())));
            }
            getCooldown().setDuration(Duration.seconds(getWaitingTime()));
            getCooldown().jumpTo(Duration.seconds(planetState.getRemainingCooldown()));
            getCooldown().play();
        }
    }

    static void on() {
       for (String name : canPlaceMap.keySet()) {
           canPlaceMap.put(name, true);
       }
    }

    public void cooldown(Button b, int cost) {
        canPlaceMap.put(this.gettype(), false);
        setCooldown(new PauseTransition(Duration.seconds(this.getWaitingTime())));
        getCooldown().setOnFinished(ev -> {
            if (cost <= Sun.getCollectedpoint()) {
                Platform.runLater(() -> {
                    b.setDisable(false);
                    b.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
                    System.out.println("✅ You can place another Sunflower now");
                });
            }

            canPlaceMap.put(this.gettype(), true);
        });
        getCooldown().play();
        b.setDisable(true);
        b.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public ImageView getEatimage() {
        return eatImage;
    }

    public void setEatimage(ImageView eatimage) {
        this.eatImage = eatimage;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public PauseTransition getCooldown() {
        return cooldown;
    }

    public void setCooldown(PauseTransition cooldown) {
        this.cooldown = cooldown;
    }

    public boolean isDayPlanet() {
        return dayPlanet;
    }

    public void setDayPlanet(boolean dayPlanet) {
        this.dayPlanet = dayPlanet;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
 interface Act {
    void act(Pane root, ArrayList<Zombies> Zombies);
}
interface specialAct {
    void act(Pane root);
}
