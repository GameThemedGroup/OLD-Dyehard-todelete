package Dyehard;

import java.util.ArrayList;
import java.util.List;

import BaseTypes.Actor;
import BaseTypes.DyePack;
import BaseTypes.Enemy;
import BaseTypes.PowerUp;
import Dyehard.Obstacles.Obstacle;
import Dyehard.Player.Hero;
import Dyehard.World.GameWorld;
import Dyehard.World.GameWorldRegion;
import Engine.Primitive;
import Engine.Vector2;

public class Space extends GameWorldRegion {
    public static float width = GameWorld.RIGHT_EDGE * 3f;
    public static int powerupCount = 5;
    public static int dyepackCount = 11;
    public static int debrisCount = 10;
    Actor hero;
    List<Primitive> primitives;
    List<Obstacle> debris;
    List<PowerUp> powerups;
    List<Actor> characters;
    List<DyePack> dyepacks;

    public Space(Hero hero, ArrayList<Enemy> enemies, float leftEdge) {
        characters = new ArrayList<Actor>();
        this.hero = hero;
        characters.add(hero);
        characters.addAll(enemies);
        debris = new ArrayList<Obstacle>();
        powerups = new ArrayList<PowerUp>();
        dyepacks = new ArrayList<DyePack>();
        primitives = new ArrayList<Primitive>();
        float height = GameWorld.TOP_EDGE;
        center.set((width * 0.5f) + leftEdge, height / 2);
        size.set(width, height);
        velocity = new Vector2(-GameWorld.Speed, 0f);
        shouldTravel = true;
        visible = false;
        float rightEdge = center.getX() + size.getX() / 2;
        float region = (rightEdge - leftEdge) / powerupCount;
        for (int i = 0; i < powerupCount; i++) {
            float regionLeft = leftEdge + (i * region);
            float regionRight = regionLeft + region;
            powerups.add(PowerUp.randomPowerUp(hero, regionLeft, regionRight));
        }
        // offset the region to pad the space before the next element
        // this makes the region slightly smaller than it actually should be
        // otherwise
        int offset = 1;
        region = (rightEdge - leftEdge) / (debrisCount + offset);
        for (int i = 0; i < debrisCount; i++) {
            float regionLeft = leftEdge + (i * region);
            float regionRight = regionLeft + region;
            debris.add(new Obstacle(characters, regionLeft, regionRight));
        }
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

    @Override
    public void destroy() {
        super.destroy();
        for (PowerUp p : powerups) {
            p.destroy();
        }
        for (DyePack p : dyepacks) {
            p.destroy();
        }
        for (Obstacle d : debris) {
            d.destroy();
        }
        for (Primitive p : primitives) {
            p.destroy();
        }
    }

    @Override
    public void update() {
        super.update();
        for (Primitive p : primitives) {
            p.update();
        }
        for (int i = primitives.size() - 1; i >= 0; --i) {
            if (primitives.get(i).visible == false) {
                primitives.remove(i);
            }
        }
        for (DyePack p : dyepacks) {
            p.update();
        }
        for (PowerUp p : powerups) {
            p.update();
        }
        for (Obstacle d : debris) {
            d.update();
        }
    }

    @Override
    public boolean isOffScreen() {
        return center.getX() + size.getX() / 2 <= 0;
    }

    @Override
    public float rightEdge() {
        return center.getX() + size.getX() / 2;
    }
}
