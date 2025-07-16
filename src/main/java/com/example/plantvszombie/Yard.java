package com.example.plantvszombie;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
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
   public Set<String> lockedCells = new HashSet<>(); // مثل "3,5"
    ButtonManager buttonManager;
    boolean isServer;
    int killedZombies=0;
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
        isServer=false;

    }

    public void PaintGrid(int x, int y) {
        gridPane = new GridPane();

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Rectangle rectangle = new Rectangle(GRID_X, GRID_Y);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(0.5);
                int row = i, col = j;
                gridPane.add(rectangle, j, i);


                rectangle.setOnDragOver(event -> {
                    if (event.getGestureSource() != rectangle && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.COPY);
                    }
                    event.consume();
                });

                rectangle.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    boolean success = false;

                    if (db.hasString()) {
                        String sel = db.getString();

                        boolean empty = true;
                        boolean bean = false;
                        Planet planet1 = null;

                        for (Planet planet : planets) {
                            if (planet.row == row && planet.col == col) {
                                if (!planet.dayPlanet) bean = true;
                                planet1 = planet;
                                empty = false;
                            }
                        }

                        for (StoneGrave grave : graves) {
                            if (grave.x == row && grave.y == col) {
                                empty = false;
                                break;
                            }
                        }

                        String cellKey = row + "," + col;
                        if (lockedCells.contains(cellKey)) return;

                        if ("shovel".equals(sel)) {
                            if (planet1 != null) {
                                planet1.remove(yardPane);
                                planets.remove(planet1);
                            }
                            success = true;
                        } else if ("bean".equals(sel) && bean) {
                            placePlanet("bean", col, row);
                            success = true;
                        } else if ("Grave".equals(sel) && findStoneGrave(col, row) != null) {
                            placePlanet("Grave", col, row);
                            success = true;
                        } else if (empty && isPlaceable(sel)) {
                            placePlanet(sel, col, row);
                            success = true;
                        }
                    }

                    event.setDropCompleted(success);
                    event.consume();
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
                col = random.nextInt(x);
                row = random.nextInt(y);
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
            zombie.freezeZombie();
            zombie.damage(planets, yardPane);
            zombie.checkAndEatPlant(planets, yardPane);
            zombie.checkAndEatZombie(Zombies, yardPane);
            if(!zombie.isAlive()){
                Zombies.remove(zombie);
                yardPane.getChildren().remove(zombie.image);
                killedZombies++;
            }
        }

            for (Planet planet : planets) {
                if(planet.dead){
                    planet.remove(yardPane);
                }
            }

        }));
        s.setCycleCount(Timeline.INDEFINITE);
        s.play();
    }

    public void removePlanet(Planet planet) {
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

    public void placePlanet(String planet, int col, int row){
        ImageView plantView = null;
        Planet planet1;
        if(Planet.canPlaceMap.get(planet)) {
            planet1 = createPlanet(planet, col, row);
            planet1.cooldown(buttonManager.getButton(planet), Planet.costMap.get(planet));
            planets.add(planet1);
            plantView = planet1.image;
            Sun.collectedpoint -= Planet.costMap.get(planet);
            if (planet1.dayPlanet||(!planet1.dayPlanet&&!day)) {
                if (planet1 instanceof specialAct) {
                    ( (specialAct) planet1 ).act(yardPane);
                }
                if (planet1 instanceof Act) {
                    ( (Act) planet1 ).act(yardPane, Zombies);
                }
            }
        } else {
            planet1 = null;
        }

        if(planet.equals("Doom")) {
            plantView = planet1.eatimage;
            if (!day) {
                plantView = planet1.image;
                lockedCells.add(row + "," + col);
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(2), e -> {
                            removePlanet(planet1);
                            Rectangle burned = new Rectangle(GRID_X, GRID_Y);
                            burned.setFill(Color.DARKGRAY);
                            burned.setOpacity(0.6);
                            gridPane.add(burned, col, row);
                        }));

                timeline.play();
            }

        } else if(planet.equals("bean")) {
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
                                removePlanet(x);
                                Rectangle burned = new Rectangle(GRID_X, GRID_Y);
                                burned.setFill(Color.DARKGRAY);
                                burned.setOpacity(0.6);
                                gridPane.add(burned, col, row);
                            }));

                    timeline.play();}
            }
            if(x instanceof Iceshroom){if(x.dead){planets.remove(x);}
                x.eatimage.setImage(x.image.getImage());
            }
            if(x instanceof Hypnoshroom){
                x.eatimage.setImage(x.image.getImage());
            }
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> removePlanet(planet1)));
            timeline.play();

        } else if (planet.equals("Hypno")||planet.equals("Ice")) {
            plantView=planet1.eatimage;
            if(!day){
                plantView=planet1.image;
            }

        }else if(planet.equals("Grave") ) {
            StoneGrave s=findStoneGrave(col,row);
            s.remove(graves);

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


    public Planet createPlanet(String planetName, int col, int row) {
        Planet newPlanet = null;
        switch (planetName) {
            case "Sunflower":
                newPlanet = new Sunflower(col, row);
                return newPlanet;
            case "Peashooter":
                newPlanet = new Peashooter(col, row);
                return newPlanet;
            case "Repeater":
                newPlanet = new Repeater(col, row);
                return newPlanet;
            case "Snow Pea":
                newPlanet = new SnowPea(col, row);
                return newPlanet;
            case "Cherry Bomb":
                newPlanet = new Cherry(col, row);
                return newPlanet;
            case "jalapeno":
                newPlanet = new Jalapeno(col, row);
                return newPlanet;
            case "Wall-nut":
                newPlanet = new WallNut(col, row);
                return newPlanet;
            case "Tall-nut":
                newPlanet = new TallNut(col, row);
                return newPlanet;
            case "Puff":
                newPlanet = new Puff(col, row);
                return newPlanet;
            case "Doom":
                newPlanet = new Doomshroom(col, row);
                return newPlanet;
            case "Scaredy":
                newPlanet = new Scaredy(col, row);
                return newPlanet;
            case "plantern":
                newPlanet = new Plantern(col, row,fog);
                return newPlanet;
            case "blover":
                newPlanet = new Blover(col, row,null);
                return newPlanet;
            case "bean":
                newPlanet = new Bean(col, row);
                return newPlanet;
            case "Ice":
                newPlanet = new Iceshroom(col, row);
                return newPlanet;
            case "Hypno":
                newPlanet = new Hypnoshroom(col, row);
                return newPlanet;
            case "Grave":
                newPlanet = new GraveBuster(col, row);
                return newPlanet;
            default:
                System.out.println("Unknown planet: " + planetName);
                return null;
        }
    }

    public void updateButtons() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0.5), event -> {
            buttonManager.update(Planet.canPlaceMap,Planet.costMap,Sun.collectedpoint);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        AnimationManager.register(timeline);
    }
    public void triggerGameEnd(boolean isWin) {
        HelloApplication.showGameResult(isWin,yardPane);
    }
}




