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
    ArrayList<PlanetState>planets;
     GameState(ArrayList<ZombieState> zombies, List<String> selected, boolean day, int gametime, int sunpoint,Fog fog,ArrayList<SunState>suns,ArrayList<PlanetState>planets) {
         this.zombies = zombies;
         this.selected = selected;
         this.day = day;
         this.gametime = gametime;
         this.sunpoint = sunpoint;
         this.fogState= fog.buildState();
         this.suns = suns;
         this.planets = planets;



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
    public int col, row;
    public String type;
    public int health;
    public boolean dead;
    ArrayList<BulletState>bulletStates;
    public double remainingCooldown;
    public PlanetState(int col, int row, String type, int health, boolean dead,ArrayList<BulletState>bulletStates, double remainingCooldown) {
        this.col = col;
        this.row = row;
        this.type = type;
        this.health = health;
        this.dead = dead;
        this.bulletStates = bulletStates;
        this.remainingCooldown = remainingCooldown;
    }
}
class OtherPlanetState extends PlanetState implements Serializable {
    boolean other;
    public OtherPlanetState(int col, int row, String type, int health, boolean dead,ArrayList<BulletState>bulletStates, double remainingCooldown,boolean other) {
        super(col, row, type, health, dead, bulletStates, remainingCooldown);
        other = this.other;
    }

}


class BulletState implements Serializable {
    public int x;
    public int y;
    public double speed;
    public String type;
    public double translateX;
    public double translateY;
    public double xzombie;
    public boolean hit;

    public BulletState(int x, int y, double speed, String type, double translateX, double translateY,double xzombie ,boolean hit) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.type = type;
        this.translateX = translateX;
        this.translateY = translateY;
        this.xzombie = xzombie;
        this.hit = hit;
    }
     }

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



