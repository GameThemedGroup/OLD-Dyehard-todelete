package Dyehard.World;

import Dyehard.DeveloperControls;
import Dyehard.Space;
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

    public GameWorld(KeyboardInput keyboard) {
        hero = new Hero(keyboard);
        space = new Space(hero);
        space.AddPrimitive(hero);
        dev = new DeveloperControls(this, space, hero, keyboard);
    }

    public boolean gameOver() {
        return !hero.isAlive();
    }

    public void update() {
        hero.update();
        dev.update();
        space.update();
    }
}
