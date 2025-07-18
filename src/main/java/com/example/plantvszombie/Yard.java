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
    private AnchorPane yardPane;
    private GridPane gridPane;
    static final double CELL_WIDTH = 80;
    static final double Cell_HEIGHT = 100;
     static final double GRID_X = 245.0;
    static  final double GRID_Y = 60.0;
    private List <String> selected;
    private ImageView yard;
    private ArrayList<Planet>planets=new ArrayList<>();
    private ArrayList<Zombies>Zombies=new ArrayList<>();
    private ArrayList<StoneGrave>graves=new ArrayList<>();
    private Fog fog;
    private boolean day;
   private Set<String> lockedCells = new HashSet<>();
    private ButtonManager buttonManager;
    private boolean isServer;
    private int killedZombies=0;

    Yard(List<String> selected,boolean day) {
        Image yar;
        if(day){
         yar = new Image(getClass().getResourceAsStream("Frontyard.png"));}
        else{
            yar =new Image(getClass().getResource("/Night_11zon.png").toExternalForm());
        }
         setYard(new ImageView(yar));
        getYard().setFitWidth(1024);
        getYard().setFitHeight(626);
        this.setSelected(selected);
        setYardPane(new AnchorPane(getYard()));
        setFog(new Fog(getYardPane()));
        this.setDay(day);
        setButtonManager(new ButtonManager(selected));
        System.out.println(selected);
        getButtonManager().addTo(getYardPane());
        PaintGrid(9, 5);
        setServer(false);
    }

    public void PaintGrid(int x, int y) {
        setGridPane(new GridPane());

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Rectangle rectangle = new Rectangle(CELL_WIDTH, Cell_HEIGHT);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(0.5);
                int row = i, col = j;
                getGridPane().add(rectangle, j, i);
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
                        for (Planet planet : getPlanets()) {
                            if (planet.getRow()== row && planet.getCol()== col) {
                                if (!planet.isDayPlanet()) bean = true;
                                planet1 = planet;
                                empty = false;
                            }
                        }
                        for (StoneGrave grave : getGraves()) {
                            if (grave.getX() == row && grave.getY() == col) {
                                empty = false;
                                break;
                            }
                        }
                        String cellKey = row + "," + col;
                        if (getLockedCells().contains(cellKey)) return;
                        if ("shovel".equals(sel)) {
                            if (planet1 != null) {
                                planet1.remove(getYardPane());
                                getPlanets().remove(planet1);
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
        getYardPane().getChildren().add(getGridPane());
        if (!isDay()) {
            PaintStone(getYardPane(), x, y);
        }
        startMovingAndDetecting();
        AnchorPane.setTopAnchor(getGridPane(), Yard.GRID_Y);
        AnchorPane.setLeftAnchor(getGridPane(), Yard.GRID_X);
    }

    private boolean isPlaceable(String sel) {
        return Arrays.asList(
                "Sunflower", "Peashooter", "Wall-nut", "Cherry Bomb",
                "Snow Pea", "Repeater", "Tall-nut", "jalapeno", "Hypno", "Puff", "Scaredy", "Doom", "Ice", "blover", "plantern"
        ).contains(sel);
    }

    private void PaintStone(Pane pane, int x, int y) {
        Random random = new Random();
        int numberOfGraves = 5;

        for (int i = 0; i < numberOfGraves; i++) {
            int col, row;
            boolean positionOccupied;
            do {
                col = random.nextInt(x);
                row = random.nextInt(y);
                positionOccupied = false;
                for (Planet planet : getPlanets()) {
                    if (planet.getCol() == col && planet.getRow() == row) {
                        positionOccupied = true;
                        break;
                    }
                }
                for (StoneGrave grave : getGraves()) {
                    int dx = Math.abs(grave.getX() - col);
                    int dy = Math.abs(grave.getY() - row);
                    if ((dx == 0 && dy == 0) || (dx + dy == 1)) {
                        positionOccupied = true;
                        break;
                    }
                }
            } while (positionOccupied);
            StoneGrave grave = new StoneGrave(col, row, pane);
            getGraves().add(grave);
            grave.spawnZombie(getZombies(), getGraves());
        }
    }

    public Planet findPlanet(int col, int row) {
        for (Planet planet : getPlanets()) {
            if (planet.getRow()== row && planet.getCol()== col) {
                return planet;
            }
        }
        return null;
    }

    public void startMovingAndDetecting() {
        Timeline s=new Timeline(new KeyFrame(Duration.seconds(1),event -> {
        ArrayList<Zombies> zombieCopy = new ArrayList<>(getZombies());
        for (Zombies zombie : zombieCopy) {
            zombie.freezeZombie();
            zombie.damage(getPlanets(), getYardPane());
            zombie.checkAndEatPlant(getPlanets(), getYardPane());
            zombie.checkAndEatZombie(getZombies(), getYardPane());
            if(!zombie.isAlive()){
                getZombies().remove(zombie);
                getYardPane().getChildren().remove(zombie.getImage());
                setKilledZombies(getKilledZombies() + 1);
            }
        }
        ArrayList<Planet>planetsCopy = new ArrayList<>(getPlanets());
            for (Planet planet : planetsCopy) {
                if(planet.isDead()){
                    this.removePlanet(planet);
                }
            }
        }));
        s.setCycleCount(Timeline.INDEFINITE);
        s.play();
    }

    public void removePlanet(Planet planet) {
        planet.remove(getYardPane());
        getPlanets().remove(planet);
    }

    private StoneGrave findStoneGrave(int col, int row) {
        for (StoneGrave grave : getGraves()) {
            if(grave.getX() == col && grave.getY() == row) {
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
            planet1.cooldown(getButtonManager().getButton(planet), Planet.costMap.get(planet));
            getPlanets().add(planet1);
            plantView = planet1.getImage();
            Sun.setCollectedpoint(Sun.getCollectedpoint() - Planet.costMap.get(planet));
            if (planet1.isDayPlanet() ||(!planet1.isDayPlanet() &&!isDay() )) {
                if (planet1 instanceof specialAct) {
                    ( (specialAct) planet1 ).act(getYardPane());
                }
                if (planet1 instanceof Act) {
                    ( (Act) planet1 ).act(getYardPane(), getZombies());
                }
            }
        } else {
            planet1 = null;
        }
        if(planet.equals("Doom")||planet.equals("Hypno")||planet.equals("Ice")) {
            plantView = planet1.getEatimage();
            if (!isDay()) {
                plantView = planet1.getImage();
                setLockedCells(col,row,planet1);
            }
        } else if(planet.equals("bean")) {
            Planet x=findPlanet(col,row);
            activatePlanet(x);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> removePlanet(planet1)));
            timeline.play();
        }else if(planet.equals("Grave") ) {
            StoneGrave s=findStoneGrave(col,row);
            if (s != null) {
                s.remove(getGraves());
            }
        }
        if (plantView != null) {
         plantView.setFitWidth(70);
         plantView.setFitHeight(70);
         double x = GRID_X + col * CELL_WIDTH + ( CELL_WIDTH - 70) / 2;
         double y = GRID_Y + row * Cell_HEIGHT + ( Cell_HEIGHT - 90) / 2;
         plantView.setLayoutX(x);
         plantView.setLayoutY(y);
         getYardPane().getChildren().add(plantView);}
    }

    private void setLockedCells(int col, int row,Planet planet) {
        if(planet instanceof Doomshroom){
        getLockedCells().add(row + "," + col);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    removePlanet(planet);
                    Rectangle burned = new Rectangle(CELL_WIDTH, Cell_HEIGHT);
                    burned.setFill(Color.DARKGRAY);
                    burned.setOpacity(0.6);
                    getGridPane().add(burned, col, row);
                }));
        timeline.play();}
    }

    protected void activatePlanet(Planet x) {
        if (x instanceof Act) {((Act)x).act(getYardPane(), getZombies());}

        if (x instanceof specialAct) {((specialAct)x).act(getYardPane());}

        if(x instanceof Doomshroom){
            setLockedCells(x.getCol(),x.getRow(),x);
            x.getEatimage().setImage(x.getImage().getImage());}

        if(x instanceof Iceshroom){if(x.isDead()){
            getPlanets().remove(x);}
            x.getEatimage().setImage(x.getImage().getImage());}

        if(x instanceof Hypnoshroom){
            x.getEatimage().setImage(x.getImage().getImage());}
    }

    public Planet createPlanet(String planetName, int col, int row) {
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
                return new Plantern(col, row, getFog());
            case "blover":
                return new Blover(col, row,null);
            case "bean":
                return new Bean(col, row);
            case "Ice":
                return new Iceshroom(col, row);
            case "Hypno":
                return new Hypnoshroom(col, row);
            case "Grave":
                return new GraveBuster(col, row);
            default:
                System.out.println("Unknown planet: " + planetName);
                return null;
        }
    }

    public void updateButtons() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0.5), event -> {
            getButtonManager().update(Planet.canPlaceMap,Planet.costMap, Sun.getCollectedpoint());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        AnimationManager.register(timeline);
    }
    public void triggerGameEnd(boolean isWin) {
        HelloApplication.showGameResult(isWin, getYardPane());
    }

    public AnchorPane getYardPane() {
        return yardPane;
    }

    public void setYardPane(AnchorPane yardPane) {
        this.yardPane = yardPane;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public List<String> getSelected() {
        return selected;
    }

    public void setSelected(List<String> selected) {
        this.selected = selected;
    }

    public ImageView getYard() {
        return yard;
    }

    public void setYard(ImageView yard) {
        this.yard = yard;
    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(ArrayList<Planet> planets) {
        this.planets = planets;
    }

    public ArrayList<Zombies> getZombies() {
        return Zombies;
    }

    public void setZombies(ArrayList<Zombies> zombies) {
        Zombies = zombies;
    }

    public ArrayList<StoneGrave> getGraves() {
        return graves;
    }

    public void setGraves(ArrayList<StoneGrave> graves) {
        this.graves = graves;
    }

    public Fog getFog() {
        return fog;
    }

    public void setFog(Fog fog) {
        this.fog = fog;
    }

    public boolean isDay() {
        return day;
    }

    public void setDay(boolean day) {
        this.day = day;
    }

    public Set<String> getLockedCells() {
        return lockedCells;
    }

    public void setLockedCells(Set<String> lockedCells) {
        this.lockedCells = lockedCells;
    }

    public ButtonManager getButtonManager() {
        return buttonManager;
    }

    public void setButtonManager(ButtonManager buttonManager) {
        this.buttonManager = buttonManager;
    }

    public boolean isServer() {
        return isServer;
    }

    public void setServer(boolean server) {
        isServer = server;
    }

    public int getKilledZombies() {
        return killedZombies;
    }

    public void setKilledZombies(int killedZombies) {
        this.killedZombies = killedZombies;
    }
}




