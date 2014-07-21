package Dyehard;

import java.util.ArrayList;
import java.util.List;

import BaseTypes.Character;
import BaseTypes.DyePack;
import BaseTypes.Enemy;
import BaseTypes.PowerUp;
import Dyehard.Obstacles.Debris;
import Dyehard.Player.Hero;
import Dyehard.World.GameWorld;
import Dyehard.World.GameWorldRegion;
import Engine.Primitive;
import Engine.Vector2;

public class Space extends GameWorldRegion {
    // TODO: 100f is a placeholder for rightEdge
    public static float width = 100f * 3f;
    public static int powerupCount = 5;
    public static int dyepackCount = 11;
    public static int debrisCount = 10;
    Character hero;
    List<Primitive> primitives;
    List<Debris> debris;
    List<PowerUp> powerups;
    List<Character> characters;
    List<DyePack> dyepacks;

    public Space(Hero hero, ArrayList<Enemy> enemies, float leftEdge) {
        characters = new ArrayList<Character>();
        this.hero = hero;
        characters.add(hero);
        characters.addAll(enemies);
        debris = new ArrayList<Debris>();
        powerups = new ArrayList<PowerUp>();
        dyepacks = new ArrayList<DyePack>();
        primitives = new ArrayList<Primitive>();
        // TODO: 50f is a placeholder for topEdge
        float height = 50f;
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
            debris.add(new Debris(characters, regionLeft, regionRight));
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
        for (Debris d : debris) {
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
        for (Debris d : debris) {
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
