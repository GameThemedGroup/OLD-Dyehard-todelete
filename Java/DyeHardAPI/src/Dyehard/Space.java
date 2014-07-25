package Dyehard;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import BaseTypes.DyePack;
import BaseTypes.Enemy;
import BaseTypes.PowerUp;
import Dyehard.Obstacles.Debris;
import Dyehard.Obstacles.ObstacleManager;
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

    private static Random RANDOM = new Random();
    Hero hero;
    List<Primitive> primitives;

    public Space(Hero hero, ArrayList<Enemy> enemies, float leftEdge) {
        this.hero = hero;

        float height = GameWorld.TOP_EDGE;
        visible = false;
        center.set((width * 0.5f) + leftEdge, height / 2);
        size.set(width, height);
        velocity = new Vector2(-GameWorld.Speed, 0f);
        shouldTravel = true;

        generateCollectibles(leftEdge);
    }

    private void generateCollectibles(float leftEdge) {
        primitives = new ArrayList<Primitive>();

        float rightEdge = center.getX() + size.getX() / 2;
        float region = (rightEdge - leftEdge) / powerupCount;

        List<Color> colorSet = DyeHard.randomColorSet(DyeHard.colorCount);
        for (int i = 0; i < dyepackCount; i++) {
            float regionLeft = leftEdge + (i * region);
            float regionRight = regionLeft + region;
            Color randomColor = colorSet.get(RANDOM.nextInt(colorSet.size()));
            DyePack dye = new DyePack(hero, regionLeft, regionRight,
                    randomColor);
            primitives.add(dye);
        }

        for (int i = 0; i < powerupCount; i++) {
            float regionLeft = leftEdge + (i * region);
            float regionRight = regionLeft + region;
            PowerUp powerup = PowerUp.randomPowerUp(hero, regionLeft,
                    regionRight);
            primitives.add(powerup);
        }
        // offset the region to pad the space before the next element
        // this makes the region slightly smaller than it actually should be
        // otherwise
        int offset = 1;
        region = (rightEdge - leftEdge) / (debrisCount + offset);
        for (int i = 0; i < debrisCount; i++) {
            float regionLeft = leftEdge + (i * region);
            float regionRight = regionLeft + region;
            Debris debris = new Debris(regionLeft, regionRight);
            ObstacleManager.registerObstacle(debris);
            // primitives.add(debris);
        }
    }

    public void AddPrimitive(Primitive primitive) {
        primitives.add(primitive);
    }

    public void AddEnemy(Enemy enemy) {
        primitives.add(enemy);
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
