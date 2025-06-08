package com.example.plantvszombie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Yard {
    AnchorPane yardPane;
    GridPane gridPane;
    Yard(){
        Image yar = new Image(getClass().getResourceAsStream("Frontyard.png"));
        ImageView yard = new ImageView(yar);
        yardPane = new AnchorPane(yard);
        PaintGrid(9,5);
    }
    public void PaintGrid(int x, int y) {
        gridPane = new GridPane();
        for (int i=0;i<y;i++) {
            for (int j=0;j< x;j++) {
                Rectangle rectangle = new Rectangle(80,100);
                rectangle.setFill(null);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(0.5);
                gridPane.add(rectangle,j,i);
            }
        }
        yardPane.getChildren().add(gridPane);
        AnchorPane.setTopAnchor(gridPane,60.0);
        AnchorPane.setLeftAnchor(gridPane,245.0);


    }

}
