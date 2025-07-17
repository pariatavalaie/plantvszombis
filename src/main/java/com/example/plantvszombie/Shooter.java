package com.example.plantvszombie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

abstract class Shooter extends Planet implements Act {
    ArrayList<Bullet> bullets;
    public Shooter(int x,int y) {
        super(x,y);
        bullets = new ArrayList<>();
    }
    @Override
    public void act(Pane root, ArrayList<Zombies> Zombies) {
        setActive(true);
        final double[]XZ={0};
        double x = Yard.GRID_X + getCol() * Yard.CELL_WIDTH+ (Yard.CELL_WIDTH - 70) / 2;
        double y = Yard.GRID_Y+ getRow() * Yard.Cell_HEIGHT + (Yard.Cell_HEIGHT - 90) / 2;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            boolean shouldShoot = false;
            for (Zombies z :Zombies ) {
                double zombieX = z.getImage().getLayoutX() + z.getImage().getTranslateX();
                if (z.getY() == getRow() && z.getX() >=getCol()&& z.getX() <=8) {
                    shouldShoot = true;
                    XZ[0] = zombieX; // نزدیک‌ترین زامبی
                    break;
                }
            }
            if (shouldShoot&&!isDead()) {
                shoot(root,x,XZ[0],y) ;
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        AnimationManager.register(timeline);
    }
    abstract void shoot(Pane root,double x,double xzombie,double y);

    @Override
    public PlanetState getState() {
        PlanetState s=super.getState();
        ArrayList<BulletState> bulletStates = new ArrayList<>();
        for (Bullet b : bullets) {
            bulletStates.add(new BulletState(b.x, b.y, b.speed, b.type, b.imageBullet.getTranslateX() + b.imageBullet.getLayoutX(), b.imageBullet.getTranslateY() + b.imageBullet.getLayoutY(), b.xzombie, b.hit));
        }
        return new ShooterState(s.col,s.row,s.type,s.health,s.dead,bulletStates,s.remainingCooldown, isActive());

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
