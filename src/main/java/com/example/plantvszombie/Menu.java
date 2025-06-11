package com.example.plantvszombie;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private final int MAX_PLANTS = 6;
    private List<Button> plantButtons;
    private List<String> selectedPlantsNames;
    private Label selectionCountLabel;
    public AnchorPane MenuPane;
    public String[] plantNames = {"Sunflower", "Peashooter", "Wall-nut", "Cherry Bomb",
            "Snow Pea", "Repeater", "Tall-nut", "jalapeno"};
    private boolean[] plantSelected;
    public Button Play;
    public int countPlant = 0;

    Menu() {
        plantButtons = new ArrayList<>();
        MenuPane = new AnchorPane();
        selectedPlantsNames = new ArrayList<>();
        selectionCountLabel = new Label();
        plantSelected = new boolean[8];
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
            if (name.equals("Sunflower")) {
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
                updateSelectionCountLabel();
            });

            if (count < 4) {
                menu1.getChildren().add(button);
            } else {
                menu2.getChildren().add(button);
            }
            count++;
        }


        menuContainer.getChildren().addAll(menu1, menu2, Play);
        return menuContainer;
    }

    private void updateSelectionCountLabel() {
        selectionCountLabel.setText("Selected: " + selectedPlantsNames.size() + "/" + MAX_PLANTS);
    }

    public List getSelectedPlantsNames() {
        return selectedPlantsNames;
    }
}
