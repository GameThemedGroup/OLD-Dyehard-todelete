package Dyehard.Enemies;

import java.util.ArrayList;
import java.util.Random;

import BaseTypes.Enemy;
import Dyehard.Player.Hero;
import Dyehard.World.GameWorld;
import Engine.Rectangle;
import Engine.Vector2;

public class EnemyManager extends Rectangle {
    private final float enemyFrequency = 12f;
    private Hero hero;
    private ArrayList<Enemy> enemies;
    private long startTime;

    public EnemyManager(Hero hero) {
        this.hero = hero;
        enemies = new ArrayList<Enemy>();
        startTime = System.nanoTime();
    }

    @Override
    public void destroy() {
        for (Enemy e : enemies) {
            e.destroy();
        }
    }

    @Override
    public void update() {
        // remove any dead enemies
        for (Enemy e : enemies) {
            if (!e.isAlive()) {
                e.destroy();
                enemies.remove(e);
            }
        }
        for (Enemy e : enemies) {
            e.update();
        }
        // generate new enemy
        if (System.nanoTime() >= startTime + (enemyFrequency * 1000000000)
                && GameWorld.Speed != 0) {
            Random rand = new Random();
            // TODO: Replace magic numbers
            float randomY = (GameWorld.TOP_EDGE - 5f - GameWorld.BOTTOM_EDGE + 5f)
                    * rand.nextFloat() + 0f + 5f;
            Vector2 position = new Vector2(GameWorld.RIGHT_EDGE + 5, randomY);
            switch (rand.nextInt(3)) {
            case 1:
                enemies.add(new BrainEnemy(position, 7.5f, hero));
                break;
            case 2:
                enemies.add(new RedBeamEnemy(position, 7.5f, hero));
                break;
            default:
                enemies.add(new SpiderEnemy(position, 7.5f, hero));
                break;
            }
            startTime = System.nanoTime();
        } else if (GameWorld.Speed == 0) {
            startTime = System.nanoTime();
        }
    }

    @Override
    public void draw() {
        for (Enemy e : enemies) {
            e.draw();
        }
    }

    public void killAll() {
        for (Enemy e : enemies) {
            e.destroy();
        }
        enemies.clear();
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
