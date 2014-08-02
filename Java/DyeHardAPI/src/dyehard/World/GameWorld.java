package dyehard.World;

import java.util.LinkedList;

import Engine.BaseCode;
import Engine.KeyboardInput;
import dyehard.DeveloperControls;
import dyehard.Enemies.EnemyManager;
import dyehard.Obstacles.ObstacleManager;
import dyehard.Player.Hero;

public class GameWorld {
    // private final float StartSpeed = 0.2f;
    public static final float LEFT_EDGE = BaseCode.world.getPositionX();
    public static final float RIGHT_EDGE = BaseCode.world.getWidth();
    public static final float TOP_EDGE = BaseCode.world.getHeight();
    public static final float BOTTOM_EDGE = BaseCode.world.getWorldPositionY();
    public static float Speed = 0.5f;
    private Hero hero;
    private DeveloperControls dev;
    private Space space;
    private EnemyManager eManager;
    private LinkedList<GameWorldRegion> gameRegions;

    public GameWorld(KeyboardInput keyboard) {
        hero = new Hero(keyboard);
        ObstacleManager.registerActor(hero);
        eManager = new EnemyManager(hero);
        gameRegions = new LinkedList<GameWorldRegion>();
        hero.setEnemies(eManager.getEnemies());

        // first element on screen
        GameWorldRegion startingSpace = new Space(hero);
        startingSpace.initialize(0f);
        gameRegions.add(startingSpace);

        // fill the rest of the existing screen
        while (gameRegions.getLast().rightEdge() <= GameWorld.RIGHT_EDGE) {
            generateNewRegion();
        }

        dev = new DeveloperControls(this, space, hero, keyboard, eManager,
                gameRegions);
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
        return !hero.isAlive();
    }

    public void update() {
        updateSequence();

        hero.update();
        dev.update();
        eManager.update();
        ObstacleManager.update();

        for (GameWorldRegion e : gameRegions) {
            e.update();
        }
    }

    private void updateSequence() {
        // generate new game regions if the current one is about to end
        if (gameRegions.getLast().rightEdge() <= GameWorld.RIGHT_EDGE + 100f) {
            generateNewRegion();
        }

        // remove game regions that have moved off screen
        if (gameRegions.peek().rightEdge() <= GameWorld.LEFT_EDGE) {
            GameWorldRegion offscreen = gameRegions.pop();
            offscreen.destroy();
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
