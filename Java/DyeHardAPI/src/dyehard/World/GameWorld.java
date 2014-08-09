package dyehard.World;

import java.util.LinkedList;

import Engine.BaseCode;
import Engine.KeyboardInput;
import dyehard.CollisionManager;
import dyehard.UpdateManager;
import dyehard.Enemies.EnemyManager;
import dyehard.Player.Hero;

public class GameWorld {
    // private final float StartSpeed = 0.2f;
    public static final float LEFT_EDGE = BaseCode.world.getPositionX();
    public static final float RIGHT_EDGE = BaseCode.world.getWidth();
    public static final float TOP_EDGE = BaseCode.world.getHeight();
    public static final float BOTTOM_EDGE = BaseCode.world.getWorldPositionY();
    public static float Speed = 0.5f;
    private Hero hero;
    private EnemyManager eManager;
    private LinkedList<GameWorldRegion> gameRegions;

    public GameWorld(KeyboardInput keyboard) {
        gameRegions = new LinkedList<GameWorldRegion>();
    }

    public void initialize(Hero hero) {
        eManager = new EnemyManager(hero);
        this.hero = hero;
    }

    // Adds a region to the queue of upcoming regions
    // The region is initalized with the position of the last region currently
    // in the queue.
    public void addRegion(GameWorldRegion region) {
        float startLocation = 0f;
        if (!gameRegions.isEmpty()) {
            startLocation = gameRegions.getLast().rightEdge();
        }
        region.initialize(startLocation);
        gameRegions.add(region);
    }

    public boolean gameOver() {
        if (hero == null) {
            return false;
        }

        return !hero.isActive();
    }

    public void update() {
        if (hero == null) {
            System.err.println("The GameWorld was not initialized!");
        }

        for (GameWorldRegion e : gameRegions) {
            e.moveLeft();
        }
        updateSequence();

        UpdateManager.update();
        CollisionManager.update();
    }

    private void updateSequence() {
        // generate new game regions if the current one is about to end
        while (gameRegions.getLast().rightEdge() <= GameWorld.RIGHT_EDGE + 100f) {
            generateNewRegion();
        }

        // remove game regions that have moved off screen
        if (gameRegions.peek().rightEdge() <= GameWorld.LEFT_EDGE) {
            gameRegions.pop();
        }
    }

    // Generates regions that alternate between Stargate and Space
    protected void generateNewRegion() {
        GameWorldRegion newRegion;
        if (gameRegions.getLast() instanceof Space) {
            newRegion = new Stargate(hero, eManager.getEnemies());
        } else {
            newRegion = new Space(hero);
        }

        // the new region will start where the last region ends
        float startLocation = gameRegions.getLast().rightEdge();
        newRegion.initialize(startLocation);
        gameRegions.add(newRegion);
    }
}
