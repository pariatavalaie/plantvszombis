package com.example.plantvszombie;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    public boolean day;
    private final int MAX_PLANTS = 6;
    private List<Button> plantButtons;
    private List<String> selectedPlantsNames;
    public AnchorPane MenuPane;
    public String[] plantNames = {"Sunflower", "Peashooter", "Wall-nut", "Cherry Bomb",
            "Snow Pea", "Repeater", "Tall-nut", "jalapeno", "Hypno", "Puff", "Scaredy", "Doom", "Ice", "bean", "Grave", "blover", "plantern"};
    private boolean[] plantSelected;
    public Button Play;
    public Button StartGame;
    public Button Exit;
    public Button Day;
    public Button Night;
    public Button Back;
    public int countPlant = 0;

    Menu() {
        plantButtons = new ArrayList<>();
        MenuPane = new AnchorPane();
        selectedPlantsNames = new ArrayList<>();
        plantSelected = new boolean[17];
        StartGame = new Button();
        Image image2 = new Image(getClass().getResource("/startgame.png").toExternalForm());
        ImageView imageView2 = new ImageView(image2);
        imageView2.setPreserveRatio(true);
        StartGame.setGraphic(imageView2);
        StartGame.setLayoutX(340);
        StartGame.setLayoutY(470);
        Exit = new Button();
        Image image1 = new Image(getClass().getResource("/existinguser.png").toExternalForm());
        ImageView imageView1 = new ImageView(image1);
        imageView1.setPreserveRatio(true);
        Exit.setGraphic(imageView1);
        Exit.setLayoutX(340);
        Exit.setLayoutY(550);
        Day = new Button();
        Image image3 = new Image(getClass().getResourceAsStream("Frontyard.png"));
        ImageView imageView3 = new ImageView(image3);
        imageView3.setFitHeight(64);
        imageView3.setFitWidth(105);
        imageView3.setPreserveRatio(true);
        Day.setGraphic(imageView3);
        Day.setLayoutX(340);
        Day.setLayoutY(60);
        Night = new Button();
        Image image4 = new Image(getClass().getResource("/Night_11zon.png").toExternalForm());
        ImageView imageView4 = new ImageView(image4);
        imageView4.setPreserveRatio(true);
        imageView4.setFitHeight(64);
        imageView4.setFitWidth(105);
        Night.setGraphic(imageView4);
        Night.setLayoutX(100);
        Night.setLayoutY(50);
        ImageView back = new ImageView(getClass().getResource("/back.png").toExternalForm());
        Back=new Button();
        Back.setShape( new Circle());
        Back.setGraphic(back);
        Back.setLayoutX(900);
        Back.setLayoutY(10);

    }

    public VBox getMenuPane() {
        VBox menuContainer = new VBox(2);
        menuContainer.setAlignment(Pos.CENTER);

        HBox menu1 = new HBox(10);
        menu1.setPadding(new Insets(20));
        menu1.setAlignment(Pos.CENTER);

        HBox menu2 = new HBox(10);
        menu2.setPadding(new Insets(20));
        menu2.setAlignment(Pos.CENTER);

        HBox menu3 = new HBox(10);
        menu3.setPadding(new Insets(20));
        menu3.setAlignment(Pos.CENTER);

        HBox menu4 = new HBox(10);
        menu4.setPadding(new Insets(20));
        menu4.setAlignment(Pos.CENTER);

        Play = new Button();
        Image image1 = new Image(getClass().getResource("/play.png").toExternalForm());
        ImageView imageView1 = new ImageView(image1);
        imageView1.setPreserveRatio(true);
        Play.setGraphic(imageView1);


        int count = 0;
        for (int i = 0; i < plantNames.length; i++) {
            String name = plantNames[i];
            Image image;
            ImageView view = null;
            if (day && (name.equals("Grave") || name.equals("plantern") || name.equals("blover"))) {
                continue;
            }
            if(!day && name.equals("bean")){
                continue;
            }
             else if (name.equals("Sunflower")) {
                image = new Image(getClass().getResource("/com/example/plantvszombie/sunflowerCard.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("Peashooter")) {
                image = new Image(getClass().getResource("/com/example/plantvszombie/peashooterCard.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("Wall-nut")) {
                image = new Image(getClass().getResource("/com/example/plantvszombie/wallnutCard.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("Snow Pea")) {
                image = new Image(getClass().getResource("/SnowPea.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("Repeater")) {
                image = new Image(getClass().getResource("/com/example/plantvszombie/repeaterCard.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("Tall-nut")) {
                image = new Image(getClass().getResource("/TallNut.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("jalapeno")) {
                image = new Image(getClass().getResource("/com/example/plantvszombie/jalapenoCard.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("Cherry Bomb")) {
                image = new Image(getClass().getResource("/com/example/plantvszombie/cherrybombCard.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("Hypno")) {
                image = new Image(getClass().getResource("/HypnoShroomSeed.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("Puff")) {
                image = new Image(getClass().getResource("/PuffShroomSeed.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("Scaredy")) {
                image = new Image(getClass().getResource("/ScaredyShroomSeed.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("Doom")) {
                image = new Image(getClass().getResource("/DoomShroomSeed.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("Ice")) {
                image = new Image(getClass().getResource("/IceShroomSeed.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("bean")) {
                image = new Image(getClass().getResource("/bean.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("Grave")) {
                image = new Image(getClass().getResource("/GraveBusterSeed.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("blover")) {
                image = new Image(getClass().getResource("/BloverSeed.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            } else if (name.equals("plantern")) {
                image = new Image(getClass().getResource("/PlanternSeed.png").toExternalForm());
                view = new ImageView(image);
                view.setFitHeight(64);
                view.setFitWidth(105);
            }

            Button button = new Button(name);
            button.setGraphic(view);
            button.setStyle("-fx-background-color: #fff");

            button.setPrefSize(100, 50);
            DropShadow shadow = new DropShadow();
            shadow.setRadius(10.0);
            shadow.setOffsetX(10.0);
            shadow.setOffsetY(10.0);

            plantButtons.add(button);




            final int index = i;

            button.setOnAction(e -> {
                if (plantSelected[index]) {
                    plantSelected[index] = false;
                    selectedPlantsNames.remove(name);
                    countPlant--;
                    button.setEffect(null);
                } else {
                    if (selectedPlantsNames.size() < MAX_PLANTS) {
                        plantSelected[index] = true;
                        selectedPlantsNames.add(name);
                        countPlant++;
                        button.setEffect(shadow);
                    } else {
                        System.out.println("You can only select up to " + MAX_PLANTS + " plants!");
                    }
                }
            });

            if (count < 4) {
                menu1.getChildren().add(button);
                if (name.equals("Grave") && day) {
                    menu1.getChildren().remove(button);
                } else if (name.equals("plantern") && day) {
                    menu1.getChildren().remove(button);
                } else if (name.equals("blover") && day) {
                    menu1.getChildren().remove(button);
                }
            } else if (count < 8) {
                menu2.getChildren().add(button);
                if (name.equals("Grave") && day) {
                    menu2.getChildren().remove(button);
                } else if (name.equals("plantern") && day) {
                    menu2.getChildren().remove(button);
                } else if (name.equals("blover") && day) {
                    menu2.getChildren().remove(button);
                }
            } else if (count < 12) {
                menu3.getChildren().add(button);
                if (name.equals("Grave") && day) {
                    menu3.getChildren().remove(button);
                } else if (name.equals("plantern") && day) {
                    menu3.getChildren().remove(button);
                } else if (name.equals("blover") && day) {
                    menu3.getChildren().remove(button);
                }
            } else {
                menu4.getChildren().add(button);
                if (name.equals("Grave") && day) {
                    menu4.getChildren().remove(button);
                } else if (name.equals("plantern") && day) {
                    menu4.getChildren().remove(button);
                } else if (name.equals("blover") && day) {
                    menu4.getChildren().remove(button);
                }
            }
            count++;
        }


        menuContainer.getChildren().addAll(menu1, menu2, menu3, menu4, Play);
        return menuContainer;
    }


    public List getSelectedPlantsNames() {
        return selectedPlantsNames;
    }

}
