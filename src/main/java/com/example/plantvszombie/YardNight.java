package com.example.plantvszombie;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class YardNight {
    AnchorPane yardNight;
    GridPane yardNightGrid;
    Button HypnoB;
    Button PuffB;
    Button ScaredyB;
    Button DoomB;
    Button IceB;
    Button ShovelB = new Button();
    Label sunPoint = new Label();
    ArrayList<Zombies> Zombies = new ArrayList<>();

    YardNight() {
        yardNightGrid = new GridPane();
        Image yar = new Image(getClass().getResourceAsStream("Night.webp"));
        ImageView yard = new ImageView(yar);
        yardNight = new AnchorPane(yard);
        ButtonPick();
    }

    public void ButtonPick() {
        VBox vbox = new VBox();
        Image Hypo = new Image(getClass().getResource("/HypnoShroomSeed.png").toExternalForm());
        ImageView view1 = new ImageView(Hypo);
        view1.setFitHeight(64);
        view1.setFitWidth(105);
        HypnoB.setGraphic(view1);
        HypnoB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(HypnoB);
        Image Puff = new Image(getClass().getResource("/PuffShroomSeed.png").toExternalForm());
        ImageView view2 = new ImageView(Puff);
        view2.setFitHeight(64);
        view2.setFitWidth(105);
        PuffB.setGraphic(view2);
        PuffB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(PuffB);
        Image Scared = new Image(getClass().getResource("/ScaredyShroomSeed.png").toExternalForm());
        ImageView view3 = new ImageView(Scared);
        view3.setFitHeight(64);
        view3.setFitWidth(105);
        ScaredyB.setGraphic(view3);
        ScaredyB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(ScaredyB);
        Image Doom = new Image(getClass().getResource("/DoomShroomSeed.png").toExternalForm());
        ImageView view4 = new ImageView(Doom);
        view4.setFitHeight(64);
        view4.setFitWidth(105);
        DoomB.setGraphic(view4);
        DoomB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(DoomB);
        Image Ice = new Image(getClass().getResource("/IceShroomSeed.webp").toExternalForm());
        ImageView view5 = new ImageView(Ice);
        view5.setFitHeight(64);
        view5.setFitWidth(105);
        IceB.setGraphic(view5);
        IceB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(IceB);
        ImageView view6 =new ImageView(new Image(getClass().getResource("/Shovel.jpg").toExternalForm()));
        view6.setFitHeight(64);
        view6.setFitWidth(105);
        ShovelB.setGraphic(view6);
        ShovelB.setStyle("-fx-background-color: #fff");
        HBox hbox = new HBox();
        hbox.getChildren().add(vbox);
        hbox.getChildren().add(ShovelB);
        yardNight.getChildren().add(hbox);
    }
}