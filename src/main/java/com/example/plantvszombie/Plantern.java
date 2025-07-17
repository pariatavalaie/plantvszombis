package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Plantern extends Planet implements specialAct{
    private Fog fog;
    private Timeline lightUpdater;
    private Circle currentHole;

    public Plantern(int x, int y, Fog fog) {
        super(x,y);
        this.fog = fog;
        this.setWatingtime(3);
        this.setHealth(4);
        this.setDayPlanet(false);
        this.setImage(new ImageView(new Image(getClass().getResource("/Animated_Plantern.gif").toExternalForm())));
        this.setEatimage(new ImageView(new Image(getClass().getResource("/Animated_Plantern.gif").toExternalForm())));
    }

    @Override
   public void act(Pane root) {
        setActive(true);
        if (!isDead() && lightUpdater == null) {
            lightUpdater = new Timeline(new KeyFrame(Duration.seconds(0.3), e -> updateLight()));
            lightUpdater.setCycleCount(Timeline.INDEFINITE);
            lightUpdater.play();
            AnimationManager.register(lightUpdater);
        }

    }

    private void updateLight() {
        if (currentHole != null) {
            fog.removeLanternHole(currentHole);
        }

        double sceneX = getImage().localToScene(0, 0).getX();
        double sceneY = getImage().localToScene(0, 0).getY();
        double fogX = fog.getFogRect().localToScene(0, 0).getX();
        double fogY = fog.getFogRect().localToScene(0, 0).getY();
        double centerX = sceneX - fogX + getImage().getBoundsInLocal().getWidth() / 2;
        double centerY = sceneY - fogY + getImage().getBoundsInLocal().getHeight() / 2;

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

    @Override
    String gettype() {
        return "plantern";
    }
}
