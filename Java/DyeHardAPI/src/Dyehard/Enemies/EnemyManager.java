package dyehard.Enemies;

import java.util.ArrayList;
import java.util.Random;

import Engine.Rectangle;
import Engine.Vector2;
import dyehard.Obstacles.ObstacleManager;
import dyehard.Player.Hero;
import dyehard.Util.Timer;
import dyehard.World.GameWorld;

public class EnemyManager extends Rectangle {
    // This time is in milliseconds
    private final float enemyFrequency = 12000f;
    private Hero hero;
    private ArrayList<Enemy> enemies;
    private ArrayList<Enemy> enemiesToRemove;
    private Timer timer;
    public static String enemySpeed;

    public EnemyManager(Hero hero) {
        this.hero = hero;
        enemies = new ArrayList<Enemy>();
        enemiesToRemove = new ArrayList<Enemy>();
        timer = new Timer(enemyFrequency);
        enemySpeed = "100%";
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
                enemiesToRemove.add(e);
            }
        }
        enemies.removeAll(enemiesToRemove);
        enemiesToRemove.clear();
        for (Enemy e : enemies) {
            e.update();
        }
        // generate new enemy
        if (timer.isDone()) {
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
            ObstacleManager.registerActor(enemies.get(enemies.size() - 1));
            timer.reset();
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
