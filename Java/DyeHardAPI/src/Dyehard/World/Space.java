package dyehard.World;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Engine.Primitive;
import Engine.Vector2;
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

    // The list of powerups that can be randomly generated
    List<PowerUp> powerUpPool;

    // The list of dyes that can be randomly generated
    List<DyePack> dyeList;

    public Space(Hero hero) {
        this.hero = hero;

        width = WIDTH;
        speed = -GameWorld.Speed;
        primitives = new ArrayList<Primitive>();
    }

    @Override
    public void initialize(float leftEdge) {
        position = leftEdge + width * 0.5f;
        generateCollectibles(leftEdge);
    }

    public void registerPowerUps(List<PowerUp> powerups) {
        powerUpPool = powerups;
        primitives.addAll(powerups);
    }

    public void registerDyes(List<DyePack> dyes) {
        dyeList = dyes;
        primitives.addAll(dyeList);
    }

    private void generateCollectibles(float leftEdge) {
        if (dyeList == null) {
            generateDefaultDyePacks(dyepackCount);
        }

        initializeDyePacks(dyeList);

        float rightEdge = position + width / 2;
        float region = (rightEdge - leftEdge) / dyepackCount;

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

    private void initializeDyePacks(List<DyePack> dyes) {
        assert dyes != null;

        // Dyepacks are distributed within uniformly distributed regions
        float regionWidth = width / dyeList.size();
        float regionStart = leftEdge();
        float regionHeight = GameWorld.TOP_EDGE - GameWorld.BOTTOM_EDGE;

        Vector2 velocity = new Vector2(-GameWorld.Speed, 0f);

        float posX, posY;

        for (int i = 0; i < dyes.size(); i++) {

            posX = regionStart + (i * regionWidth);
            posX += RANDOM.nextFloat() * regionWidth;

            posY = (regionHeight - DyePack.height) * RANDOM.nextFloat()
                    + DyePack.height / 2f;

            Vector2 position = new Vector2(posX, posY);

            dyes.get(i).initialize(position, velocity);
        }
    }

    private void generateDefaultDyePacks(int count) {
        dyeList = new ArrayList<DyePack>();
        for (int i = 0; i < count; ++i) {
            Color randomColor = Colors.randomColor();
            DyePack dye = new DyePack(hero, randomColor);
            dyeList.add(dye);
        }

        primitives.addAll(dyeList);
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
