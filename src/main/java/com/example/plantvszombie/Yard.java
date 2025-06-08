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
        ImageView view1 = new ImageView(Snowpea);
        view1.setFitHeight(64);
        view1.setFitWidth(105);
        SnowpeaB.setGraphic(view1);
        SnowpeaB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(SnowpeaB);
        Image Peashooter = new Image("C:\\Users\\XMART\\IdeaProjects\\plantvszombie\\src\\main\\resources\\com\\example\\plantvszombie\\peashooterCard.png");
        ImageView view2 = new ImageView(Peashooter);
        view2.setFitHeight(64);
        view2.setFitWidth(105);
        peashooterB.setGraphic(view2);
        peashooterB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(peashooterB);
        Image Reapeater = new Image("C:\\Users\\XMART\\IdeaProjects\\plantvszombie\\src\\main\\resources\\com\\example\\plantvszombie\\repeaterCard.png");
        ImageView view3 = new ImageView(Reapeater);
        view3.setFitHeight(64);
        view3.setFitWidth(105);
        reapeaterB.setGraphic(view3);
        reapeaterB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(reapeaterB);
        Image Tallnut = new Image("C:\\Users\\XMART\\IdeaProjects\\plantvszombie\\src\\main\\resources\\TallNut.png");
        ImageView view4 = new ImageView(Tallnut);
        view4.setFitHeight(64);
        view4.setFitWidth(105);
        TallnutB.setGraphic(view4);
        TallnutB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(TallnutB);
        Image Wallnut = new Image("C:\\Users\\XMART\\IdeaProjects\\plantvszombie\\src\\main\\resources\\TallNut.png");
        ImageView view5 = new ImageView(Wallnut);
        view5.setFitHeight(64);
        view5.setFitWidth(105);
        WallnutB.setGraphic(view5   );
        WallnutB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(WallnutB);
        Image Cherrybomb = new Image("C:\\Users\\XMART\\IdeaProjects\\plantvszombie\\src\\main\\resources\\com\\example\\plantvszombie\\cherrybombCard.png");
        ImageView view6 = new ImageView(Cherrybomb);
        view6.setFitHeight(64);
        view6.setFitWidth(105);
        cherrybombB.setGraphic(view6);
        cherrybombB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(cherrybombB);
        Image Jalapeno = new Image("C:\\Users\\XMART\\IdeaProjects\\plantvszombie\\src\\main\\resources\\com\\example\\plantvszombie\\jalapenoCard.png");
        ImageView view7 = new ImageView(Jalapeno);
        view7.setFitHeight(64);
        view7.setFitWidth(105);
        JalapenoB.setGraphic(view7);
        JalapenoB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(JalapenoB);
        Image Sunflower = new Image("C:\\Users\\XMART\\IdeaProjects\\plantvszombie\\src\\main\\resources\\com\\example\\plantvszombie\\sunflowerCard.png");
        ImageView view8 = new ImageView(Sunflower);
        view8.setFitHeight(64);
        view8.setFitWidth(105);
        SunflowerB.setGraphic(view8);
        SunflowerB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(SunflowerB);
        yardPane.getChildren().add(vbox);
    }


}
