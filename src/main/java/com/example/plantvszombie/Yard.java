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
    static final double CELL_WIDTH =80;
    static final double Cell_HEIGHT =100;
     static final double GRID_X = 245.0;
    static  final double GRID_Y = 60.0;
    List <String> selected;
    ImageView yard;
    ArrayList<Planet>planets=new ArrayList<>();
    ArrayList<Zombies>Zombies=new ArrayList<>();
    ArrayList<StoneGrave>graves=new ArrayList<>();
    Fog fog;
    boolean day;
   public Set<String> lockedCells = new HashSet<>();
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
                Rectangle rectangle = new Rectangle(CELL_WIDTH, Cell_HEIGHT);
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
                            if (planet.getRow()== row && planet.getCol()== col) {
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
        AnchorPane.setTopAnchor(gridPane, Yard.GRID_Y);
        AnchorPane.setLeftAnchor(gridPane, Yard.GRID_X);
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
                    if (planet.getCol() == col && planet.getRow() == row) {
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
            if (planet.getRow()== row && planet.getCol()== col) {
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
        ArrayList<Planet>planetsCopy = new ArrayList<>(planets);
            for (Planet planet : planetsCopy) {
                if(planet.dead){
                    this.removePlanet(planet);
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
                            Rectangle burned = new Rectangle(CELL_WIDTH, Cell_HEIGHT);
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
                lockedCells.add(row + "," + col);
                x.eatimage.setImage(x.image.getImage());
                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.seconds(1), e -> {
                                removePlanet(x);
                                Rectangle burned = new Rectangle(CELL_WIDTH, Cell_HEIGHT);
                                burned.setFill(Color.DARKGRAY);
                                burned.setOpacity(0.6);
                                gridPane.add(burned, col, row);
                            }));

                    timeline.play();
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
            if (s != null) {
                s.remove(graves);
            }

        }

        if (plantView != null) {
            plantView.setFitWidth(70);
         plantView.setFitHeight(70);
         double x = GRID_X + col * CELL_WIDTH + ( CELL_WIDTH - 70) / 2;
         double y = GRID_Y + row * Cell_HEIGHT + ( Cell_HEIGHT - 90) / 2;
         plantView.setLayoutX(x);
         plantView.setLayoutY(y);
         yardPane.getChildren().add(plantView);}
    }


    public Planet createPlanet(String planetName, int col, int row) {
        Planet newPlanet = null;
        switch (planetName) {
            case "Sunflower":
                return new Sunflower(col, row);
            case "Peashooter":
                return new Peashooter(col, row);
            case "Repeater":
                return new Repeater(col, row);
            case "Snow Pea":
                 return new SnowPea(col, row);
            case "Cherry Bomb":
                return new Cherry(col, row);
            case "jalapeno":
                return new Jalapeno(col, row);
            case "Wall-nut":
                return new WallNut(col, row);
            case "Tall-nut":
                return new TallNut(col, row);
            case "Puff":
               return new Puff(col, row);
            case "Doom":
                return new Doomshroom(col, row);
            case "Scaredy":
                return new Scaredy(col, row);
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




