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
    FogState fogState;
    ArrayList<SunState> suns;
     GameState(ArrayList<ZombieState> zombies, List<String> selected, boolean day, int gametime, int sunpoint,Fog fog,ArrayList<SunState>suns) {
         this.zombies = zombies;
         this.selected = selected;
         this.day = day;
         this.gametime = gametime;
         this.sunpoint = sunpoint;
         this.fogState= fog.buildState();
         this.suns = suns;



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
class PlanetState implements Serializable {
    private int x, y;
    private String type;
    private int health;
    private double cooldown;
    ArrayList<Bullet> bullets;
    private ArrayList<BulletState>bulletStates;
    private boolean other;
    PlanetState(int x, int y, String type, int health, ArrayList<Bullet> bullets,boolean other) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.health = health;
        this.bullets = bullets;
        this.other = other;
    }


}
class BulletState{}

 class FogState implements Serializable {
    public double currentTranslateX;
    public boolean isVisible;
    public List<LanternHoleState> holes = new ArrayList<>();
}
class LanternHoleState implements Serializable {
    public double centerX;
    public double centerY;
    public double radius;

    public LanternHoleState(double x, double y, double r) {
        this.centerX = x;
        this.centerY = y;
        this.radius = r;
    }
}

 class SunState implements Serializable {
    public double x;
    public double y;
    public double z;
    public boolean isFalling; // true: fallingSun | false: sunflower
}



