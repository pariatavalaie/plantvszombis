package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;

public class Plantern extends Planet {
    static boolean canplace = true;
    static final int cost = 25;
    private Fog fog;
    private Timeline lightUpdater;
    private Circle currentHole;

    public Plantern(int x, int y, Fog fog) {
        this.row = y;
        this.col = x;
        this.fog = fog;
        this.watingtime = 3;
        this.health = 4;
        this.dayplanet=false;
        bullets = new ArrayList<>();
        this.image = new ImageView(new Image(getClass().getResource("/Animated_Plantern.gif").toExternalForm()));
        this.eatimage = new ImageView(new Image(getClass().getResource("/Animated_Plantern.gif").toExternalForm()));
    }

    @Override
    void act(Pane root, ArrayList<Zombies> Zombies) {}

    @Override
    void act(Pane root) {
        if (!dead && lightUpdater == null) {
            lightUpdater = new Timeline(new KeyFrame(Duration.seconds(0.3), e -> updateLight()));
            lightUpdater.setCycleCount(Timeline.INDEFINITE);
            lightUpdater.play();
            AnimationManager.register(lightUpdater);
        }

    }

    private void updateLight() {
        // ابتدا سوراخ قبلی رو حذف کن
        if (currentHole != null) {
            fog.removeLanternHole(currentHole);
        }

        // مکان دقیق سوراخ
        double sceneX = image.localToScene(0, 0).getX();
        double sceneY = image.localToScene(0, 0).getY();
        double fogX = fog.getFogRect().localToScene(0, 0).getX();
        double fogY = fog.getFogRect().localToScene(0, 0).getY();
        double centerX = sceneX - fogX + image.getBoundsInLocal().getWidth() / 2;
        double centerY = sceneY - fogY + image.getBoundsInLocal().getHeight() / 2;

        currentHole = fog.addLanternHole(centerX, centerY, 100);
    }

    @Override
    public void remove(Pane root) {
        super.remove(root);
        if (lightUpdater != null) {
            lightUpdater.stop();
        }
        if (currentHole != null) {
            fog.removeLanternHole(currentHole);
        }
    }
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
