package com.example.plantvszombie;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public Stage stage;
    public Menu menu = new Menu();
    SaveManger saveManger = new SaveManger();
    boolean isMultiplayer = false;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.setTitle("Plant Vs Zombie");
        stage.setResizable(false);
        Image image = new Image(getClass().getResource("/firstpage.png").toExternalForm());
        ImageView yard = new ImageView(image);
        yard.setFitHeight(626);
        yard.setFitWidth(1024);
        Pane pane = new Pane();
        pane.getChildren().addAll(yard, menu.Loadgame, menu.Exit, menu.StartGame);

        
        Button multiplayer = new Button("🌐 Multiplayer");
        multiplayer.setLayoutX(450);
        multiplayer.setLayoutY(450);
        pane.getChildren().add(multiplayer);

        Scene scene = new Scene(pane, 1024, 626);
        stage.setScene(scene);
        stage.show();

        menu.Exit.setOnAction(event -> stage.close());
        menu.StartGame.setOnAction(e -> menu2());
        menu.Loadgame.setOnAction(e -> {

            Yard yard1 = saveManger.loadGame("save.dat");
            pauseButton(yard1);
            yard1.updateButtons();
            if (yard1.day) Sun.fall(yard1.yardPane);
            Scene scene1 = new Scene(yard1.yardPane, 1024, 626);
            stage.setScene(scene1);
        });

        multiplayer.setOnAction(e -> showMultiplayerMenu());
    }

    private void showMultiplayerMenu() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Multiplayer Setup");

        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER_LEFT);

        Button serverBtn = new Button("👑 Host Game (Server)");
        Button clientBtn = new Button("🔌 Join Game (Client)");
        content.getChildren().addAll(serverBtn, clientBtn);

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        serverBtn.setOnAction(e -> {
            isMultiplayer = true;
            menu2();
            dialog.close();

        });

        clientBtn.setOnAction(e -> {
            isMultiplayer = true;
            TextInputDialog ipDialog = new TextInputDialog("localhost");
            ipDialog.setTitle("Enter Server IP");
            ipDialog.setHeaderText("Join as Client");
            ipDialog.setContentText("Server IP:");
            ipDialog.getEditor().requestFocus(); // گرفتن فوکوس

            ipDialog.showAndWait().ifPresent(ip -> {
                System.out.println("Entered IP: " + ip); // فقط تست
                startMultiplayerGame(false, ip);
            });
        });

        dialog.showAndWait();
    }

    private void startMultiplayerGame(boolean isServer) {
        startMultiplayerGame(isServer, "localhost");
    }

    private void startMultiplayerGame(boolean isServer, String host) {
        if (isServer) {

            Yard yard = new Yard(menu.getSelectedPlantsNames(), menu.day);
            pauseButton(yard);
            yard.updateButtons();
            GameServer.yard = yard;

            Label waitingLabel = new Label("Waiting for client to join...");
            waitingLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
            StackPane loadingPane = new StackPane(waitingLabel);
            loadingPane.setStyle("-fx-background-color: black;");
            Scene loadingScene = new Scene(loadingPane, 1024, 626);
            stage.setScene(loadingScene);


            GameServer.start(yard, () -> {

                if (yard.day) Sun.fall(yard.yardPane);
                ZombieWaveManger zw = new ZombieWaveManger(yard);
                zw.start();

                Scene scene = new Scene(yard.yardPane, 1024, 626);
                stage.setScene(scene);
            });

        } else {
            try {
                GameClient client = new GameClient(host, 54321);
                Yard yard = client.clientYard;
                yard.updateButtons();

                Scene scene = new Scene(yard.yardPane, 1024, 626);
                stage.setScene(scene);
            } catch (IOException e) {
                showError("Cannot connect to server.");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private void play() {
        Yard yard = new Yard(menu.getSelectedPlantsNames(), menu.day);
        pauseButton(yard);
        if (menu.day) Sun.fall(yard.yardPane);
        ZombieWaveManger zw = new ZombieWaveManger(yard);
        zw.start();
        yard.updateButtons();
        Scene scene1 = new Scene(yard.yardPane, 1024, 626);
        stage.setScene(scene1);
    }

    private void play2() {
        Image yar = new Image(getClass().getResource("/choose level.png").toExternalForm());
        ImageView yard = new ImageView(yar);
        yard.setFitHeight(626);
        yard.setFitWidth(1024);
        Pane pane = new Pane(yard);
        VBox menuPane = menu.getMenuPane();
        menuPane.setLayoutY(50);
        menuPane.setLayoutX(190);
        pane.getChildren().addAll(menuPane, menu.Back);

        Scene menuScene = new Scene(pane, 1024, 626);
        stage.setScene(menuScene);

        menu.Play.setOnAction(e -> {
            if (menu.countPlant == 6&&!isMultiplayer) play();
            else if(isMultiplayer&&menu.countPlant == 6) startMultiplayerGame(true);
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("You have to choose 6 plants");
                alert.showAndWait();
            }
        });

        menu.Back.setOnAction(e -> {
            menu2();
            menu = new Menu();
        });
    }

    private void menu2() {
        Image yar = new Image(getClass().getResource("/WhatsApp Image 2025-06-29 at 03.49.35.jpeg").toExternalForm());
        ImageView yard = new ImageView(yar);
        yard.setFitHeight(626);
        yard.setFitWidth(1024);
        Pane pane = new Pane(yard);
        HBox menuPane = new HBox(menu.Day,menu.Night);
        menuPane.setLayoutX(300);
        menuPane.setLayoutY(201);
        pane.getChildren().addAll(menuPane ,menu.Back);
        Scene menuScene = new Scene(pane, 1024, 626);
        stage.setScene(menuScene);

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

    private void showPauseMenu(Yard yard) {
        Rectangle overlay = new Rectangle(1000, 600, Color.rgb(0, 0, 0, 0.5));
        VBox pauseMenu = new VBox(20);
        pauseMenu.setAlignment(Pos.CENTER);
        pauseMenu.setLayoutX(300);
        pauseMenu.setLayoutY(150);

        Button resumeButton = new Button("▶ resume");
        Button saveButton = new Button("💾 save");
        Button exitButton = new Button("❌ exit");

        pauseMenu.getChildren().addAll(resumeButton, saveButton, exitButton);
        StackPane pauseGroup = new StackPane(overlay, pauseMenu);
        yard.yardPane.getChildren().add(pauseGroup);

        resumeButton.setOnAction(e -> {
            yard.yardPane.getChildren().remove(pauseGroup);
            AnimationManager.resumeAll();
        });

        saveButton.setOnAction(e -> {
            saveManger.saveGame(yard, "save.dat");
            yard.yardPane.getChildren().remove(pauseGroup);
            AnimationManager.resumeAll();
        });

        exitButton.setOnAction(e -> Platform.exit());
    }

    private void pauseButton(Yard yard) {
        ImageView pause = new ImageView(new Image(getClass().getResource("/pause.png").toExternalForm()));
        pause.setFitHeight(50);
        pause.setFitWidth(50);
        Button pauseButton = new Button();
        pauseButton.setLayoutX(285);
        pauseButton.setLayoutY(10);
        pauseButton.setGraphic(pause);
        pauseButton.setShape(new Circle());
        pauseButton.setOnAction(e -> {
            showPauseMenu(yard);
            AnimationManager.pauseAll();
        });
        yard.yardPane.getChildren().add(pauseButton);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();

    }
}
