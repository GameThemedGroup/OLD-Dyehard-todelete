package Dyehard.World;

import Dyehard.DeveloperControls;
import Dyehard.GameObject;
import Dyehard.Player.Hero;
import Engine.KeyboardInput;
import Engine.Vector2;

public class GameWorld extends GameObject {
    // private final float StartSpeed = 0.2f;
    public static float Speed;
    public static Vector2 Gravity = new Vector2(0, -0.02f);
    // private float SpeedReference;
    // private boolean stop;
    // private Timer accelerationTimer;
    //
    // private Queue<GameWorldRegion> onscreen;
    // private Queue<GameWorldRegion> upcoming;
    private Hero hero;
    private DeveloperControls dev;

    // private EnemyManager eManager;
    // private InfoPanel infoPanel;
    // private Laser laser;
    public GameWorld(KeyboardInput keyboard) {
        hero = new Hero(keyboard);
        dev = new DeveloperControls(keyboard, hero);
    }

    @Override
    public void draw() {
        hero.draw();
    }

    public boolean gameOver() {
        return !hero.isAlive();
    }

    @Override
    public void remove() {
        hero.remove();
    }

    @Override
    public void update() {
        hero.update();
        dev.update();
    }
}
