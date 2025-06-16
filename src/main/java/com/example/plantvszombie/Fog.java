package com.example.plantvszombie;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Fog {
    private Rectangle fogRect;
    private final List<Circle> holes = new ArrayList<>();
    private double currentTranslateX = 0;

    public Fog(Pane root) {
        Image imageView = new Image(getClass().getResource("/Untitled.png").toExternalForm());

        fogRect = new Rectangle(1024, 626);
        fogRect.setFill(new ImagePattern(imageView));

        fogRect.setLayoutX(1024);
        fogRect.setLayoutY(0);

        fogRect.setMouseTransparent(true);

        root.getChildren().add(fogRect);
    }

    public void enterSlowly() {
        TranslateTransition fogTransition = new TranslateTransition(Duration.seconds(20), fogRect);
        fogTransition.setToX(-512); // حرکت به سمت وسط صفحه
        fogTransition.play();
    }

    public void bringFogToFront(Pane root) {
        fogRect.toFront();
        updateClip();
    }
    public Circle addLanternHole(double centerX, double centerY, double radius) {
        Circle hole = new Circle(centerX, centerY, radius);
        holes.add(hole);
        updateClip();
        return hole;
    }

    public void removeLanternHole(Circle hole) {
        holes.remove(hole);
        updateClip();
    }


    private void updateClip() {

        fogRect.setClip(null);

        Rectangle fullFogShape = new Rectangle(1024, 626);

        Shape clipShape = fullFogShape;


        for (Circle hole : holes) {
            clipShape = Shape.subtract(clipShape, hole);
        }

        fogRect.setClip(clipShape);
    }
    public Rectangle getFogRect() {
        return fogRect;
    }
    public void hideTemporarily() {
        currentTranslateX = fogRect.getTranslateX();
        fogRect.setVisible(false);
    }

    public void restore() {
        fogRect.setVisible(true);
        fogRect.setTranslateX(currentTranslateX);
    }
}
