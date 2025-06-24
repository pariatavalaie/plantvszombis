package com.example.plantvszombie;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.util.*;

public class Yard {
    AnchorPane yardPane;
    GridPane gridPane;
    static final double GRID_X=80;
    static final double GRID_Y=100;
    List <String> selected;
    ImageView yard;
    Button SnowpeaB=new Button();
    Button peashooterB=new Button();
    Button reapeaterB=new Button();
    Button TallnutB=new Button();
    Button WallnutB=new Button();
    Button cherrybombB=new Button();
    Button JalapenoB=new Button();
    Button SunflowerB=new Button();
    Text number;
    TextFlow sunpointFlow;
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
    ArrayList<StoneGrave>graves=new ArrayList<>();
    Fog fog;
    boolean day;
    private Set<String> lockedCells = new HashSet<>(); // مثل "3,5"
    Yard(List<String> selected,boolean day) {
        Image yar;
        if(day){
         yar = new Image(getClass().getResourceAsStream("Frontyard.png"));}
        else{
            yar =new Image(getClass().getResource("/Night_11zon.png").toExternalForm());
        }
         yard = new ImageView(yar);
        yard.setFitWidth(1024);
        yard.setFitHeight(626);
        this.selected = selected;
        yardPane = new AnchorPane(yard);
        fog = new Fog(yardPane);
        this.day = day;
        Text emoji = new Text("☀️");
        emoji.setFill(Color.GOLD);
        emoji.setStyle("-fx-font-size: 26px; -fx-font-family: 'Segoe UI Emoji';");
        number = new Text(String.valueOf(Sun.collectedpoint));
        number.setFill(Color.BLACK);
        number.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI Emoji';");
        sunpointFlow = new TextFlow(emoji, number);
        sunpointFlow.setStyle(
                "-fx-background-color: #fffacd;" +
                        "-fx-padding: 4px 0px;" +
                        "-fx-background-radius: 8px;"
        );

        PaintGrid(9, 5);
        buttonpic();
    }

    public void PaintGrid(int x, int y) {
        gridPane = new GridPane();
        final String[] selected = {""};

        setSelectAction(SunflowerB, selected, "sunflower");
        setSelectAction(peashooterB, selected, "peashooter");
        setSelectAction(reapeaterB, selected, "reapeater");
        setSelectAction(SnowpeaB, selected, "snowpea");
        setSelectAction(ShovelB, selected, "shovel");
        setSelectAction(cherrybombB, selected, "cherrybomb");
        setSelectAction(JalapenoB, selected, "jalapeno");
        setSelectAction(WallnutB, selected, "wallnut");
        setSelectAction(TallnutB, selected, "tallnut");
        setSelectAction(HypnoB, selected, "Hypno");
        setSelectAction(PuffB, selected, "Puff");
        setSelectAction(IceB, selected, "Ice");
        setSelectAction(ScaredyB, selected, "Scaredy");
        setSelectAction(DoomB, selected, "Doom");
        setSelectAction(PlanternB, selected, "Plantern");
        setSelectAction(GraveB, selected, "Grave");
        setSelectAction(BloverB, selected, "Blover");
        setSelectAction(BeanB, selected, "Bean");

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Rectangle rectangle = new Rectangle(GRID_X, GRID_Y);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(0.5);
                int row = i, col = j;
                gridPane.add(rectangle, j, i);

                rectangle.setOnMouseClicked(event -> {
                    boolean empty = true;
                    boolean bean = false;
                    Planet planet1 = null;

                    for (Planet planet : planets) {
                        if (planet.row == row && planet.col == col) {
                            if (!planet.dayplanet) bean = true;
                            planet1 = planet;
                            empty = false;
                        }
                    }

                    for (StoneGrave grave : graves) {
                        if (grave.x == row && grave.y == col) {
                            empty = false;
                        }
                    }

                    String cellKey = row + "," + col;
                    if (lockedCells.contains(cellKey)) return;

                    String sel = selected[0];

                    if ("shovel".equals(sel)) {
                        if (planet1 != null) {
                            planet1.remove(yardPane);
                            planets.remove(planet1);
                        }
                        selected[0] = null;
                        return;
                    }

                    if ("Bean".equals(sel) && bean) {
                        placeplanet("Bean", col, row);
                        selected[0] = null;
                        return;
                    }

                    if ("Grave".equals(sel) && findStoneGrave(col, row) != null) {
                        placeplanet("Grave", col, row);
                        selected[0] = null;
                        return;
                    }

                    if (empty && isPlaceable(sel)) {
                        placeplanet(sel, col, row);
                        selected[0] = null;
                    }
                });
            }
        }

        yardPane.getChildren().add(gridPane);

        if (!day) {
            PaintStone(yardPane, x, y);
        }

        AnchorPane.setTopAnchor(gridPane, 60.0);
        AnchorPane.setLeftAnchor(gridPane, 245.0);
    }

    private void setSelectAction(Button button, String[] selected, String name) {
        button.setOnAction(e -> {
            selected[0] = name;

        });
    }

    private boolean isPlaceable(String sel) {
        return Arrays.asList(
                "sunflower", "peashooter", "reapeater", "snowpea",
                "cherrybomb", "jalapeno", "wallnut", "tallnut",
                "Puff", "Doom", "Scaredy", "Plantern",
                "Blover", "Ice", "Hypno"
        ).contains(sel);
    }

    private void PaintStone(Pane pane, int x, int y) {
        Random random = new Random();
        int numberOfGraves = 5; // تعداد سنگ‌قبرهایی که اول بازی ظاهر می‌شن

        for (int i = 0; i < numberOfGraves; i++) {
            int col, row;
            boolean positionOccupied;

            do {
                col = random.nextInt(x); // انتخاب تصادفی ستون
                row = random.nextInt(y); // انتخاب تصادفی سطر
                positionOccupied = false;

                // بررسی اینکه گیاهی در این مکان نباشه
                for (Planet planet : planets) {
                    if (planet.col == col && planet.row == row) {
                        positionOccupied = true;
                        break;
                    }
                }


                for (StoneGrave grave : graves) {
                    int dx = Math.abs(grave.x - col);
                    int dy = Math.abs(grave.y - row);
                    if ((dx == 0 && dy == 0) || (dx + dy == 1)) {

                        positionOccupied = true;
                        break;
                    }
                }

            } while (positionOccupied);

            StoneGrave grave = new StoneGrave(col, row, pane);
            graves.add(grave);

            grave.spawnZombie(Zombies, graves);
        }
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

        ArrayList<Zombies> zombieCopy = new ArrayList<>(Zombies);

        for (Zombies zombie : zombieCopy) {
            zombie.damage(planets, yardPane);
            zombie.remove(yardPane, Zombies);
            zombie.checkAndEatPlant(planets, yardPane);
            zombie.checkAndEatZombie(Zombies, yardPane);
        }
    }

    private void removePlanet(Planet planet) {
        planet.remove(yardPane);
        planets.remove(planet);

    }
    private StoneGrave findStoneGrave(int col, int row) {
        for (StoneGrave grave : graves) {
            if(grave.x == col && grave.y == row) {
                return grave;
            }
        }
        return null;
    }

    public void buttonpic() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().add(sunpointFlow);
        addPlantCard(vbox, "Snow Pea", SnowpeaB, "/SnowPea.png");
        addPlantCard(vbox, "Peashooter", peashooterB, "/com/example/plantvszombie/peashooterCard.png");
        addPlantCard(vbox, "Repeater", reapeaterB, "/com/example/plantvszombie/repeaterCard.png");
        addPlantCard(vbox, "Tall-nut", TallnutB, "/TallNut.png");
        addPlantCard(vbox, "Wall-nut", WallnutB, "/com/example/plantvszombie/wallnutCard.png");
        addPlantCard(vbox, "Cherry Bomb", cherrybombB, "/com/example/plantvszombie/cherrybombCard.png");
        addPlantCard(vbox, "jalapeno", JalapenoB, "/com/example/plantvszombie/jalapenoCard.png");
        addPlantCard(vbox, "Sunflower", SunflowerB, "/com/example/plantvszombie/sunflowerCard.png");
        addPlantCard(vbox, "Hypno", HypnoB, "/HypnoShroomSeed.png");
        addPlantCard(vbox, "Puff", PuffB, "/PuffShroomSeed.png");
        addPlantCard(vbox, "Scaredy", ScaredyB, "/ScaredyShroomSeed.png");
        addPlantCard(vbox, "Doom", DoomB, "/DoomShroomSeed.png");
        addPlantCard(vbox, "Ice", IceB, "/IceShroomSeed.png");
        addPlantCard(vbox, "bean", BeanB, "/bean.png");
        addPlantCard(vbox, "plantern", PlanternB, "/PlanternSeed.png");
        addPlantCard(vbox, "blover", BloverB, "/BloverSeed.png");
        addPlantCard(vbox, "Grave", GraveB, "/GraveBusterSeed.png");

        ImageView shovelView = createImageView("/Shovel.jpg");
        ShovelB.setGraphic(shovelView);
        ShovelB.setStyle("-fx-background-color: #fff");
        HBox hbox = new HBox();
        hbox.getChildren().addAll(vbox, ShovelB);
        yardPane.getChildren().add(hbox);
    }

    private void addPlantCard(VBox vbox, String name, Button button, String imagePath) {
        ImageView view = createImageView(imagePath);
        button.setGraphic(view);
        button.setStyle("-fx-background-color: #fff");
        if (check(name)) {
            vbox.getChildren().add(button);
        }
    }

    private ImageView createImageView(String path) {
        Image image = new Image(getClass().getResource(path).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(64);
        imageView.setFitWidth(105);
        return imageView;
    }


    public void placeplanet(String planet,int col,int row){
        ImageView plantView = null;
        if (planet.equals("sunflower")&&Sunflower.canplace){
            Sunflower S=new Sunflower(col,row,yardPane);
            Sunflower.canplace=false;
            SunflowerB.setDisable(true); // غیرفعال کردن دکمه
            SunflowerB.setStyle("-fx-opacity: 0.4; -fx-background-color: gray;"); //
            S.cooldown(SunflowerB,()->Sunflower.canplace=true,Sunflower.cost);
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
            P.cooldown(peashooterB,()->Peashooter.canplace=true,Peashooter.cost);
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
            R.cooldown(reapeaterB,()->Repeater.canplace=true,Repeater.cost);
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
            S.cooldown(SnowpeaB,()->SnowPea.canplace=true,SnowPea.cost);
            S.act(yardPane,Zombies);
            Sun.collectedpoint-=50;
        }else if(planet.equals("cherrybomb") && Cherry.canplace){
            Cherry C=new Cherry(col,row);
            Cherry.canplace=false;
            cherrybombB.setDisable(true);
            planets.add(C);
            plantView=C.image;
            C.cooldown(cherrybombB,()->Cherry.canplace=true,Cherry.cost);
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
            J.cooldown(JalapenoB,()->Jalapeno.canplace=true,Jalapeno.cost);
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
                w.cooldown(WallnutB,()->WallNut.canplace=true,WallNut.cost);
                Sun.collectedpoint-=WallNut.cost;
            }else if(planet.equals("tallnut")&&TallNut.canplace){
                TallNut t=new TallNut(col,row);
                TallNut.canplace=false;
                TallnutB.setDisable(true);
                TallnutB.setStyle(("-fx-opacity: 0.4; -fx-background-color: gray;"));
                planets.add(t);
                plantView=t.image;
                t.cooldown(TallnutB,()->TallNut.canplace=true,TallNut.cost);
                Sun.collectedpoint-=TallNut.cost;
            }else if(planet.equals("Puff")&&Puff.canplace){
            Puff P=new Puff(col,row);
            Puff.canplace=false;
            PuffB.setDisable(true);
            PuffB.setStyle(("-fx-opacity: 0.4; -fx-background-color: gray;"));
            planets.add(P);
            plantView=P.image;
            P.cooldown(PuffB,()->Puff.canplace=true,Puff.cost);
            if(!day){
            P.act(yardPane, Zombies);}
          }else if(planet.equals("Doom")&&Doomshroom.canplace){
            Doomshroom C=new Doomshroom(col,row);
            Doomshroom.canplace=false;
            DoomB.setDisable(true);
            DoomB.setStyle(("-fx-opacity: 0.4; -fx-background-color: gray;"));
            planets.add(C);
            plantView=C.eatimage;
            C.cooldown(DoomB,()->Doomshroom.canplace=true,Doomshroom.cost);
            if(!day){
            plantView=C.image;
            C.act(yardPane,Zombies);
            Planet cherry = findPlanet(col,row);
            lockedCells.add(row + "," + col);
            if (cherry != null) {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(2), e -> {
                            removePlanet(cherry);
                            Rectangle burned = new Rectangle(GRID_X, GRID_Y);
                            burned.setFill(Color.DARKGRAY);
                            burned.setOpacity(0.6);
                            gridPane.add(burned, col, row);
                        }));

                timeline.play();
            }}
            Sun.collectedpoint-=Doomshroom.cost;
        }else if(planet.equals("Scaredy")&&Scaredy.canplace){
            Scaredy C=new Scaredy(col,row);
            Scaredy.canplace=false;
            ScaredyB.setDisable(true);
            ScaredyB.setStyle(("-fx-opacity: 0.4; -fx-background-color: gray;"));
            planets.add(C);
            plantView=C.image;
            C.cooldown(ScaredyB,()->Scaredy.canplace=true,Scaredy.cost);
            if(!day){
                C.act(yardPane,Zombies);}
            Sun.collectedpoint-=Scaredy.cost;
        }else if(planet.equals("Plantern")&&Plantern.canplace){
            Plantern p=new Plantern(col,row,fog);
            Plantern.canplace=false;
            PlanternB.setDisable(true);
            planets.add(p);
            plantView=p.image;
            p.cooldown(PlanternB,()->Plantern.canplace=true,Plantern.cost);
            if(!day){
                p.act(yardPane);}
            Sun.collectedpoint-=Plantern.cost;
        } else if (planet.equals("Blover")&&Blover.canplace) {
            Blover b=new Blover(col,row,fog);
            Blover.canplace=false;
            BloverB.setDisable(true);
            BloverB.setStyle(("-fx-opacity: 0.4; -fx-background-color: gray;"));
            planets.add(b);
            plantView=b.image;
            b.cooldown(BloverB,()->Blover.canplace=true,Blover.cost);
            if(!day){
            b.act(yardPane);}
            Sun.collectedpoint-=Blover.cost;
        } else if(planet.equals("Bean")&&Bean.canplace) {
            Bean b=new Bean(col,row);
            Bean.canplace=false;
            BeanB.setDisable(true);
            BeanB.setStyle(("-fx-opacity: 0.4; -fx-background-color: gray;"));
            planets.add(b);
            plantView=b.image;
            b.cooldown(BeanB,()->Bean.canplace=true,Bean.cost);
            Planet x=findPlanet(col,row);
            x.act(yardPane);
            x.act(yardPane,Zombies);
            if(x instanceof Doomshroom){
                Planet cherry = findPlanet(x.col,x.row);
                lockedCells.add(x.row + "," + x.col);
                x.eatimage.setImage(x.image.getImage());
                if (cherry != null) {
                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.seconds(1), e -> {
                                removePlanet(cherry);
                                Rectangle burned = new Rectangle(GRID_X, GRID_Y);
                                burned.setFill(Color.DARKGRAY);
                                burned.setOpacity(0.6);
                                gridPane.add(burned, col, row);
                            }));

                    timeline.play();}

            }
            if(x instanceof Iceshroom){planets.remove(x);x.eatimage.setImage(x.image.getImage());
            }
            if(x instanceof Hypnoshroom){
                ( (Hypnoshroom) x ).active=true;
                x.eatimage.setImage(x.image.getImage());
            }

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                removePlanet(b);
            }));
            timeline.play();
            Sun.collectedpoint-=Bean.cost;

        }else if(planet.equals("Ice")&&Iceshroom.canplace) {
            Iceshroom i=new Iceshroom(col,row);
            Iceshroom.canplace=false;
            IceB.setDisable(true);
            IceB.setStyle(("-fx-opacity: 0.4; -fx-background-color: gray;"));
            planets.add(i);
            plantView=i.eatimage;
            System.out.println(Iceshroom.canplace);
            i.cooldown(IceB,()->Iceshroom.canplace=true,Iceshroom.cost);
            if(!day){
                i.act(yardPane,Zombies);
                plantView=i.image;
                planets.remove(i);

            }
            Sun.collectedpoint-=Iceshroom.cost;


        } else if (planet.equals("Hypno")&&Hypnoshroom.canplace) {
            Hypnoshroom h=new Hypnoshroom(col,row);
            Hypnoshroom.canplace=false;
            HypnoB.setDisable(true);
            HypnoB.setStyle(("-fx-opacity: 0.4; -fx-background-color: gray;"));
            planets.add(h);
            h.cooldown(HypnoB,()->Hypnoshroom.canplace=true,Hypnoshroom.cost);
            plantView=h.eatimage;
            if(!day){
                h.active=true;
                plantView=h.image;
                h.act(yardPane,Zombies);
            }
            Sun.collectedpoint-=Hypnoshroom.cost;

        }else if(planet.equals("Grave")&&GraveBuster.canplace) {
            GraveBuster g=new GraveBuster(col,row);
            GraveBuster.canplace=false;
            GraveB.setDisable(true);
            GraveB.setStyle(("-fx-opacity: 0.4; -fx-background-color: gray;"));
            g.cooldown(GraveB,()->GraveBuster.canplace=true,GraveBuster.cost);
            StoneGrave s=findStoneGrave(col,row);
            s.remove(graves);
            g.act(yardPane);
            plantView=g.image;
            Sun.collectedpoint-=GraveBuster.cost;
        }


        plantView.setFitWidth(70);
        plantView.setFitHeight(70);


        double gridX = 245.0; // Left anchor of grid
        double gridY = 60.0;  // Top anchor of grid

        double x = gridX + col * GRID_X + (GRID_X - 70) / 2;
        double y = gridY + row * GRID_Y + (GRID_Y - 90) / 2;

        plantView.setLayoutX(x);
        plantView.setLayoutY(y);

        yardPane.getChildren().add(plantView);
    }
    public boolean check(String name){
        System.out.println(selected.size());
        for(int i=0 ; i<selected.size() ; i++){
            if(name.equals(selected.get(i))){
                System.out.println(selected.get(i));
                return true;
            }
        }
        return false;
    }
    public void updateButtons() {
        int[] costs = {
                Peashooter.cost,
                Repeater.cost,
                Sunflower.cost,
                WallNut.cost,
                TallNut.cost,
                SnowPea.cost,
                Cherry.cost,
                Jalapeno.cost,
                Doomshroom.cost,
                Plantern.cost,
                Blover.cost,
                Bean.cost,
                Iceshroom.cost,
                Hypnoshroom.cost,
                GraveBuster.cost,

        };

        boolean[] canplaces = {
                Peashooter.canplace,
                Repeater.canplace,
                Sunflower.canplace,
                WallNut.canplace,
                TallNut.canplace,
                SnowPea.canplace,
                Cherry.canplace,
                Jalapeno.canplace,
                Doomshroom.canplace,
                Plantern.canplace,
                Blover.canplace,
                Bean.canplace,
                Iceshroom.canplace,
                Hypnoshroom.canplace,
                GraveBuster.canplace,
        };
        Button[] buttons = {
                peashooterB,
                reapeaterB,
                SunflowerB,
                WallnutB,
                TallnutB,
                SnowpeaB,
                cherrybombB,
                JalapenoB,
                DoomB,
                PlanternB,
                BloverB,
                BeanB,
                IceB,
                HypnoB,
                GraveB
        };

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0.5), event -> {

            for (int i = 0; i < costs.length; i++) {
                boolean canPlace = canplaces[i];
                switch (i) {
                    case 0: canPlace = Peashooter.canplace; break;
                    case 1: canPlace = Repeater.canplace; break;
                    case 2: canPlace = Sunflower.canplace; break;
                    case 3: canPlace = WallNut.canplace; break;
                    case 4: canPlace = TallNut.canplace; break;
                    case 5: canPlace = SnowPea.canplace; break;
                    case 6: canPlace = Cherry.canplace; break;
                    case 7: canPlace = Jalapeno.canplace; break;
                    case 8: canPlace = Doomshroom.canplace; break;
                    case 9: canPlace = Plantern.canplace; break;
                    case 10: canPlace = Blover.canplace; break;
                    case 11: canPlace = Bean.canplace; break;
                    case 12: canPlace = Iceshroom.canplace; break;
                    case 13: canPlace = Hypnoshroom.canplace; break;
                    case 14: canPlace = GraveBuster.canplace; break;
                    default: canPlace = false;
                }
                if (canPlace&&Sun.collectedpoint >= costs[i] ) {
                    buttons[i].setDisable(false);
                    buttons[i].setStyle("-fx-opacity: 1.0; -fx-background-color: #fff;");
                } else {
                    buttons[i].setDisable(true);
                    buttons[i].setStyle("-fx-opacity: 0.4; -fx-background-color: gray;");
                }
            }

            number.setText(String.valueOf(Sun.collectedpoint));

        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


}



