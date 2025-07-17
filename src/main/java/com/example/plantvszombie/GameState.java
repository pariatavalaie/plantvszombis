package com.example.plantvszombie;
import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameState implements Serializable {
    private ArrayList<ZombieState> zombies;
    private Set<String> lockedCells;
    private boolean day;
    private int gameTime;
    private int sunPoint;
    private FogState fogState;
    private ArrayList<SunState> suns;
    private ArrayList<PlanetState>planets;
    private ArrayList<stoneGraveState> StoneGraves;
    private List<String> selected;
     GameState(ArrayList<ZombieState> zombies,  Set<String> lockedCells, boolean day, int gametime, int sunpoint,Fog fog,ArrayList<SunState>suns,ArrayList<PlanetState>planets,ArrayList<stoneGraveState>stoneGraves,List<String>selected) {
         this.setZombies(zombies);
         this.setLockedCells(lockedCells);
         this.setDay(day);
         this.setGameTime(gametime);
         this.setSunPoint(sunpoint);
         this.setFogState(fog.buildState());
         this.setSuns(suns);
         this.setPlanets(planets);
         this.setStoneGraves(stoneGraves);
         this.setSelected(selected);
     }
     public int getGametime() {
         return getGameTime();
     }
     public int getSunpoint() {
         return getSunPoint();
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

    public void setZombies(ArrayList<ZombieState> zombies) {
        this.zombies = zombies;
    }

    public Set<String> getLockedCells() {
        return lockedCells;
    }

    public void setLockedCells(Set<String> lockedCells) {
        this.lockedCells = lockedCells;
    }

    public void setDay(boolean day) {
        this.day = day;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public int getSunPoint() {
        return sunPoint;
    }

    public void setSunPoint(int sunPoint) {
        this.sunPoint = sunPoint;
    }

    public void setFogState(FogState fogState) {
        this.fogState = fogState;
    }

    public void setSuns(ArrayList<SunState> suns) {
        this.suns = suns;
    }

    public void setPlanets(ArrayList<PlanetState> planets) {
        this.planets = planets;
    }

    public void setStoneGraves(ArrayList<stoneGraveState> stoneGraves) {
        StoneGraves = stoneGraves;
    }

    public void setSelected(List<String> selected) {
        this.selected = selected;
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
    private int col;
     private int row;
    private String type;
    private int health;
    private boolean dead;
    private double remainingCooldown;
    private boolean active;
    public PlanetState(int col, int row, String type, int health, boolean dead, double remainingCooldown,boolean active) {
        this.setCol(col);
        this.setRow(row);
        this.setType(type);
        this.setHealth(health);
        this.setDead(dead);
        this.setRemainingCooldown(remainingCooldown);
        this.setActive(active);
    }

     public int getCol() {
         return col;
     }

     public void setCol(int col) {
         this.col = col;
     }

     public int getRow() {
         return row;
     }

     public void setRow(int row) {
         this.row = row;
     }

     public String getType() {
         return type;
     }

     public void setType(String type) {
         this.type = type;
     }

     public int getHealth() {
         return health;
     }

     public void setHealth(int health) {
         this.health = health;
     }

     public boolean isDead() {
         return dead;
     }

     public void setDead(boolean dead) {
         this.dead = dead;
     }

     public double getRemainingCooldown() {
         return remainingCooldown;
     }

     public void setRemainingCooldown(double remainingCooldown) {
         this.remainingCooldown = remainingCooldown;
     }

     public boolean isActive() {
         return active;
     }

     public void setActive(boolean active) {
         this.active = active;
     }
 }
class OtherPlanetState extends PlanetState implements Serializable {
    private boolean other;

    public OtherPlanetState(int col, int row, String type, int health, boolean dead, double remainingCooldown, boolean other,boolean active) {
        super(col, row, type, health, dead, remainingCooldown, active);
        this.setOther(other);
    }
    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }
}
class ShooterState extends PlanetState implements Serializable {
    private ArrayList<BulletState>bulletStates;
    ShooterState(int col, int row, String type, int health, boolean dead, ArrayList<BulletState> bulletStates, double remainingCooldown,boolean active) {
        super(col, row, type, health, dead, remainingCooldown,active);
        this.setBulletStates(bulletStates);
    }

    public ArrayList<BulletState> getBulletStates() {
        return bulletStates;
    }

    public void setBulletStates(ArrayList<BulletState> bulletStates) {
        this.bulletStates = bulletStates;
    }
}
class scardyState extends ShooterState implements Serializable {
    private boolean scardy;
    public scardyState(int col, int row, String type, int health, boolean dead, ArrayList<BulletState> bulletStates, double remainingCooldown,boolean scardy,boolean active) {
        super(col, row, type, health, dead,bulletStates,remainingCooldown,active);
        this.setScardy(scardy);

    }

    public boolean isScardy() {
        return scardy;
    }

    public void setScardy(boolean scardy) {
        this.scardy = scardy;
    }
}


class BulletState implements Serializable {
    private int x;
    private int y;
    private double speed;
    private String type;
    private double translateX;
    private double translateY;
    private double xzombie;
    private boolean hit;

    public BulletState(int x, int y, double speed, String type, double translateX, double translateY, double xzombie, boolean hit) {
        this.setX(x);
        this.setY(y);
        this.setSpeed(speed);
        this.setType(type);
        this.setTranslateX(translateX);
        this.setTranslateY(translateY);
        this.setXzombie(xzombie);
        this.setHit(hit);
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTranslateX(double translateX) {
        this.translateX = translateX;
    }

    public void setTranslateY(double translateY) {
        this.translateY = translateY;
    }

    public void setXzombie(double xzombie) {
        this.xzombie = xzombie;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }
}

 class FogState implements Serializable {
    private double currentTranslateX;
    private boolean isVisible;
    public List<LanternHoleState> holes = new ArrayList<>();

     public double getCurrentTranslateX() {
         return currentTranslateX;
     }

     public void setCurrentTranslateX(double currentTranslateX) {
         this.currentTranslateX = currentTranslateX;
     }

     public boolean isVisible() {
         return isVisible;
     }

     public void setVisible(boolean visible) {
         isVisible = visible;
     }
 }
class LanternHoleState implements Serializable {
    private double centerX;
    private double centerY;
    private double radius;

    public LanternHoleState(double x, double y, double r) {
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(r);
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

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}

 class SunState implements Serializable {
     private double x;
     private double y;
     private double z;
     private boolean isFalling;
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

     public void setX(double x) {
         this.x = x;
     }

     public void setY(double y) {
         this.y = y;
     }

     public void setZ(double z) {
         this.z = z;
     }

     public boolean isFalling() {
         return isFalling;
     }

     public void setFalling(boolean falling) {
         isFalling = falling;
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




