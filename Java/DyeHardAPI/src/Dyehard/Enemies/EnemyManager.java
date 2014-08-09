package dyehard.Enemies;

import java.util.ArrayList;
import java.util.Random;

import Engine.Vector2;
import dyehard.UpdateManager;
import dyehard.Updateable;
import dyehard.Player.Hero;
import dyehard.Util.Timer;
import dyehard.World.GameWorld;

public class EnemyManager implements Updateable {
    // This time is in milliseconds
    private final float enemyFrequency = 10000f;
    private Hero hero;
    private ArrayList<Enemy> enemies;
    private Timer timer;

    public EnemyManager(Hero hero) {
        this.hero = hero;
        UpdateManager.register(this);
        enemies = new ArrayList<Enemy>();
        timer = new Timer(enemyFrequency);
    }

    @Override
    public void update() {
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
            timer.reset();
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public boolean isActive() {
        // The enemy manager is always active
        return true;
    }
}
