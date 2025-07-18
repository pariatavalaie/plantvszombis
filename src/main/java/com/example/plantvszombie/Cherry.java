package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.ArrayList;

public class Cherry extends Planet implements Act {

    public Cherry(int x, int y) {
        super(x,y);
        this.setWaitingTime(2);
        this.setHealth(4);
        this.setDayPlanet(true);
        this.setImage(new ImageView(new Image(getClass().getResource("/newCherryBomb.gif").toExternalForm())));
        this.setEatimage(new ImageView(new Image(getClass().getResource("/newCherryBomb.gif").toExternalForm())));
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

                if (distanceX <= Yard.CELL_WIDTH / 2 * 3 && distanceY <= Yard.Cell_HEIGHT / 2 * 3) {
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

        Timeline timelineDelete = new Timeline(
                new KeyFrame(Duration.seconds(2), e -> this.setDead(true))
        );
        AnimationManager.register(timelineDelete);
        timelineDelete.setCycleCount(1);
        timelineDelete.play();
    }

    @Override
    String gettype() {
        return "Cherry Bomb";
    }
}

