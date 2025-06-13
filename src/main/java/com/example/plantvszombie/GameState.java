package com.example.plantvszombie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {
    private int sunPoints;
    private List<PlantState> plants;
    private List<ZombieState> zombies;
    private int gameTime;

    // Constructor, getters, and setters
    public GameState(int sunPoints, List<PlantState> plants, List<ZombieState> zombies, int gameTime) {
        this.sunPoints = sunPoints;
        this.plants = plants;
        this.zombies = zombies;
        this.gameTime = gameTime;
    }

    public int getSunPoints() {
        return sunPoints;
    }

    public List<PlantState> getPlants() {
        return plants;
    }

    public List<ZombieState> getZombies() {
        return zombies;
    }

    public int getGameTime() {
        return gameTime;
    }
}

class PlantState implements Serializable {
    private String type;
    private int row;
    private int col;
    private int health;

    public PlantState(String type, int row, int col, int health) {
        this.type = type;
        this.row = row;
        this.col = col;
        this.health = health;
    }

    // Getters
    public String getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getHealth() {
        return health;
    }
}

class ZombieState implements Serializable {
    private String type;
    private int x;
    private int y;
    private int hp;

    public ZombieState(String type, int x, int y, int hp) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.hp = hp;
    }

    // Getters
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
}