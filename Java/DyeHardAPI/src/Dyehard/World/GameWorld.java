package Dyehard.World;

import java.util.ArrayList;
import java.util.List;

import Dyehard.DeveloperControls;
import Dyehard.Character;
import Dyehard.GameObject;
import Dyehard.Obstacles.Obstacle;
import Dyehard.Player.Hero;
import Engine.KeyboardInput;
import Engine.Primitive;
import Engine.Vector2;

public class GameWorld extends GameObject {
    // private final float StartSpeed = 0.2f;
    public static float Speed = .05f;
    public static Vector2 Gravity = new Vector2(0, -0.02f);
    private Hero hero;
    private DeveloperControls dev;
    private List<Primitive> primitives;
    private List<Character> characters;

    public GameWorld(KeyboardInput keyboard) {
        hero = new Hero(keyboard);
        dev = new DeveloperControls(keyboard, hero);
        characters = new ArrayList<Character>();
        characters.add(hero);
        primitives = new ArrayList<Primitive>();
        primitives.add(new Obstacle(characters, new Vector2(50, 5),
                new Vector2(5, 5), new Vector2(-Speed, 0)));
        primitives.add(new Obstacle(characters, new Vector2(55, 25),
                new Vector2(5, 5), new Vector2(-Speed, 0)));
        primitives.add(new Obstacle(characters, new Vector2(60, 40),
                new Vector2(5, 5), new Vector2(-Speed, 0)));
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
        for (Primitive p : primitives) {
            p.update();
        }
        for (int i = primitives.size() - 1; i >= 0; --i) {
            if (primitives.get(i).visible == false) {
                primitives.remove(i);
            }
        }
    }
}
