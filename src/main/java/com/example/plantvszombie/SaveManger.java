package com.example.plantvszombie;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

public class SaveManger {

    public void saveGame(Yard yard, String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            GameState gameState = buildGameState(yard);
            out.writeObject(gameState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GameState buildGameState(Yard yard) {
        ArrayList<ZombieState> zombieStates = new ArrayList<>();
        for (Zombies z : yard.getZombies()) {
            zombieStates.add(z.getState());
        }
        ArrayList<SunState> sunStates = new ArrayList<>();
        for (Sun s : Sun.getSuns()) {
            sunStates.add(s.getState());
        }
        ArrayList<PlanetState> planetStates = new ArrayList<>();
        for (Planet p : yard.getPlanets()) {
            planetStates.add(p.getState());
        }
        ArrayList<stoneGraveState> stoneGrave = new ArrayList<>();
        for (StoneGrave G : yard.getGraves()) {
            stoneGrave.add(G.getState());
        }
        return new GameState(zombieStates, yard.getLockedCells(), yard.isDay(), ZombieWaveManger.getGameTime(), Sun.getCollectedpoint(), yard.getFog(), sunStates, planetStates,stoneGrave, yard.getSelected());
    }

    public Yard loadGame(String filePath) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
                GameState gameState = (GameState) in.readObject();
                 Yard yard=new Yard(gameState.getSelected(),gameState.isDay());
                yard.updateButtons();
                Set<String> lockedCells = gameState.getLockedCells();

                if (lockedCells != null) {
                    for (String cell : lockedCells) {
                        String[] parts = cell.split(",");
                        int row = Integer.parseInt(parts[0]);
                        int col = Integer.parseInt(parts[1]);

                        Rectangle burned = new Rectangle(Yard.CELL_WIDTH,Yard.Cell_HEIGHT);
                        burned.setFill(Color.DARKGRAY);
                        burned.setOpacity(0.6);

                        yard.getGridPane().add(burned, col, row);
                    }
                }
                for (ZombieState z : gameState.getZombies()) {
                    yard.getZombies().add(ZombieFactory.createFromState(z, yard.getYardPane()));
                }
                for (PlanetState p : gameState.getPlanets()) {
                    Planet.on();
                    yard.placePlanet(p.getType(), p.getCol(), p.getRow());
                    Planet planet=yard.findPlanet(p.getCol(), p.getRow());
                    planet.loadpplanet(p, yard.getYardPane());
                    if(p.isActive() &&!planet.isDayPlanet() && yard.isDay()){
                      yard.activatePlanet(planet);
                    }
                }
                for (SunState s : gameState.getSuns()){
                    Sun.getSuns().add(Sun.fromState(s, yard.getYardPane()));
                }
                for(stoneGraveState g : gameState.getStoneGraves()){
                    StoneGrave s=g.getStoneGrave(yard.getYardPane());
                    yard.getGraves().add(s);
                }
                yard.getFog().restoreState(gameState.getFogState());
                if(ZombieWaveManger.getGameTime()>=160){
                    yard.getFog().enterSlowly();
                }

                ZombieWaveManger zw=new ZombieWaveManger(yard);
                ZombieWaveManger.setGameTime(gameState.getGametime());
                zw.start();
                Sun.setCollectedpoint(gameState.getSunpoint());
                return yard;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
              return null;
            }
        }
}
  class ZombieFactory {
    public static Zombies createFromState(ZombieState state, Pane pane) {
        Zombies z;
        switch (state.getType()) {
            case "NormalZombie":
                z = new NormalZombie(state.getX(), state.getY(), pane);
                break;
            case "ScreendoorZombie":
                z = new ScreendoorZombie(state.getX(), state.getY(), pane);
                break;
            case "ImpZombie":
                z = new ImpZombie(state.getX(), state.getY(), pane);
                break;
                case "ConeheadZombie":
                    z = new ConeheadZombie(state.getX(), state.getY(), pane);
                    break;
            default:
                throw new IllegalArgumentException("Unknown zombie type: " + state.getType());
        }
        z.setHp((state.getHp()));
        z.setDirection((state.getDirection()));
        z.setHypnotized((state.isHypnotized()));
        z.setInHouse((state.isInHouse()));
        z.setFighting((state.isFighting()));
        if(z.isHypnotized()){
            z.setDirection(z.getDirection() * -1);
            z.reverseDirection();
        }
        return z;
    }
}


