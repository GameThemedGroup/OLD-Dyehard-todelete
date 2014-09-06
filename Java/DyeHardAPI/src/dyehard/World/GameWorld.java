package dyehard.World;

import java.util.LinkedList;

import Engine.BaseCode;
import dyehard.ManagerState;
import dyehard.UpdateManager;
import dyehard.UpdateManager.Updateable;
import dyehard.Background.Background;
import dyehard.Background.DyehardUI;
import dyehard.Enemies.EnemyManager;
import dyehard.Obstacles.Laser;
import dyehard.Player.Hero;

public class GameWorld implements Updateable {
    // private final float StartSpeed = 0.2f;
    public static final float LEFT_EDGE = BaseCode.world.getPositionX();
    public static final float RIGHT_EDGE = BaseCode.world.getWidth();
    public static final float TOP_EDGE = BaseCode.world.getHeight();
    public static final float BOTTOM_EDGE = BaseCode.world.getWorldPositionY();
    public static float Speed = 0.3f;
    float distance = 0f;
    private Hero hero;
    private LinkedList<GameWorldRegion> gameRegions;

    public GameWorld() {
        gameRegions = new LinkedList<GameWorldRegion>();
    }

    public void initialize(Hero hero) {
        new EnemyManager(hero);
        this.hero = hero;

        new Background();
        new DyehardUI(hero);
        new Laser(hero);

        // Draw the hero on top of the background
        hero.removeFromAutoDrawSet();
        hero.addToAutoDrawSet();

        UpdateManager.register(this);

        addRegion(new Space(hero));
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

        return GameState.RemainingLives <= 0;
    }

    @Override
    public void update() {
        if (hero == null) {
            System.err.println("The GameWorld was not initialized!");
        }

        for (GameWorldRegion e : gameRegions) {
            e.moveLeft();
            distance += Speed;
            GameState.DistanceTravelled = (int) distance;
        }
        updateSequence();
    }

    @Override
    public ManagerState updateState() {
        return ManagerState.ACTIVE;
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
            newRegion = new Stargate(hero);
        } else {
            newRegion = new Space(hero);
        }

        // the new region will start where the last region ends
        float startLocation = gameRegions.getLast().rightEdge();
        newRegion.initialize(startLocation);
        gameRegions.add(newRegion);
    }

    public boolean nextRegionIsSpace() {
        if (gameRegions.size() > 1 && gameRegions.get(1) instanceof Space) {
            return true;
        }
        return false;
    }

    public boolean nextRegionIsStargate() {
        if (gameRegions.size() > 1 && gameRegions.get(1) instanceof Stargate) {
            return true;
        }
        return false;
    }
}
