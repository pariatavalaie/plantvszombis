package com.example.plantvszombie;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.*;

public class Yard {
    AnchorPane yardPane;
    GridPane gridPane;
    static final double GRID_X=80;
    static final double GRID_Y=100;
    List <String> selected;
    ImageView yard;
    ArrayList<Planet>planets=new ArrayList<>();
    ArrayList<Zombies>Zombies=new ArrayList<>();
    ArrayList<StoneGrave>graves=new ArrayList<>();
    Fog fog;
    boolean day;
    private Set<String> lockedCells = new HashSet<>(); // مثل "3,5"
    ButtonManager buttonManager;
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
        buttonManager=new ButtonManager(selected);
        System.out.println(selected);
        buttonManager.addTo(yardPane);
        PaintGrid(9, 5);

    }

    public void PaintGrid(int x, int y) {
        gridPane = new GridPane();
        final String[] selected = {""};
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

                    String sel =buttonManager.getSelected();
                    buttonManager.clearSelected();

                    if ("shovel".equals(sel)) {
                        if (planet1 != null) {
                            planet1.remove(yardPane);
                            planets.remove(planet1);
                        }
                        selected[0] = null;
                        return;
                    }

                    if ("bean".equals(sel) && bean) {
                        placeplanet("bean", col, row);
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
        startMovingAndDetecting();

        AnchorPane.setTopAnchor(gridPane, 60.0);
        AnchorPane.setLeftAnchor(gridPane, 245.0);
    }



    private boolean isPlaceable(String sel) {
        return Arrays.asList(
                "Sunflower", "Peashooter", "Wall-nut", "Cherry Bomb",
                "Snow Pea", "Repeater", "Tall-nut", "jalapeno", "Hypno", "Puff", "Scaredy", "Doom", "Ice", "blover", "plantern"
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

    public Planet findPlanet(int col, int row) {
        for (Planet planet : planets) {
            if (planet.row == row && planet.col == col) {
                return planet;
            }
        }
        return null;
    }
    public void startMovingAndDetecting() {
        Timeline s=new Timeline(new KeyFrame(Duration.seconds(1),event -> {

        ArrayList<Zombies> zombieCopy = new ArrayList<>(Zombies);

        for (Zombies zombie : zombieCopy) {
            zombie.damage(planets, yardPane);
            zombie.remove(yardPane, Zombies);
            zombie.checkAndEatPlant(planets, yardPane);
            zombie.checkAndEatZombie(Zombies, yardPane);

            for (Planet planet : planets) {
                if(planet.dead){
                    planet.remove(yardPane);
                }
            }

        }}));
        s.setCycleCount(Timeline.INDEFINITE);
        s.play();
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

    public void placeplanet(String planet,int col,int row){
        ImageView plantView = null;
        if (planet.equals("Sunflower")&&Sunflower.canplace){
            Planet S=new Sunflower(col,row,yardPane);
            Sunflower.canplace=false;
            S.cooldown(buttonManager.getButton("Sunflower"),()->Sunflower.canplace=true,Sunflower.cost);
            planets.add(S);
            plantView=S.image;
            ((specialAct)S).act(yardPane);
            Sun.collectedpoint-=Sunflower.cost;
        } else if(planet.equals("Peashooter")&&Peashooter.canplace){
            Planet P=new Peashooter(col,row);
            Peashooter.canplace=false;
            planets.add(P);
            P.cooldown(buttonManager.getButton("Peashooter"),()->Peashooter.canplace=true,Peashooter.cost);
            plantView=P.image;
            ((Act)P).act(yardPane,Zombies);
            Sun.collectedpoint-=Peashooter.cost;
        }else if(planet.equals("Repeater")&&Repeater.canplace){
            System.out.println("repeater");
            Planet R=new Repeater(col,row);
            Repeater.canplace=false;
            planets.add(R);
            R.cooldown(buttonManager.getButton( "Repeater"),()->Repeater.canplace=true,Repeater.cost);
            plantView=R.image;
            ((Act)R).act(yardPane,Zombies);
            Sun.collectedpoint-=Repeater.cost;
        }
        else if(planet.equals("Snow Pea")&&SnowPea.canplace){
            Planet S=new SnowPea(col,row);
            SnowPea.canplace=false;
            planets.add(S);
            plantView=S.image;
            S.cooldown(buttonManager.getButton("Snow Pea"),()->SnowPea.canplace=true,SnowPea.cost);
            ((Act)S).act(yardPane,Zombies);
            Sun.collectedpoint-=50;
        }else if(planet.equals("Cherry Bomb") && Cherry.canplace){
            Planet C =new Cherry(col,row);
            Cherry.canplace=false;
            planets.add(C);
            plantView=C.image;
            C.cooldown(buttonManager.getButton("Cherry Bomb"),()->Cherry.canplace=true,Cherry.cost);
            ((Act)C).act(yardPane, Zombies);
           Sun.collectedpoint-=Cherry.cost;
        }else if(planet.equals("jalapeno") && Jalapeno.canplace) {
            Planet J = new Jalapeno(col, row);
            Jalapeno.canplace = false;
            planets.add(J);
            plantView = J.image;
            J.cooldown(buttonManager.getButton("jalapeno"),()->Jalapeno.canplace=true,Jalapeno.cost);
            ((Act)J).act(yardPane, Zombies);
            Sun.collectedpoint -= Jalapeno.cost;
        }else if (planet.equals("Wall-nut")&&WallNut.canplace){
                Planet w=new WallNut(col,row);
                WallNut.canplace=false;
                planets.add(w);
                plantView=w.image;
                w.cooldown(buttonManager.getButton("Wall-nut"),()->WallNut.canplace=true,WallNut.cost);
                Sun.collectedpoint-=WallNut.cost;
            }else if(planet.equals("Tall-nut")&&TallNut.canplace){
                Planet t=new TallNut(col,row);
                TallNut.canplace=false;
                planets.add(t);
                plantView=t.image;
                t.cooldown(buttonManager.getButton("Tall-nut"),()->TallNut.canplace=true,TallNut.cost);
                Sun.collectedpoint-=TallNut.cost;
            }else if(planet.equals("Puff")&&Puff.canplace){
            Planet P=new Puff(col,row);
            Puff.canplace=false;
            planets.add(P);
            plantView=P.image;
            P.cooldown(buttonManager.getButton("Puff"),()->Puff.canplace=true,Puff.cost);
            if(!day){
            ((Act)P).act(yardPane, Zombies);}
          }else if(planet.equals("Doom")&&Doomshroom.canplace){
            Planet C=new Doomshroom(col,row);
            Doomshroom.canplace=false;
            planets.add(C);
            plantView=C.eatimage;
            C.cooldown(buttonManager.getButton("Doom"),()->Doomshroom.canplace=true,Doomshroom.cost);
            if(!day){
            plantView=C.image;
            ((Act)C).act(yardPane,Zombies);
            lockedCells.add(row + "," + col);
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(2), e -> {
                            removePlanet(C);
                            Rectangle burned = new Rectangle(GRID_X, GRID_Y);
                            burned.setFill(Color.DARKGRAY);
                            burned.setOpacity(0.6);
                            gridPane.add(burned, col, row);
                        }));

                timeline.play();
            }
            Sun.collectedpoint-=Doomshroom.cost;
        }else if(planet.equals("Scaredy")&&Scaredy.canplace){
            Planet C=new Scaredy(col,row);
            Scaredy.canplace=false;
            planets.add(C);
            plantView=C.image;
            C.cooldown(buttonManager.getButton("Scaredy"),()->Scaredy.canplace=true,Scaredy.cost);
            if(!day){
                ((Act)C).act(yardPane,Zombies);}
            Sun.collectedpoint-=Scaredy.cost;
        }else if(planet.equals("plantern")&&Plantern.canplace){
            Planet p=new Plantern(col,row,fog);
            Plantern.canplace=false;
            planets.add(p);
            plantView=p.image;
            p.cooldown(buttonManager.getButton("plantern"),()->Plantern.canplace=true,Plantern.cost);
            if(!day){
            ((specialAct)p).act(yardPane);}
            Sun.collectedpoint-=Plantern.cost;
        } else if (planet.equals("blover")&&Blover.canplace) {
            Planet b=new Blover(col,row,fog);
            Blover.canplace=false;
            planets.add(b);
            plantView=b.image;
            b.cooldown(buttonManager.getButton("blover"),()->Blover.canplace=true,Blover.cost);
            if(!day){
                ((specialAct)b).act(yardPane);}
            Sun.collectedpoint-=Blover.cost;
        } else if(planet.equals("bean")&&Bean.canplace) {
            Planet b=new Bean(col,row);
            Bean.canplace=false;
            planets.add(b);
            plantView=b.image;
            b.cooldown(buttonManager.getButton("bean"),()->Bean.canplace=true,Bean.cost);
            Planet x=findPlanet(col,row);
            if (x instanceof Act) {
                ((Act)x).act(yardPane, Zombies);
            }
            if (x instanceof specialAct) {
                ((specialAct)x).act(yardPane);
            }
            if(x instanceof Doomshroom){
                Planet Doom = findPlanet(x.col,x.row);
                lockedCells.add(x.row + "," + x.col);
                x.eatimage.setImage(x.image.getImage());
                if (Doom != null) {
                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.seconds(1), e -> {
                                removePlanet(Doom);
                                Rectangle burned = new Rectangle(GRID_X, GRID_Y);
                                burned.setFill(Color.DARKGRAY);
                                burned.setOpacity(0.6);
                                gridPane.add(burned, col, row);
                            }));

                    timeline.play();}
            }
            if(x instanceof Iceshroom){if(x.dead==true){planets.remove(x);};x.eatimage.setImage(x.image.getImage());
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
            Planet i=new Iceshroom(col,row);
            Iceshroom.canplace=false;
            planets.add(i);
            plantView=i.eatimage;
            System.out.println(Iceshroom.canplace);
            i.cooldown(buttonManager.getButton("Ice"),()->Iceshroom.canplace=true,Iceshroom.cost);
            if(!day){
                ((Act)i).act(yardPane,Zombies);
                plantView=i.image;
                if(i.dead==true){
                    planets.remove(i);
                }
            }
            Sun.collectedpoint-=Iceshroom.cost;
        } else if (planet.equals("Hypno")&&Hypnoshroom.canplace) {
            Planet h=new Hypnoshroom(col,row);
            Hypnoshroom.canplace=false;
            planets.add(h);
            h.cooldown(buttonManager.getButton("Hypno"),()->Hypnoshroom.canplace=true,Hypnoshroom.cost);
            plantView=h.eatimage;
            if(!day){
                ((Hypnoshroom)h).active=true;
                plantView=h.image;
                ((Act)h).act(yardPane,Zombies);
            }
            Sun.collectedpoint-=Hypnoshroom.cost;

        }else if(planet.equals("Grave")&&GraveBuster.canplace) {
            Planet g=new GraveBuster(col,row);
            GraveBuster.canplace=false;
            g.cooldown(buttonManager.getButton("Grave"),()->GraveBuster.canplace=true,GraveBuster.cost);
            StoneGrave s=findStoneGrave(col,row);
            s.remove(graves);
            ((specialAct)g).act(yardPane);
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
                Puff.cost,
                Scaredy.cost,

        };
       String[] names={"Peashooter","Repeater","Sunflower", "Wall-nut","Tall-nut","Snow Pea","Cherry Bomb","jalapeno",
               "Doom", "plantern","blover", "bean", "Ice", "Hypno","Grave","Puff","Scaredy" };


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0.5), event -> {
            Map<String, Boolean> canPlaceMap = new HashMap<>();
            Map<String, Integer> costMap = new HashMap<>();
            for (int i = 0; i < costs.length; i++) {

               boolean canPlace = false;
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
                    case 15: canPlace = Puff.canplace; break;
                    case 16: canPlace = Scaredy.canplace; break;
                    default: canPlace = false;
                }
                costMap.put(names[i], costs[i]);
                canPlaceMap.put(names[i], canPlace);

            }
            buttonManager.update(canPlaceMap,costMap,Sun.collectedpoint);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}




