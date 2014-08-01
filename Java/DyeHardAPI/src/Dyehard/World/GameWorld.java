package dyehard.World;

import java.util.LinkedList;

import dyehard.DeveloperControls;
import dyehard.Enemies.EnemyManager;
import dyehard.Obstacles.ObstacleManager;
import dyehard.Player.Hero;



import Engine.BaseCode;
import Engine.KeyboardInput;
import Engine.Vector2;

public class GameWorld {
    // private final float StartSpeed = 0.2f;
    public static final float LEFT_EDGE = BaseCode.world.getPositionX();
    public static final float RIGHT_EDGE = BaseCode.world.getWidth();
    public static final float TOP_EDGE = BaseCode.world.getHeight();
    public static final float BOTTOM_EDGE = BaseCode.world.getWorldPositionY();
    public static float Speed = 0.5f;
    public static Vector2 Gravity = new Vector2(0, -0.01f);
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
        gameRegions.add(new Space(hero, eManager.getEnemies(),
                GameWorld.LEFT_EDGE));
        gameRegions.add(new Stargate(hero, eManager.getEnemies(), 300f));

        // fill the rest of the existing screen
        while (gameRegions.getLast().rightEdge() <= GameWorld.RIGHT_EDGE) {
            generateNewRegion();
        }

        dev = new DeveloperControls(this, space, hero, keyboard, eManager,
                gameRegions);
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
        // remove game regions that have moved off screen
        if (gameRegions.peek().rightEdge() <= GameWorld.LEFT_EDGE) {
            GameWorldRegion offscreen = gameRegions.pop();
            offscreen.destroy();
        }

        // generate new game regions if the current one is about to end
        if (gameRegions.getLast().rightEdge() <= GameWorld.RIGHT_EDGE + 100f) {
            generateNewRegion();
        }
    }

    private void generateNewRegion() {
        // the new region will start where the last region ends
        float startLocation = gameRegions.getLast().rightEdge();
        GameWorldRegion newRegion;
        if (gameRegions.getLast() instanceof Stargate) {
            newRegion = new Space(hero, eManager.getEnemies(), startLocation);
        } else {
            newRegion = new Stargate(hero, eManager.getEnemies(), startLocation);
        }

        gameRegions.add(newRegion);
    }
}
