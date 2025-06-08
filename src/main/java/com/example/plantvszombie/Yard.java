package com.example.plantvszombie;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Yard {
    AnchorPane yardPane;
    GridPane gridPane;
    final double GRID_X=80;
    final double GRID_Y=100;
    Button SnowpeaB=new Button();
    Button peashooterB=new Button();
    Button reapeaterB=new Button();
    Button TallnutB=new Button();
    Button WallnutB=new Button();
    Button cherrybombB=new Button();
    Button JalapenoB=new Button();
    Button SunflowerB=new Button();

    Yard() {
        Image yar = new Image(getClass().getResourceAsStream("Frontyard.png"));
        ImageView yard = new ImageView(yar);
        yardPane = new AnchorPane(yard);
        PaintGrid(9, 5);
        buttonpic();
    }

    public void PaintGrid(int x, int y) {
        gridPane = new GridPane();
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Rectangle rectangle = new Rectangle(GRID_X, GRID_Y);
                rectangle.setFill(null);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(0.5);
                gridPane.add(rectangle, j, i);
            }
        }
        yardPane.getChildren().add(gridPane);
        AnchorPane.setTopAnchor(gridPane, 60.0);
        AnchorPane.setLeftAnchor(gridPane, 245.0);


    }
    public void buttonpic(){
        VBox vbox = new VBox();
        Image Snowpea = new Image("C:\\Users\\XMART\\IdeaProjects\\plantvszombie\\src\\main\\resources\\SnowPea.png");
        SnowpeaB.setGraphic(new ImageView(Snowpea));
        SnowpeaB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(SnowpeaB);
        Image Peashooter = new Image("C:\\Users\\XMART\\IdeaProjects\\plantvszombie\\src\\main\\resources\\com\\example\\plantvszombie\\peashooterCard.png");
        ImageView view = new ImageView(Peashooter);
        view.setFitHeight(64);
        view.setFitWidth(105);
        peashooterB.setGraphic(view);
        peashooterB.setStyle("-fx-background-color: #fff");

        vbox.getChildren().add(peashooterB);
        Image Reapeater = new Image("C:\\Users\\XMART\\IdeaProjects\\plantvszombie\\src\\main\\resources\\com\\example\\plantvszombie\\repeaterCard.png");
        reapeaterB.setGraphic(new ImageView(Reapeater));
        reapeaterB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(reapeaterB);
        Image Tallnut = new Image("C:\\Users\\XMART\\IdeaProjects\\plantvszombie\\src\\main\\resources\\TallNut.png");
        TallnutB.setGraphic(new ImageView(Tallnut));
        TallnutB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(TallnutB);
        Image Wallnut = new Image("C:\\Users\\XMART\\IdeaProjects\\plantvszombie\\src\\main\\resources\\TallNut.png");
        WallnutB.setGraphic(new ImageView(Wallnut));
        WallnutB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(WallnutB);
        Image Cherrybomb = new Image("C:\\Users\\XMART\\IdeaProjects\\plantvszombie\\src\\main\\resources\\com\\example\\plantvszombie\\cherrybombCard.png");
        cherrybombB.setGraphic(new ImageView(Cherrybomb));
        cherrybombB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(cherrybombB);
        Image Jalapeno = new Image("C:\\Users\\XMART\\IdeaProjects\\plantvszombie\\src\\main\\resources\\com\\example\\plantvszombie\\jalapenoCard.png");
        JalapenoB.setGraphic(new ImageView(Jalapeno));
        JalapenoB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(JalapenoB);
        Image Sunflower = new Image("C:\\Users\\XMART\\IdeaProjects\\plantvszombie\\src\\main\\resources\\com\\example\\plantvszombie\\sunflowerCard.png");
        SunflowerB.setGraphic(new ImageView(Sunflower));
        SunflowerB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(SunflowerB);
        yardPane.getChildren().add(vbox);
    }


}
