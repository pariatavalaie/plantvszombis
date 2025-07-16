package com.example.plantvszombie;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

abstract class Shooter extends Planet {
    ArrayList<Bullet> bullets;

    @Override
    public PlanetState getState() {
        PlanetState s=super.getState();
        ArrayList<BulletState> bulletStates = new ArrayList<>();
        for (Bullet b : bullets) {
            bulletStates.add(new BulletState(b.x, b.y, b.speed, b.type, b.imageBullet.getTranslateX() + b.imageBullet.getLayoutX(), b.imageBullet.getTranslateY() + b.imageBullet.getLayoutY(), b.xzombie, b.hit));
        }
        return new ShooterState(s.col,s.row,s.type,s.health,s.dead,bulletStates,s.remainingCooldown,active);

    }

    @Override
    public void loadpplanet(PlanetState planetState, Pane root) {
        super.loadpplanet(planetState, root);
        for (BulletState bullet :((ShooterState)planetState).bulletStates) {
            Bullet bullet1 = new Bullet(bullet.x, bullet.y, bullet.getSpeed(), bullet.getType());
            bullet1.shoot(root, bullet.getTranslateX(), bullet.getXzombie(), bullet.getTranslateY());
            this.bullets.add(bullet1);
        }
    }

    @Override
    String gettype() {
        return "";
    }
}
