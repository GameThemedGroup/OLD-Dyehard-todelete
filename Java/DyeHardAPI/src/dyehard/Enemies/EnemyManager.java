package dyehard.Enemies;

import GameWorld;
import UpdateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Engine.Vector2;
import dyehard.Configuration;
import dyehard.UpdateObject;
import dyehard.Player.Hero;
import dyehard.Util.Timer;

public class EnemyManager extends UpdateObject {
    // This time is in milliseconds
    private static final float enemyFrequency = Configuration.worldEnemyFrequency * 1000f;
    private static Hero hero;
    private static List<Enemy> enemies;
    private static Timer timer;
    private static boolean isInstantiated = false;
    private static Random RANDOM = new Random();

    static {
        enemies = new ArrayList<Enemy>();
        timer = new Timer(enemyFrequency);
    }

    public EnemyManager(Hero hero) {
        if (isInstantiated) {
            throw new IllegalStateException(
                    "Only one Enemy Manager can instantiated.");
        }

        UpdateManager.register(this);
        EnemyManager.hero = hero;
    }

    @Override
    public void update() {
        // generate new enemy
        if (timer.isDone()) {
            generateEnemy();
        }
    }

    public static void generateEnemy() {
        // TODO: Replace magic numbers
        float randomY = RANDOM.nextInt((int) GameWorld.TOP_EDGE - 8) + 5;
        Vector2 position = new Vector2(GameWorld.RIGHT_EDGE + 10, randomY);
        switch (RANDOM.nextInt(3)) {
        case 1:
            enemies.add(new PortalEnemy(position, hero));
            break;
        case 2:
            enemies.add(new ChargerEnemy(position, hero));
            break;
        default:
            enemies.add(new RegularEnemy(position, hero));
            break;
        }
        enemies.get(enemies.size() - 1).initialize();
        timer.reset();
    }

    public static List<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public void setSpeed(float v) {
        // TODO Auto-generated method stub

    }
}
