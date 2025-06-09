package com.example.plantvszombie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Yard yard = new Yard();
        Sun.fall(yard.yardPane);
        System.out.println(Sun.collectedpoint);
        NormalZombie A=new NormalZombie(9,4,yard.yardPane);
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