package com.example.plantvszombie;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.*;
import java.util.ArrayList;

public class SaveManger {
    public void saveGame(Yard yard,String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Zombies
            ArrayList<ZombieState> zombieStates = new ArrayList<>();
            for (Zombies z : yard.Zombies) {
                zombieStates.add(z.getState());
            }
            ArrayList<SunState> sunStates = new ArrayList<>();
            for (Sun s : Sun.suns) {
                sunStates.add(s.getState());
            }
            ArrayList<PlanetState> planetStates = new ArrayList<>();
            for (Planet p:yard.planets) {
                planetStates.add(p.getState());
            }
            GameState gameState = new GameState(zombieStates,yard.selected,yard.day,ZombieWaveManger.gameTime,Sun.collectedpoint,yard.fog,sunStates,planetStates);
            out.writeObject(gameState);


        }catch (Exception e) {e.printStackTrace();}
        }

        public Yard loadGame(String filePath) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
                GameState gameState = (GameState) in.readObject();
                Yard yard=new Yard(gameState.getSelected(),gameState.isDay());
                yard.updateButtons();
                yard.fog.restoreState(gameState.getFogState());
                for (ZombieState z : gameState.getZombies()) {
                    yard.Zombies.add(ZombieFactory.createFromState(z,yard.yardPane));
                }
                for (SunState s : gameState.getSuns()){
                 Sun.suns.add(Sun.fromState(s,yard.yardPane));
                }
                for (PlanetState p : gameState.getPlanets()) {
                    Planet.on();
                    yard.placeplanet(p.type, p.col, p.row);
                    Planet planet=yard.findPlanet(p.col,p.row);
                    planet.loadpplanet(p,yard.yardPane);

                }

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
        z.hp=(state.getHp());
        z.direction=(state.getDirection());
        z.isHypnotized=(state.isHypnotized());
        z.inHouse=(state.isInHouse());
        z.fighting=(state.isFighting());
        if(z.isHypnotized){
            z.direction*=-1;
            z.reverseDirection();
        }
        return z;
    }
}


