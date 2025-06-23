package com.example.plantvszombie;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    public Stage stage;
    public Menu menu = new Menu();
    SaveManger saveManger = new SaveManger();

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
        pane.getChildren().add(menu.Loadgame);
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
        menu.Loadgame.setOnAction(e -> {
            Yard yard1= saveManger.loadGame("save.dat");
            yard1.updateButtons();
            Scene scene1 = new Scene(yard1.yardPane, 1024, 626);
            System.out.println(ZombieWaveManger.gameTime);
            stage.setScene(scene1);
            stage.setResizable(false);
            stage.show();
        });

    }

    private void play() {
        Yard yard = new Yard(menu.getSelectedPlantsNames(), menu.day);
        ImageView pause=new ImageView(new Image(getClass().getResource("/pause.png").toExternalForm()));
        pause.setFitHeight(50);
        pause.setFitWidth(50);
        Button pauseButton = new Button();
        pauseButton.setLayoutX(285);
        pauseButton.setLayoutY(10);
        pauseButton.setGraphic(pause);
        pauseButton.setShape(new Circle());
        pauseButton.setOnAction(e -> {
                    showPauseMenu(yard.yardPane,yard);
                    AnimationManager.pauseAll();
                }
        );
        yard.yardPane.getChildren().add(pauseButton);


        if(menu.day){
        Sun.fall(yard.yardPane);}
        ZombieWaveManger zw = new ZombieWaveManger(yard);
        zw.start();
        yard.updateButtons();
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
        pane.getChildren().add(menu.Back);
        Scene menuScene = new Scene(pane, 1024, 626);
        stage.setScene(menuScene);
        stage.show();

        menu.Play.setOnAction(e -> {
            System.out.println(menu.countPlant);
            if(menu.countPlant == 6){
                play();
            }else{
                Alert eror = new Alert(Alert.AlertType.WARNING);
                eror.setHeaderText("\"You have to choose 6 planets\"");
                eror.showAndWait();
            }
        });
        menu.Back.setOnAction(e -> {
            menu2();
            menu=new Menu();
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
        pane.getChildren().add(menu.Back);
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
        menu.Back.setOnAction(e -> {
            try {
                start(stage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
    private void showPauseMenu(Pane root,Yard yard) {

        Rectangle overlay = new Rectangle(1000, 600, Color.rgb(0, 0, 0, 0.5));

        VBox pauseMenu = new VBox(20);
        pauseMenu.setAlignment(Pos.CENTER);
        pauseMenu.setLayoutX(300);
        pauseMenu.setLayoutY(150);
        pauseMenu.setSpacing(10);

        Button resumeButton = new Button("▶ resume");
        Button saveButton = new Button("💾 save");
        Button exitButton = new Button("❌ exit");

        pauseMenu.getChildren().addAll(resumeButton, saveButton, exitButton);

       StackPane pauseGroup = new StackPane(overlay, pauseMenu);
        root.getChildren().add(pauseGroup);


        resumeButton.setOnAction(e -> {
            root.getChildren().remove(pauseGroup);
            AnimationManager.resumeAll();
        });

        saveButton.setOnAction(e -> {
            saveManger.saveGame(yard,"save.dat");
            root.getChildren().remove(pauseGroup);
            AnimationManager.resumeAll();
        });


        exitButton.setOnAction(e -> {
            Platform.exit();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}

