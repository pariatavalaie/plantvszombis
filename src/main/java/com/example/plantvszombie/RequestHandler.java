package com.example.plantvszombie;

import java.io.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class RequestHandler {
    private ObjectOutputStream out;

    public RequestHandler(ObjectOutputStream out) {
        this.out = out;
        startBroadcasting();
    }

    public void startBroadcasting() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            try {
                GameState currentState = SaveManger.buildGameState(GameServer.yard);
                out.reset(); // جلوگیری از کش serialization
                out.writeObject(currentState);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
