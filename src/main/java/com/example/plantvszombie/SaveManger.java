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
            GameState gameState = new GameState(zombieStates,yard.selected,yard.day,ZombieWaveManger.gameTime,Sun.collectedpoint);
            out.writeObject(gameState);


        }catch (Exception e) {e.printStackTrace();}
        }

        public Yard loadGame(String filePath) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
                GameState gameState = (GameState) in.readObject();
                Yard yard=new Yard(gameState.selected,gameState.day);
                yard.updateButtons();
                for (ZombieState z : gameState.zombies) {
                    yard.Zombies.add(ZombieFactory.createFromState(z,yard.yardPane));
                }
                Sun.collectedpoint=gameState.sunpoint;
                ZombieWaveManger zw=new ZombieWaveManger(yard);
                ZombieWaveManger.gameTime=gameState.gametime;

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
            default:
                throw new IllegalArgumentException("Unknown zombie type: " + state.getType());
        }
        z.hp=(state.getHp());
        z.direction=(state.getDirection());
        z.isHypnotized=(state.isHypnotized());
        z.inHouse=(state.isInHouse());
        z.fighting=(state.isFighting());
        return z;
    }
}


