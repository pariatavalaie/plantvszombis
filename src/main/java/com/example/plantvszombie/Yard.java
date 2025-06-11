package com.example.plantvszombie;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Yard {
    AnchorPane yardPane;
    GridPane gridPane;
    final double GRID_X=80;
    final double GRID_Y=100;
    Menu menu;
    Button SnowpeaB=new Button();
    Button peashooterB=new Button();
    Button reapeaterB=new Button();
    Button TallnutB=new Button();
    Button WallnutB=new Button();
    Button cherrybombB=new Button();
    Button JalapenoB=new Button();
    Button SunflowerB=new Button();
    Button ShovelB=new Button();
    ArrayList<Planet>planets=new ArrayList<>();
    ArrayList<Zombies>Zombies=new ArrayList<>();

    Yard(Menu menu) {
        Image yar = new Image(getClass().getResourceAsStream("Frontyard.png"));
        ImageView yard = new ImageView(yar);
        this.menu = menu;
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
        peashooterB.setOnAction(event -> {
            selected[0]="peashooter";
        });
        reapeaterB.setOnAction(event -> {
            selected[0]="reapeater";
        });
        SnowpeaB.setOnAction(event -> {
            selected[0]="snowpea";
        });
        ShovelB.setOnAction(event -> {
            selected[0]="shovel";
            System.out.println("shovel");
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
                    boolean empty=true;
                    Planet planet1 = null;
                    for (Planet planet : planets) {
                        if(planet.row==row && planet.col==col){
                            planet1=planet;
                            System.out.println(planet1.row+" "+planet1.col);
                            empty=false;

                        }
                    }
                    if ("sunflower".equals(selected[0])&&empty) {
                        System.out.println(row);
                        placeplanet("sunflower",col,row);
                        selected[0] = null;

                    }else if ("peashooter".equals(selected[0])&&empty) {
                        System.out.println(row);
                        placeplanet("peashooter",col,row);
                        selected[0] = null;
                    }else if ("reapeater".equals(selected[0])&&empty) {
                        System.out.println(row);
                        placeplanet("reapeater",col,row);
                        selected[0] = null;
                    }else if ("snowpea".equals(selected[0])&&empty) {
                        System.out.println(row);
                        placeplanet("snowpea",col,row);
                        selected[0] = null;
                    }else if ("shovel".equals(selected[0])) {
                        System.out.println("pak");
                        if(planet1!=null){
                            planet1.remove(yardPane);
                            planets.remove(planet1);}
                        selected[0] = null;
                    }
                });
            }
        }

        yardPane.getChildren().add(gridPane);
        AnchorPane.setTopAnchor(gridPane, 60.0);
        AnchorPane.setLeftAnchor(gridPane, 245.0);


    }
    public void startMovingAndDetecting() {
        // جلوگیری از ConcurrentModificationException با ایجاد کپی
        ArrayList<Zombies> zombieCopy = new ArrayList<>(Zombies);

        for (Zombies zombie : zombieCopy) {
            zombie.damage(planets, yardPane);
            zombie.remove(yardPane, Zombies);
        }
    }

    public void buttonpic(){
        VBox vbox = new VBox();
        Image Snowpea = new Image(getClass().getResource("/SnowPea.png").toExternalForm());
        ImageView view1 = new ImageView(Snowpea);
        view1.setFitHeight(64);
        view1.setFitWidth(105);
        SnowpeaB.setGraphic(view1);
        SnowpeaB.setStyle("-fx-background-color: #fff");
        if(check("Snow Pea")){
            vbox.getChildren().add(SnowpeaB);
        }
        Image Peashooter = new Image(getClass().getResource("/com/example/plantvszombie/peashooterCard.png").toExternalForm());
        ImageView view2 = new ImageView(Peashooter);
        view2.setFitHeight(64);
        view2.setFitWidth(105);
        peashooterB.setGraphic(view2);
        peashooterB.setStyle("-fx-background-color: #fff");
        if(check("Peashooter")){
            vbox.getChildren().add(peashooterB);
        }
        Image Reapeater = new Image(getClass().getResource("/com/example/plantvszombie/repeaterCard.png").toExternalForm());
        ImageView view3 = new ImageView(Reapeater);
        view3.setFitHeight(64);
        view3.setFitWidth(105);
        reapeaterB.setGraphic(view3);
        reapeaterB.setStyle("-fx-background-color: #fff");
        if(check("Repeater")){
            vbox.getChildren().add(reapeaterB);
        }
        Image Tallnut = new Image(getClass().getResource("/TallNut.png").toExternalForm());
        ImageView view4 = new ImageView(Tallnut);
        view4.setFitHeight(64);
        view4.setFitWidth(105);
        TallnutB.setGraphic(view4);
        TallnutB.setStyle("-fx-background-color: #fff");
        if(check("Tall-nut")){
            vbox.getChildren().add(TallnutB);
        }
        Image Wallnut = new Image(getClass().getResource("/com/example/plantvszombie/wallnutCard.png").toExternalForm());
        ImageView view5 = new ImageView(Wallnut);
        view5.setFitHeight(64);
        view5.setFitWidth(105);
        WallnutB.setGraphic(view5   );
        WallnutB.setStyle("-fx-background-color: #fff");
        if(check("Wall-nut")){
            vbox.getChildren().add(WallnutB);
        }
        Image Cherrybomb =new Image(getClass().getResource("/com/example/plantvszombie/cherrybombCard.png").toExternalForm());
        ImageView view6 = new ImageView(Cherrybomb);
        view6.setFitHeight(64);
        view6.setFitWidth(105);
        cherrybombB.setGraphic(view6);
        cherrybombB.setStyle("-fx-background-color: #fff");
        if(check("Cherry Bomb")){
            vbox.getChildren().add(cherrybombB);
        }
        Image Jalapeno = new Image(getClass().getResource("/com/example/plantvszombie/jalapenoCard.png").toExternalForm());
        ImageView view7 = new ImageView(Jalapeno);
        view7.setFitHeight(64);
        view7.setFitWidth(105);
        JalapenoB.setGraphic(view7);
        JalapenoB.setStyle("-fx-background-color: #fff");
        if(check("jalapeno")){
            vbox.getChildren().add(JalapenoB);
        }
        Image Sunflower = new Image(getClass().getResource("/com/example/plantvszombie/sunflowerCard.png").toExternalForm());
        ImageView view8 = new ImageView(Sunflower);
        view8.setFitHeight(64);
        view8.setFitWidth(105);
        SunflowerB.setGraphic(view8);
        SunflowerB.setStyle("-fx-background-color: #fff");
        if(check("Sunflower")){
            vbox.getChildren().add(SunflowerB);
        }
        ImageView view9=new ImageView(new Image(getClass().getResource("/Shovel.jpg").toExternalForm()));
        view9.setFitHeight(64);
        view9.setFitWidth(105);
        ShovelB.setGraphic(view9);
        ShovelB.setStyle("-fx-background-color: #fff");
        HBox hbox = new HBox();
        hbox.getChildren().add(vbox);
        hbox.getChildren().add(ShovelB);
        yardPane.getChildren().add(hbox);
    }

    public void placeplanet(String planet,int col,int row){
        ImageView plantView = null;
        if (planet.equals("sunflower")&&Sunflower.canplace&&Sun.collectedpoint>=50){
            Sunflower S=new Sunflower(col,row,yardPane);
            Sunflower.canplace=false;
            SunflowerB.setDisable(true); // غیرفعال کردن دکمه
            SunflowerB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;"); //
            S.cooldown(SunflowerB);
            planets.add(S);
            plantView=S.image;
            S.act(yardPane);
            Sun.collectedpoint-=50;
        } else if(planet.equals("peashooter")&&Peashooter.canplace&&Sun.collectedpoint>=50){
            Peashooter P=new Peashooter(col,row);
            Peashooter.canplace=false;
            peashooterB.setDisable(true); // غیرفعال کردن دکمه
            peashooterB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;"); //
            planets.add(P);
            P.cooldown(peashooterB);
            plantView=P.image;
            P.act(yardPane,Zombies);
            Sun.collectedpoint-=100;
        }else if(planet.equals("reapeater")&&Sun.collectedpoint>=50){
            System.out.println("repeater");
            Repeater R=new Repeater(col,row);
            Repeater.canplace=false;
            reapeaterB.setDisable(true);
            reapeaterB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
            planets.add(R);
            R.cooldown(reapeaterB);
            plantView=R.image;
            R.act(yardPane,Zombies);
            Sun.collectedpoint-=50;
        }
        else if(planet.equals("snowpea")&&SnowPea.canplace&&Sun.collectedpoint>=50){
            SnowPea S=new SnowPea(col,row);
            SnowPea.canplace=false;
            SnowpeaB.setDisable(true);
            SnowpeaB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
            planets.add(S);
            plantView=S.image;
            S.cooldown(SnowpeaB);
            S.act(yardPane,Zombies);
            Sun.collectedpoint-=50;
        }

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
    public boolean check(String name){
        List selected = menu.getSelectedPlantsNames();
        System.out.println(selected.size());
        for(int i=0 ; i<selected.size() ; i++){
            if(name.equals(selected.get(i))){
                System.out.println(selected.get(i));
                return true;
            }
        }
        return false;
    }





}



