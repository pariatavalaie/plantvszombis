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
        for (Zombies z : yard.Zombies) {
            zombieStates.add(z.getState());
        }

        ArrayList<SunState> sunStates = new ArrayList<>();
        for (Sun s : Sun.suns) {
            sunStates.add(s.getState());
        }

        ArrayList<PlanetState> planetStates = new ArrayList<>();
        for (Planet p : yard.planets) {
            planetStates.add(p.getState());
        }
        ArrayList<stoneGraveState> stoneGrave = new ArrayList<>();
        for (StoneGrave G : yard.graves) {
            stoneGrave.add(G.getState());
        }

        return new GameState(zombieStates, yard.lockedCells, yard.day, ZombieWaveManger.gameTime, Sun.collectedpoint, yard.fog, sunStates, planetStates,stoneGrave,yard.selected);
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

                        yard.gridPane.add(burned, col, row);
                    }
                }


                for (ZombieState z : gameState.getZombies()) {
                    yard.Zombies.add(ZombieFactory.createFromState(z,yard.yardPane));
                }
                
                for (PlanetState p : gameState.getPlanets()) {
                    Planet.on();
                    yard.placePlanet(p.getType(), p.getCol(), p.getRow());
                    Planet planet=yard.findPlanet(p.getCol(), p.getRow());
                    planet.loadpplanet(p,yard.yardPane);
                    if(p.isActive() &&!planet.isDayPlanet() && yard.day){
                      yard.activatePlanet(planet);
                    }

                }
                for (SunState s : gameState.getSuns()){
                    Sun.suns.add(Sun.fromState(s,yard.yardPane));
                }
                for(stoneGraveState g : gameState.getStoneGraves()){
                    yard.graves.add(g.getStoneGrave(yard.yardPane));
                }
                yard.fog.restoreState(gameState.getFogState());

                ZombieWaveManger zw=new ZombieWaveManger(yard);
                ZombieWaveManger.gameTime=gameState.getGametime();
                zw.start();

                Sun.collectedpoint=gameState.getSunpoint();



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


