package Dyehard.World;

import java.util.LinkedList;

import Dyehard.DeveloperControls;
import Dyehard.Space;
import Dyehard.Enemies.EnemyManager;
import Dyehard.Obstacles.Stargate;
import Dyehard.Player.Hero;
import Engine.BaseCode;
import Engine.KeyboardInput;
import Engine.Vector2;

public class GameWorld {
    // private final float StartSpeed = 0.2f;
    public static final float LEFT_EDGE = BaseCode.world.getPositionX();
    public static final float RIGHT_EDGE = BaseCode.world.getWidth();
    public static final float TOP_EDGE = BaseCode.world.getHeight();
    public static final float BOTTOM_EDGE = BaseCode.world.getWorldPositionY();
    public static float Speed = .5f;
    public static Vector2 Gravity = new Vector2(0, -0.01f);
    private Hero hero;
    private DeveloperControls dev;
    private Space space;
    private EnemyManager eManager;
    private LinkedList<GameWorldRegion> onscreen;
    private LinkedList<GameWorldRegion> upcoming;

    public GameWorld(KeyboardInput keyboard) {
        hero = new Hero(keyboard);
        eManager = new EnemyManager(hero);
        onscreen = new LinkedList<GameWorldRegion>();
        upcoming = new LinkedList<GameWorldRegion>();
        // set the enemy manager for the weapon
        hero.setEnemies(eManager.getEnemies());
        // first element on screen
        onscreen.addLast(new Space(hero, eManager.getEnemies(),
                GameWorld.LEFT_EDGE));
        // fill the rest of the existing screen
        while (onscreen.getLast().rightEdge() <= GameWorld.RIGHT_EDGE) {
            onscreen.addLast(nextElement(onscreen));
        }
        // prep upcoming elements
        upcoming.addLast(nextElement(onscreen));
        dev = new DeveloperControls(this, space, hero, keyboard, eManager,
                onscreen);
    }

    public boolean gameOver() {
        return !hero.isAlive();
    }

    public void update() {
        updateSequence();
        hero.update();
        dev.update();
        eManager.update();
        for (GameWorldRegion e : onscreen) {
            e.update();
        }
    }

    private void updateSequence() {
        if (onscreen.getFirst().isOffScreen()) {
            // remove off screen element
            onscreen.removeFirst().destroy();
        }
        if (onscreen.getLast().rightEdge() <= GameWorld.RIGHT_EDGE) {
            // move item from upcoming to end of onscreen
            upcoming.addLast(nextElement(upcoming));
            onscreen.addLast(upcoming.removeFirst());
        }
    }

    private GameWorldRegion nextElement(LinkedList<GameWorldRegion> seq) {
        if (seq.getLast() instanceof Stargate) {
            return new Space(hero, eManager.getEnemies(), seq.getLast()
                    .rightEdge());
        } else {
            return new Stargate(hero, eManager.getEnemies(), seq.getLast()
                    .rightEdge());
        }
    }
}
