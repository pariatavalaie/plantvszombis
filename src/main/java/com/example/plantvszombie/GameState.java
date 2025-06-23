package com.example.plantvszombie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {
    ArrayList<ZombieState> zombies;
    List <String> selected;
    boolean day;
    int gametime;
    int sunpoint;
     GameState(ArrayList<ZombieState> zombies, List<String> selected, boolean day, int gametime, int sunpoint) {
         this.zombies = zombies;
         this.selected = selected;
         this.day = day;
         this.gametime = gametime;
         this.sunpoint = sunpoint;


     }


}


 class ZombieState implements Serializable {
    private String type; // مثلا "NormalZombie"
    private int x, y;
    private int hp;
    private int direction;
    private boolean isHypnotized;
    private boolean inHouse;
    private boolean fighting;

    public ZombieState(String type, int x, int y, int hp, int direction,
                       boolean isHypnotized, boolean inHouse, boolean fighting) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.direction = direction;
        this.isHypnotized = isHypnotized;
        this.inHouse = inHouse;
        this.fighting = fighting;

    }
    public String getType() {
        return type;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getHp() {
        return hp;
    }
    public int getDirection() {
        return direction;
    }
    public boolean isHypnotized() {
        return isHypnotized;
    }
    public boolean isInHouse() {
        return inHouse;
    }
    public boolean isFighting() {
        return fighting;
    }


}

