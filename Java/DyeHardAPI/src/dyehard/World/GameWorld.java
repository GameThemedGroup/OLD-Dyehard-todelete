package dyehard.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import Engine.BaseCode;
import Engine.KeyboardInput;
import dyehard.Updateable;
import dyehard.Background.Background;
import dyehard.Collectibles.Collectible;
import dyehard.Enemies.Enemy;
import dyehard.Obstacles.Laser;
import dyehard.Obstacles.Obstacle;
import dyehard.Player.Hero;
import dyehard.Weapons.Projectile;

public class GameWorld {
    // private final float StartSpeed = 0.2f;
    public static final float LEFT_EDGE = BaseCode.world.getPositionX();
    public static final float RIGHT_EDGE = BaseCode.world.getWidth();
    public static final float TOP_EDGE = BaseCode.world.getHeight();
    public static final float BOTTOM_EDGE = BaseCode.world.getWorldPositionY();
    public static float Speed = .1f; // 0.3f;

    public static LinkedList<GameWorldRegion> gameRegions;

    private static Hero hero;
    private static List<Enemy> enemies;
    private static List<Projectile> projectiles;
    private static List<Collectible> collectibles;
    private static List<Obstacle> obstacles;

    private static Set<Updateable> updateables;
    private static Set<Updateable> newlyRegisteredUpdateables;

    public static Hero getHero() {
        return hero;
    }

    public static List<Enemy> getEnemies() {
        return enemies;
    }

    public static List<Projectile> getProjectiles() {
        return projectiles;
    }

    public static List<Collectible> getCollectible() {
        return collectibles;
    }

    public static List<Obstacle> getObstacles() {
        return obstacles;
    }

    static {
        collectibles = new ArrayList<Collectible>();
        projectiles = new ArrayList<Projectile>();
        enemies = new ArrayList<Enemy>();
        obstacles = new ArrayList<Obstacle>();

        updateables = new HashSet<Updateable>();
        newlyRegisteredUpdateables = new HashSet<Updateable>();

        gameRegions = new LinkedList<GameWorldRegion>();
    }

    public GameWorld(KeyboardInput keyboard) {
        new Background();
        hero = new Hero();

        new Laser(hero);

        // first element on screen
        gameRegions.add(new Space(GameWorld.LEFT_EDGE));
        // gameRegions.add(new Stargate(hero, enemies, 300f));

        // fill the rest of the existing screen
        while (gameRegions.getLast().rightEdge() <= GameWorld.RIGHT_EDGE) {
            generateNewRegion();
        }
    }

    public static void registerUpdateable(Updateable updateable) {
        if (updateable instanceof Enemy) {
            enemies.add((Enemy) updateable);
        } else if (updateable instanceof Projectile) {
            projectiles.add((Projectile) updateable);
        } else if (updateable instanceof Collectible) {
            collectibles.add((Collectible) updateable);
        } else if (updateable instanceof Obstacle) {
            obstacles.add((Obstacle) updateable);
        }

        newlyRegisteredUpdateables.add(updateable);
    }

    public boolean gameOver() {
        return !hero.isAlive();
    }

    public static void update() {

        for (Updateable o : updateables) {
            if (o != null && o.isActive()) {
                o.update();
            }
        }

        updateables.addAll(newlyRegisteredUpdateables);
        newlyRegisteredUpdateables.clear();

        updateSequence();
    }

    public static void pruneObjects() {
        Set<Updateable> destroyed = new HashSet<Updateable>();
        for (Updateable o : updateables) {
            if (o == null || o.isActive() == false) {
                destroyed.add(o);
            }
        }

        updateables.removeAll(destroyed);
    }

    public static GameWorldRegion getLastRegion() {
        return gameRegions.peekLast();
    }

    private static void updateSequence() {
        // remove game regions that have moved off screen
        if (gameRegions.peek().rightEdge() <= GameWorld.LEFT_EDGE) {
            gameRegions.pop();
        }

        // generate new game regions if the current one is about to end
        if (gameRegions.getLast().rightEdge() <= GameWorld.RIGHT_EDGE + 100f) {
            generateNewRegion();
        }
    }

    private static void generateNewRegion() {
        // the new region will start where the last region ends
        float startLocation = gameRegions.getLast().rightEdge();
        GameWorldRegion newRegion;
        if (gameRegions.getLast() instanceof Stargate) {
            newRegion = new Space(startLocation);
        } else {
            newRegion = new Stargate(hero, enemies, startLocation);
        }

        gameRegions.add(newRegion);
    }
}
