package com.example.plantvszombie;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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


        Image image = new Image(getClass().getResource("/first page_11zon.png").toExternalForm());
        ImageView yard = new ImageView(image);
        yard.setFitHeight(626);
        yard.setFitWidth(1024);
        Pane pane = new Pane();
        Menu menu = new Menu();
        pane.getChildren().add(yard);
        pane.getChildren().add(menu.Exit);
        pane.getChildren().add(menu.StartGame);
        Scene scene = new Scene(pane, 1024, 626);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        menu.Exit.setOnAction(event -> {
            stage.close();
        });
        menu.StartGame.setOnAction(e -> {
           play2();
        });

    }

    private void play() {
        Yard yard = new Yard(menu);
        Sun.fall(yard.yardPane);
        ZombieWaveManger zw = new ZombieWaveManger(yard);
        zw.start();
        Scene scene1 = new Scene(yard.yardPane, 1024, 626);
        stage.setScene(scene1);
        stage.setResizable(false);
        stage.show();
    }
    private void play2(){
        menu = new Menu();
        Image yar = new Image(getClass().getResource("/Lawn.jpg").toExternalForm());
        ImageView yard = new ImageView(yar);
        yard.setFitHeight(626);
        yard.setFitWidth(1024);
        Pane pane = new Pane(yard);
        VBox menuPane = menu.getMenuPane();
        menuPane.setLayoutY(150);
        menuPane.setLayoutX(140);
        pane.getChildren().add(menuPane);
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

    public static void main(String[] args) {
        launch();
    }
}

