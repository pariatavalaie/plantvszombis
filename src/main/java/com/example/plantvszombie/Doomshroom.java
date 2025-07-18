package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class Doomshroom extends Planet implements Act{

    public Doomshroom(int x,int y) {
        super(x,y);
        this.setHealth(4);
        this.setWaitingTime(5);
        this.setDayPlanet(false);
        setImage(new ImageView(new Image(getClass().getResource("/DoomShroom1.gif").toExternalForm())));
        setEatimage(new ImageView(new Image(getClass().getResource("/DoomShroom3.gif").toExternalForm())));
    }

    @Override
    public void act(Pane root, ArrayList<Zombies> Zombies) {
        setActive(true);

        double cherryX = Yard.GRID_X + getCol()* Yard.CELL_WIDTH + (Yard.CELL_WIDTH- 70) / 2;
        double cherryY = Yard.GRID_Y + getRow()* Yard.Cell_HEIGHT + (Yard.Cell_HEIGHT - 90) / 2;

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            for (Zombies z : Zombies) {
                double zombieX = z.getImage().getLayoutX() + z.getImage().getTranslateX();
                double zombieY = z.getImage().getLayoutY() + z.getImage().getTranslateY();
                double distanceX = Math.abs(zombieX - cherryX);
                double distanceY = Math.abs(zombieY - cherryY);


                if (distanceX <= Yard.CELL_WIDTH/ 2 * 4 && distanceY <= Yard.Cell_HEIGHT / 2 * 4) {
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(ev-> {
                        z.setHp(0);
                    });
                    pause.play();
                    z.getImage().setImage(z.getDeadZombie().getImage());
                }
            } }));
        AnimationManager.register(timeline);

        timeline.setCycleCount(1);
        timeline.play();
    }

    @Override
    String gettype() {
        return "Doom";
    }
}
