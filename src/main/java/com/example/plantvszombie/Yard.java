package com.example.plantvszombie;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Yard {
    AnchorPane yardPane;
    GridPane gridPane;
    static final double GRID_X=80;
    static final double GRID_Y=100;
    Menu menu;
    ImageView yard;
    Button SnowpeaB=new Button();
    Button peashooterB=new Button();
    Button reapeaterB=new Button();
    Button TallnutB=new Button();
    Button WallnutB=new Button();
    Button cherrybombB=new Button();
    Button JalapenoB=new Button();
    Button SunflowerB=new Button();
    Label sunpoint=new Label();
    Button ShovelB=new Button();
    Button HypnoB = new Button();
    Button PuffB = new Button();
    Button ScaredyB = new Button();
    Button DoomB = new Button();
    Button IceB = new Button();
    Button BeanB = new Button();
    Button PlanternB = new Button();
    Button GraveB = new Button();
    Button BloverB = new Button();
    ArrayList<Planet>planets=new ArrayList<>();
    ArrayList<Zombies>Zombies=new ArrayList<>();
    Fog fog;

    Yard(Menu menu,boolean day) {
        Image yar = new Image(getClass().getResourceAsStream("Frontyard.png"));
         yard = new ImageView(yar);
        this.menu = menu;
        yardPane = new AnchorPane(yard);
        fog = new Fog(yardPane);
        sunpoint.setText("☀\uFE0F"+Sun.collectedpoint);
        sunpoint.setStyle(
                "-fx-font-size: 26px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Segoe UI Emoji';"
        );
        sunpoint.setLayoutX(850);
        sunpoint.setLayoutY(8);
        yardPane.getChildren().add(sunpoint);
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
        cherrybombB.setOnAction(event -> {
            selected[0]="cherrybomb";
        });
        JalapenoB.setOnAction(event -> {
            selected[0]="jalapeno";
        });
        WallnutB.setOnAction(event -> {
            selected[0]="wallnut";
        });
        TallnutB.setOnAction(event -> {
            selected[0]="tallnut";
        });
        HypnoB.setOnAction(event -> {
            selected[0]="Hypno";
        });
        PuffB.setOnAction(event -> {
            selected[0]="Puff";
        });
        IceB.setOnAction(event -> {
            selected[0]="Ice";
        });
        ScaredyB.setOnAction(event -> {
            selected[0]="Scaredy";
        });
        DoomB.setOnAction(event -> {
            selected[0]="Doom";
        });
        PlanternB.setOnAction(event -> {
            selected[0]="Plantern";
        });
        GraveB.setOnAction(event -> {
            selected[0]="Grave";
        });
        BloverB.setOnAction(event -> {
            selected[0]="Blover";
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
                    }else if("cherrybomb".equals(selected[0])&&empty) {
                        System.out.println(row);
                        placeplanet("cherrybomb",col,row);
                        selected[0] = null;
                    }else if("jalapeno".equals(selected[0])&&empty) {
                        System.out.println(row);
                        placeplanet("jalapeno",col,row);
                        selected[0] = null;
                    }else if ("wallnut".equals(selected[0])&&empty) {
                        placeplanet("wallnut",col,row);
                        selected[0] = null;
                    } else if ("tallnut".equals(selected[0])&&empty) {
                        placeplanet("tallnut",col,row);
                        selected[0] = null;
                    } else if("Puff".equals(selected[0])&&empty) {
                        placeplanet("Puff",col,row);
                    }
                });
            }
        }

        yardPane.getChildren().add(gridPane);
        AnchorPane.setTopAnchor(gridPane, 60.0);
        AnchorPane.setLeftAnchor(gridPane, 245.0);


    }
    private Planet findPlanet(int col, int row) {
        for (Planet planet : planets) {
            if (planet.row == row && planet.col == col) {
                return planet;
            }
        }
        return null;
    }
    public void startMovingAndDetecting() {
        // جلوگیری از ConcurrentModificationException با ایجاد کپی
        ArrayList<Zombies> zombieCopy = new ArrayList<>(Zombies);

        for (Zombies zombie : zombieCopy) {
            zombie.damage(planets, yardPane);
            zombie.remove(yardPane, Zombies);
            zombie.checkAndEatPlant(planets, yardPane);
        }
    }

    private void removePlanet(Planet planet) {
        planet.remove(yardPane); // فرض بر اینکه متد remove در کلاس Planet وجود دارد
        planets.remove(planet);
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
        Image Hypo = new Image(getClass().getResource("/HypnoShroomSeed.png").toExternalForm());
        ImageView view10 = new ImageView(Hypo);
        view10.setFitHeight(64);
        view10.setFitWidth(105);
        HypnoB.setGraphic(view10);
        HypnoB.setStyle("-fx-background-color: #fff");
        if(check("Hypno")){
            vbox.getChildren().add(HypnoB);
        }
        Image Puff = new Image(getClass().getResource("/PuffShroomSeed.png").toExternalForm());
        ImageView view11 = new ImageView(Puff);
        view11.setFitHeight(64);
        view11.setFitWidth(105);
        PuffB.setGraphic(view11);
        PuffB.setStyle("-fx-background-color: #fff");
        if(check("Puff")){
            vbox.getChildren().add(PuffB);
        }
        Image Scared = new Image(getClass().getResource("/ScaredyShroomSeed.png").toExternalForm());
        ImageView view12 = new ImageView(Scared);
        view12.setFitHeight(64);
        view12.setFitWidth(105);
        ScaredyB.setGraphic(view12);
        ScaredyB.setStyle("-fx-background-color: #fff");
        if(check("Scaredy")){
            vbox.getChildren().add(ScaredyB);
        }
        Image Doom = new Image(getClass().getResource("/DoomShroomSeed.png").toExternalForm());
        ImageView view13 = new ImageView(Doom);
        view13.setFitHeight(64);
        view13.setFitWidth(105);
        DoomB.setGraphic(view13);
        DoomB.setStyle("-fx-background-color: #fff");
        if(check("Doom")){
            vbox.getChildren().add(DoomB);
        }
        Image Ice = new Image(getClass().getResource("/IceShroomSeed.png").toExternalForm());
        ImageView view14 = new ImageView(Ice);
        view14.setFitHeight(64);
        view14.setFitWidth(105);
        IceB.setGraphic(view14);
        IceB.setStyle("-fx-background-color: #fff");
        if(check("Ice")){
            vbox.getChildren().add(IceB);
        }
        Image bean = new Image(getClass().getResource("/bean.png").toExternalForm());
        ImageView view15 = new ImageView(bean);
        view15.setFitHeight(64);
        view15.setFitWidth(105);
        BeanB.setGraphic(view15);
        BeanB.setStyle("-fx-background-color: #fff");
        if(check("bean")){
            vbox.getChildren().add(BeanB);
        }
        Image plantern = new Image(getClass().getResource("/PlanternSeed.png").toExternalForm());
        ImageView view16 = new ImageView(plantern);
        view16.setFitHeight(64);
        view16.setFitWidth(105);
        PlanternB.setGraphic(view16);
        PlanternB.setStyle("-fx-background-color: #fff");
        if(check("plantern")){
            vbox.getChildren().add(PlanternB);
        }
        Image blover = new Image(getClass().getResource("/BloverSeed.png").toExternalForm());
        ImageView view17 = new ImageView(blover);
        view17.setFitHeight(64);
        view17.setFitWidth(105);
        BloverB.setGraphic(view17);
        BloverB.setStyle("-fx-background-color: #fff");
        if(check("blover")){
            vbox.getChildren().add(BloverB);
        }
        Image Grave = new Image(getClass().getResource("/GraveBusterSeed.png").toExternalForm());
        ImageView view18 = new ImageView(Grave);
        view18.setFitHeight(64);
        view18.setFitWidth(105);
        GraveB.setGraphic(view18);
        GraveB.setStyle("-fx-background-color: #fff");
        if(check("Grave")){
            vbox.getChildren().add(GraveB);
        }

        HBox hbox = new HBox();
        hbox.getChildren().add(vbox);
        hbox.getChildren().add(ShovelB);
        yardPane.getChildren().add(hbox);
    }

    public void placeplanet(String planet,int col,int row){
        ImageView plantView = null;
        if (planet.equals("sunflower")&&Sunflower.canplace){
            Sunflower S=new Sunflower(col,row,yardPane);
            Sunflower.canplace=false;
            SunflowerB.setDisable(true); // غیرفعال کردن دکمه
            SunflowerB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;"); //
            S.cooldown(SunflowerB);
            planets.add(S);
            plantView=S.image;
            S.act(yardPane);
            Sun.collectedpoint-=Sunflower.cost;
        } else if(planet.equals("peashooter")&&Peashooter.canplace){
            Peashooter P=new Peashooter(col,row);
            Peashooter.canplace=false;
            peashooterB.setDisable(true); // غیرفعال کردن دکمه
            peashooterB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;"); //
            planets.add(P);
            P.cooldown(peashooterB);
            plantView=P.image;
            P.act(yardPane,Zombies);
            Sun.collectedpoint-=Peashooter.cost;
        }else if(planet.equals("reapeater")&&Repeater.canplace){
            System.out.println("repeater");
            Repeater R=new Repeater(col,row);
            Repeater.canplace=false;
            reapeaterB.setDisable(true);
            reapeaterB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
            planets.add(R);
            R.cooldown(reapeaterB);
            plantView=R.image;
            R.act(yardPane,Zombies);
            Sun.collectedpoint-=Repeater.cost;
        }
        else if(planet.equals("snowpea")&&SnowPea.canplace){
            SnowPea S=new SnowPea(col,row);
            SnowPea.canplace=false;
            SnowpeaB.setDisable(true);
            SnowpeaB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
            planets.add(S);
            plantView=S.image;
            S.cooldown(SnowpeaB);
            S.act(yardPane,Zombies);
            Sun.collectedpoint-=50;
        }else if(planet.equals("cherrybomb") && Cherry.canplace){
            Cherry C=new Cherry(col,row);
            Cherry.canplace=false;
            cherrybombB.setDisable(true);
            planets.add(C);
            plantView=C.image;
            C.cooldown(cherrybombB);
            C.act(yardPane,Zombies);
            Planet cherry = findPlanet(col,row);
            if (cherry != null) {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(2), e -> removePlanet(cherry))
                );
                timeline.play();
            }
            Sun.collectedpoint-=Cherry.cost;
        }else if(planet.equals("jalapeno") && Jalapeno.canplace) {
            Jalapeno J = new Jalapeno(col, row);
            Jalapeno.canplace = false;
            JalapenoB.setDisable(true);
            JalapenoB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
            planets.add(J);
            plantView = J.image;
            J.cooldown(JalapenoB);
            J.act(yardPane, Zombies);
            Planet jalapeno = findPlanet(col, row);
            if (jalapeno != null) {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(2), e -> removePlanet(jalapeno))
                );
                timeline.play();
            }
            Sun.collectedpoint -= Jalapeno.cost;
        }else if (planet.equals("wallnut")&&WallNut.canplace){
                WallNut w=new WallNut(col,row);
                WallNut.canplace=false;
                WallnutB.setDisable(true);
                WallnutB.setStyle(("-fx-opacity: 0.4; -fx-background-color: gray;"));
                planets.add(w);
                plantView=w.image;
                w.cooldown(WallnutB);
                Sun.collectedpoint-=WallNut.cost;
            }else if(planet.equals("tallnut")&&TallNut.canplace){
                TallNut t=new TallNut(col,row);
                TallNut.canplace=false;
                TallnutB.setDisable(true);
                TallnutB.setStyle(("-fx-opacity: 0.4; -fx-background-color: gray;"));
                planets.add(t);
                plantView=t.image;
                t.cooldown(WallnutB);
                Sun.collectedpoint-=TallNut.cost;
            }else if(planet.equals("Puff")&&Puff.canplace){
            Puff P=new Puff(col,row);
            Puff.canplace=false;
            PuffB.setDisable(true);
            PuffB.setStyle(("-fx-opacity: 0.4; -fx-background-color: gray;"));
            planets.add(P);
            plantView=P.image;
            P.cooldown(PuffB);
            P.act(yardPane, Zombies);
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
    public void Updatebutton() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0.5), event -> {
            if (Sun.collectedpoint >= Peashooter.cost && Peashooter.canplace) {
                peashooterB.setDisable(false);
                peashooterB.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
            } else {
                peashooterB.setDisable(true);
                peashooterB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
            }

            if (Sun.collectedpoint >= Repeater.cost && Repeater.canplace) {
                reapeaterB.setDisable(false);
                reapeaterB.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
            } else {
                reapeaterB.setDisable(true);
                reapeaterB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
            }
            if (Sun.collectedpoint >= Sunflower.cost && Sunflower.canplace) {
                SunflowerB.setDisable(false);
                SunflowerB.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
            } else {
                SunflowerB.setDisable(true);
                SunflowerB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
            }

            if (Sun.collectedpoint >= WallNut.cost && WallNut.canplace) {
                WallnutB.setDisable(false);
                WallnutB.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
            } else {
                WallnutB.setDisable(true);
                WallnutB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
            }
            if (Sun.collectedpoint >= TallNut.cost && TallNut.canplace) {
                TallnutB.setDisable(false);
                TallnutB.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
            } else {
                TallnutB.setDisable(true);
                TallnutB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
            }

            if (Sun.collectedpoint >= SnowPea.cost && SnowPea.canplace) {
                SnowpeaB.setDisable(false);
                SnowpeaB.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
            } else {
                SnowpeaB.setDisable(true);
                SnowpeaB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
            }
            if (Sun.collectedpoint >= Cherry.cost && Cherry.canplace) {
                cherrybombB.setDisable(false);
                cherrybombB.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
            } else {
                cherrybombB.setDisable(true);
                cherrybombB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
            }
            if (Sun.collectedpoint >= Jalapeno.cost && Jalapeno.canplace) {
                JalapenoB.setDisable(false);
                JalapenoB.setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
            } else {
                JalapenoB.setDisable(true);
                cherrybombB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
            }
            sunpoint.setText("☀\uFE0F"+Sun.collectedpoint);
            sunpoint.setLayoutX(850);
            sunpoint.setLayoutY(8);


        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }






}



