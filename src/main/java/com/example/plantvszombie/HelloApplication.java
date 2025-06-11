package com.example.plantvszombie;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloApplication extends Application {
    public Stage stage;
    public Menu menu;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.setTitle("Plant Vs Zombie");
        stage.setResizable(false);

        menu = new Menu();
        Image yar = new Image(getClass().getResourceAsStream("Frontyard.png"));
        ImageView yard = new ImageView(yar);
        Pane pane = new Pane(yard);
        pane.getChildren().add(menu.getMenuPane());
        Scene menuScene = new Scene(pane, 1024, 626);
        stage.setScene(menuScene);
        stage.show();

        menu.Play.setOnAction(e -> {
            System.out.println(menu.countPlant);
            if(menu.countPlant == 6){
                play();
            }else{
                System.out.println("You have to choose 6 planets");
            }
        });
    }

    private void play() {
        Yard yard = new Yard(menu);
        Sun.fall(yard.yardPane);
        Scene scene1 = new Scene(yard.yardPane, 1024, 626);
        stage.setScene(scene1);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

