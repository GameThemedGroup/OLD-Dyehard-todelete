package dyehard.World;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Engine.Primitive;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.PowerUp;
import dyehard.Enemies.Enemy;
import dyehard.Obstacles.Debris;
import dyehard.Obstacles.ObstacleManager;
import dyehard.Player.Hero;
import dyehard.Util.Colors;

public class Space extends GameWorldRegion {
    public static float WIDTH = GameWorld.RIGHT_EDGE * 3f;

    public static int powerupCount = 5;
    public static int dyepackCount = 11;
    public static int debrisCount = 10;

    private static Random RANDOM = new Random();
    Hero hero;
    List<Primitive> primitives;

    public Space(Hero hero) {
        this.hero = hero;

        width = WIDTH;
        speed = -GameWorld.Speed;
    }

    @Override
    public void initialize(float leftEdge) {
        position = leftEdge + width * 0.5f;
        generateCollectibles(leftEdge);
    }

    private void generateCollectibles(float leftEdge) {
        primitives = new ArrayList<Primitive>();

        float rightEdge = position + width / 2;
        float region = (rightEdge - leftEdge) / dyepackCount;

        List<Color> colorSet = Colors.randomColorSet(Colors.colorCount);
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
        for (Primitive p : primitives) {
            p.destroy();
        }
    }

    @Override
    public void update() {
        position += speed;
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
