package com.example.plantvszombie;

import javafx.application.Application;

import javafx.scene.Scene;

import javafx.scene.image.Image;

import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Yard yard = new Yard();
        Sun.fall(yard.yardPane);
        Scene scene=new Scene(yard.yardPane,1024,626);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResource("/PvZ_android.png").toExternalForm()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}