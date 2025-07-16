package com.example.plantvszombie;
import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameState implements Serializable {
    ArrayList<ZombieState> zombies;
    public Set<String> lockedCells;
    boolean day;
    int gameTime;
    int sunPoint;
    FogState fogState;
    ArrayList<SunState> suns;
    ArrayList<PlanetState>planets;
    ArrayList<stoneGraveState> StoneGraves;
    List<String> selected;
     GameState(ArrayList<ZombieState> zombies,  Set<String> lockedCells, boolean day, int gametime, int sunpoint,Fog fog,ArrayList<SunState>suns,ArrayList<PlanetState>planets,ArrayList<stoneGraveState>stoneGraves,List<String>selected) {
         this.zombies = zombies;
         this.lockedCells = lockedCells;
         this.day = day;
         this.gameTime = gametime;
         this.sunPoint = sunpoint;
         this.fogState= fog.buildState();
         this.suns = suns;
         this.planets = planets;
         this.StoneGraves = stoneGraves;
         this.selected = selected;
     }
     public int getGametime() {
         return gameTime;
     }
     public int getSunpoint() {
         return sunPoint;
     }
     public boolean isDay() {
         return day;
     }
     public FogState getFogState() {
         return fogState;
     }
     public ArrayList<SunState> getSuns() {
         return suns;
     }
     public ArrayList<PlanetState> getPlanets() {
         return planets;
     }
     public ArrayList<ZombieState> getZombies() {
         return zombies;
     }
     public List<String> getSelected() {
         return selected;
     }
     public ArrayList<stoneGraveState> getStoneGraves() {
         return StoneGraves;
     }
}

 class ZombieState implements Serializable {
    private String type;
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
    public double remainingCooldown;
    public boolean active;
    public PlanetState(int col, int row, String type, int health, boolean dead, double remainingCooldown,boolean active) {
        this.col = col;
        this.row = row;
        this.type = type;
        this.health = health;
        this.dead = dead;
        this.remainingCooldown = remainingCooldown;
        this.active = active;
    }
}
class OtherPlanetState extends PlanetState implements Serializable {
    boolean other;

    public OtherPlanetState(int col, int row, String type, int health, boolean dead, double remainingCooldown, boolean other,boolean active) {
        super(col, row, type, health, dead, remainingCooldown, active);
        this.other = other;
    }
    public boolean isOther() {
        return other;
    }

}
class ShooterState extends PlanetState implements Serializable {
    ArrayList<BulletState>bulletStates;
    ShooterState(int col, int row, String type, int health, boolean dead, ArrayList<BulletState> bulletStates, double remainingCooldown,boolean active) {
        super(col, row, type, health, dead, remainingCooldown,active);
        this.bulletStates = bulletStates;
    }

}
class scardyState extends ShooterState implements Serializable {
    boolean scardy;
    public scardyState(int col, int row, String type, int health, boolean dead, ArrayList<BulletState> bulletStates, double remainingCooldown,boolean scardy,boolean active) {
        super(col, row, type, health, dead,bulletStates,remainingCooldown,active);
        this.scardy = scardy;

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

    public BulletState(int x, int y, double speed, String type, double translateX, double translateY, double xzombie, boolean hit) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.type = type;
        this.translateX = translateX;
        this.translateY = translateY;
        this.xzombie = xzombie;
        this.hit = hit;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public double getSpeed() {
        return speed;
    }
    public String getType() {
        return type;
    }
    public double getTranslateX() {
        return translateX;
    }
    public double getTranslateY() {
        return translateY;
    }
    public double getXzombie() {
        return xzombie;
    }
    public boolean isHit() {
        return hit;
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
    public double getCenterX() {
        return centerX;
    }
    public double getCenterY() {
        return centerY;
    }
    public double getRadius() {
        return radius;
    }
}

 class SunState implements Serializable {
     public double x;
     public double y;
     public double z;
     public boolean isFalling;
     // true: fallingSun | false: sunflower

     public double getX() {
         return x;
     }
     public double getY() {
         return y;
     }
     public double getZ() {
         return z;
     }
 }
 class stoneGraveState implements Serializable {
     private int x;
     private int y;

     public stoneGraveState(int x, int y) {
         this.x = x;
         this.y = y;
     }
     public int getX() {
         return x;
     }
     public int getY() {
         return y;
     }
     public StoneGrave getStoneGrave(Pane pane) {
         return new StoneGrave(getX(),getY(),pane);
     }
 }




