package Dyehard.World;

import java.util.LinkedList;

import Dyehard.DeveloperControls;
import Dyehard.Space;
import Dyehard.Enemies.EnemyManager;
import Dyehard.Player.Hero;
import Engine.KeyboardInput;
import Engine.Vector2;

public class GameWorld {
    // private final float StartSpeed = 0.2f;
    public static float Speed = .5f;
    public static Vector2 Gravity = new Vector2(0, -0.01f);
    private Hero hero;
    private DeveloperControls dev;
    private Space space;
    private EnemyManager eManager;
    private LinkedList<GameWorldRegion> onscreen;
    private LinkedList<GameWorldRegion> upcoming;
    public static final float leftEdge = 0f;
    public static final float rightEdge = 100f;

    public GameWorld(KeyboardInput keyboard) {
        hero = new Hero(keyboard);
        eManager = new EnemyManager(hero);
        onscreen = new LinkedList<GameWorldRegion>();
        upcoming = new LinkedList<GameWorldRegion>();
        // set the enemy manager for the weapon
        hero.setEnemies(eManager.getEnemies());
        // first element on screen
        onscreen.addLast(new Space(hero, eManager.getEnemies(), leftEdge));
        // fill the rest of the exisiting screen
        while (onscreen.getLast().rightEdge() <= rightEdge) {
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
        hero.update();
        dev.update();
        eManager.update();
        updateSequence();
        for (GameWorldRegion e : onscreen) {
            e.update();
        }
    }

    private void updateSequence() {
        if (onscreen.getFirst().isOffScreen()) {
            // remove off screen element
            onscreen.removeFirst().destroy();
        }
        if (onscreen.getLast().rightEdge() <= rightEdge) {
            // move item from upcoming to end of onscreen
            upcoming.addLast(nextElement(upcoming));
            onscreen.addLast(upcoming.removeFirst());
        }
    }

    private GameWorldRegion nextElement(LinkedList<GameWorldRegion> seq) {
        return new Space(hero, eManager.getEnemies(), seq.getLast().rightEdge());
    }
}
