package com.example.plantvszombie;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    public Stage stage;
    public Menu menu = new Menu();

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
           menu2();
        });

    }

    private void play() {
        Yard yard = new Yard(menu, menu.day);
        if(menu.day){
        Sun.fall(yard.yardPane);}
        ZombieWaveManger zw = new ZombieWaveManger(yard);
        zw.start();
        yard.Updatebutton();
        Scene scene1 = new Scene(yard.yardPane, 1024, 626);
        stage.setScene(scene1);
        stage.setResizable(false);
        stage.show();
    }
    private void play2() {
        Image yar = new Image(getClass().getResource("/choose level.png").toExternalForm());
        ImageView yard = new ImageView(yar);
        yard.setFitHeight(626);
        yard.setFitWidth(1024);
        Pane pane = new Pane(yard);
        VBox menuPane = menu.getMenuPane();
        System.out.println(menu.day);
        menuPane.setLayoutY(50);
        menuPane.setLayoutX(190);
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
    private void menu2(){
        Image yar = new Image(getClass().getResource("/Lawn.jpg").toExternalForm());
        ImageView yard = new ImageView(yar);
        yard.setFitHeight(626);
        yard.setFitWidth(1024);
        Pane pane = new Pane(yard);
        pane.getChildren().add(menu.Day);
        pane.getChildren().add(menu.Night);
        Scene menuScene = new Scene(pane, 1024, 626);
        stage.setScene(menuScene);
        stage.show();

        menu.Day.setOnAction(e -> {
            menu.day = true;
            play2();
        });
        menu.Night.setOnAction(e -> {
            menu.day = false;
            play2();
        });

    }

    public static void main(String[] args) {
        launch();
    }
}

