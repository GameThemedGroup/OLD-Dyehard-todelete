package Dyehard;

import java.util.ArrayList;
import java.util.List;

import BaseTypes.Character;
import BaseTypes.DyePack;
import BaseTypes.Enemy;
import BaseTypes.Obstacle;
import BaseTypes.PowerUp;
import Dyehard.Player.Hero;
import Dyehard.World.GameWorld;
import Engine.Primitive;
import Engine.Vector2;

public class Space {
    Character hero;
    List<Primitive> primitives;
    List<Obstacle> obstacles;
    List<PowerUp> powerups;
    List<Character> characters;
    List<DyePack> dyepacks;
    private static final int TimeToSpawnDebris = 40;
    int debrisTimer = 0;

    public Space(Hero hero) {
        characters = new ArrayList<Character>();
        this.hero = hero;
        characters.add(hero);
        obstacles = new ArrayList<Obstacle>();
        powerups = new ArrayList<PowerUp>();
        dyepacks = new ArrayList<DyePack>();
        primitives = new ArrayList<Primitive>();
    }

    public void AddPrimitive(Primitive primitive) {
        primitives.add(primitive);
    }

    public void AddEnemy(Enemy enemy) {
        primitives.add(enemy);
        characters.add(enemy);
    }

    public void AddDyepack(DyePack dyepack) {
        primitives.add(dyepack);
    }

    public void AddPowerup(PowerUp powerup) {
        primitives.add(powerup);
    }

    public void update() {
        updateTimers();
        for (Primitive p : primitives) {
            p.update();
        }
        for (int i = primitives.size() - 1; i >= 0; --i) {
            if (primitives.get(i).visible == false) {
                primitives.remove(i);
            }
        }
    }

    private void updateTimers() {
        debrisTimer++;
        if (debrisTimer >= TimeToSpawnDebris) {
            generateDebris();
            debrisTimer = 0;
        }
    }

    // Increments the timer used to generate obstacles
    // If the timer's duration has been reached, an obstacle is generated and
    // added to the list of primitives
    public void generateDebris() {
        float startY = (float) (Math.random() * 80 + 5);
        Vector2 position = new Vector2(100, startY);
        Vector2 size = new Vector2(5, 5);
        Vector2 speed = new Vector2(-GameWorld.Speed, 0);
        primitives.add(new Obstacle(characters, position, size, speed));
    }
}
