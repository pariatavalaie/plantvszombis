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

import java.util.ArrayList;

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
    ArrayList<Planet>planets=new ArrayList<>();

    Yard() {
        Image yar = new Image(getClass().getResourceAsStream("Frontyard.png"));
        ImageView yard = new ImageView(yar);
        yardPane = new AnchorPane(yard);
        PaintGrid(9, 5);
        buttonpic();
    }

    public void PaintGrid(int x, int y) {
        gridPane = new GridPane();
        final String []selected ={""};
        SunflowerB.setOnAction(event -> {
            selected[0]="sunflower";
            System.out.println(selected[0]);
        });
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Rectangle rectangle = new Rectangle(GRID_X, GRID_Y);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(0.5);
                int row = i;
                int col = j;
                gridPane.add(rectangle, j, i);
                rectangle.setOnMouseClicked(event -> {
                    System.out.println("H");
                    if ("sunflower".equals(selected[0])) {
                        System.out.println(row);
                        placeplanet("sunflower",col,row);
                        selected[0] = null;

                    }
                });
            }
        }
        yardPane.getChildren().add(gridPane);
        AnchorPane.setTopAnchor(gridPane, 60.0);
        AnchorPane.setLeftAnchor(gridPane, 245.0);


    }
    public void buttonpic(){
        VBox vbox = new VBox();
        Image Snowpea = new Image(getClass().getResource("/SnowPea.png").toExternalForm());
        ImageView view1 = new ImageView(Snowpea);
        view1.setFitHeight(64);
        view1.setFitWidth(105);
        SnowpeaB.setGraphic(view1);
        SnowpeaB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(SnowpeaB);
        Image Peashooter = new Image(getClass().getResource("/com/example/plantvszombie/peashooterCard.png").toExternalForm());
        ImageView view2 = new ImageView(Peashooter);
        view2.setFitHeight(64);
        view2.setFitWidth(105);
        peashooterB.setGraphic(view2);
        peashooterB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(peashooterB);
        Image Reapeater = new Image(getClass().getResource("/com/example/plantvszombie/repeaterCard.png").toExternalForm());
        ImageView view3 = new ImageView(Reapeater);
        view3.setFitHeight(64);
        view3.setFitWidth(105);
        reapeaterB.setGraphic(view3);
        reapeaterB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(reapeaterB);
        Image Tallnut = new Image(getClass().getResource("/TallNut.png").toExternalForm());
        ImageView view4 = new ImageView(Tallnut);
        view4.setFitHeight(64);
        view4.setFitWidth(105);
        TallnutB.setGraphic(view4);
        TallnutB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(TallnutB);
        Image Wallnut = new Image(getClass().getResource("/com/example/plantvszombie/wallnutCard.png").toExternalForm());
        ImageView view5 = new ImageView(Wallnut);
        view5.setFitHeight(64);
        view5.setFitWidth(105);
        WallnutB.setGraphic(view5   );
        WallnutB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(WallnutB);
        Image Cherrybomb =new Image(getClass().getResource("/com/example/plantvszombie/cherrybombCard.png").toExternalForm());
        ImageView view6 = new ImageView(Cherrybomb);
        view6.setFitHeight(64);
        view6.setFitWidth(105);
        cherrybombB.setGraphic(view6);
        cherrybombB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(cherrybombB);
        Image Jalapeno = new Image(getClass().getResource("/com/example/plantvszombie/jalapenoCard.png").toExternalForm());
        ImageView view7 = new ImageView(Jalapeno);
        view7.setFitHeight(64);
        view7.setFitWidth(105);
        JalapenoB.setGraphic(view7);
        JalapenoB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(JalapenoB);
        Image Sunflower = new Image(getClass().getResource("/com/example/plantvszombie/sunflowerCard.png").toExternalForm());
        ImageView view8 = new ImageView(Sunflower);
        view8.setFitHeight(64);
        view8.setFitWidth(105);
        SunflowerB.setGraphic(view8);
        SunflowerB.setStyle("-fx-background-color: #fff");
        vbox.getChildren().add(SunflowerB);
        yardPane.getChildren().add(vbox);
    }

    public void placeplanet(String planet,int col,int row){
        Image plantImage=null;
        if (planet.equals("sunflower")){
            Sunflower S=new Sunflower(col,row,yardPane);
            planets.add(S);
            plantImage=S.image;
            S.act(yardPane);
        }
        ImageView plantView = new ImageView(plantImage);
        plantView.setFitWidth(70);
        plantView.setFitHeight(70);

        double gridX = 245.0; // Left anchor of grid
        double gridY = 60.0;  // Top anchor of grid

        double cellWidth = 80.0;
        double cellHeight = 100.0;

        double x = gridX + col * GRID_X + (GRID_X - 70) / 2;
        double y = gridY + row * GRID_Y + (GRID_Y - 90) / 2;

        plantView.setLayoutX(x);
        plantView.setLayoutY(y);

        yardPane.getChildren().add(plantView);
    }

}




