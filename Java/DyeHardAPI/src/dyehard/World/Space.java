package dyehard.World;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Engine.Vector2;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.Ghost;
import dyehard.Collectibles.Invincibility;
import dyehard.Collectibles.Magnetism;
import dyehard.Collectibles.Overload;
import dyehard.Collectibles.PowerUp;
import dyehard.Collectibles.SlowDown;
import dyehard.Collectibles.SpeedUp;
import dyehard.Collectibles.Unarmed;
import dyehard.Obstacles.Debris;
import dyehard.Player.Hero;
import dyehard.Util.Colors;

public class Space extends GameWorldRegion {
    public static float WIDTH = GameWorld.RIGHT_EDGE * 3f;

    private static int powerUpCount = 0;
    public static int dyepackCount = 0;
    public static int debrisCount = 0;

    private static Random RANDOM = new Random();

    // The list of powerups that can be randomly generated
    private static List<PowerUp> powerUpTypes;
    private static List<PowerUp> userPowerUps;
    private List<PowerUp> powerUpList;

    // The list of dyes that can be randomly generated
    List<DyePack> dyeList;

    public Space(Hero hero) {
        width = WIDTH;
        speed = -GameWorld.Speed;
        powerUpTypes = new ArrayList<PowerUp>();
        userPowerUps = new ArrayList<PowerUp>();
    }

    @Override
    public void initialize(float leftEdge) {
        position = leftEdge + width * 0.5f;
        generateCollectibles(leftEdge);
    }

    public void registerPowerUpTypes(List<PowerUp> powerups, int numPowerUps) {
        // this.numPowerUps = numPowerUps;
        powerUpTypes = powerups;
    }

    public void registerDyes(List<DyePack> dyes) {
        dyeList = dyes;
    }

    private void generateCollectibles(float leftEdge) {
        if (dyeList == null) {
            generateDefaultDyePacks(dyepackCount);
        }

        initializeDyePacks(dyeList);

        generatePowerUps(powerUpTypes, powerUpCount);
        initializePowerUps(powerUpList);

        // offset the region to pad the space before the next element
        // this makes the region slightly smaller than it actually should be
        // otherwise
        int offset = 1;
        float region = (rightEdge() - leftEdge()) / (debrisCount + offset);
        for (int i = 0; i < debrisCount; i++) {
            float regionLeft = leftEdge + (i * region);
            float regionRight = regionLeft + region;
            new Debris(regionLeft, regionRight);
        }
    }

    private void generatePowerUps(List<PowerUp> powerUpTypes, int count) {
        powerUpList = new ArrayList<PowerUp>();
        if (powerUpTypes == null || powerUpTypes.size() == 0) {
            return;
        }

        for (int i = 0; i < count; i++) {
            PowerUp randomPowerUp = powerUpTypes.get(RANDOM
                    .nextInt(powerUpTypes.size()));

            PowerUp generatedPowerUp = randomPowerUp.clone();
            powerUpList.add(generatedPowerUp);
        }
    }

    private void initializePowerUps(List<PowerUp> powerups) {
        assert powerups != null;

        // Powerups are distributed within uniformly distributed regions
        float regionWidth = width / powerups.size();
        float regionStart = leftEdge();
        float regionHeight = GameWorld.TOP_EDGE - GameWorld.BOTTOM_EDGE;

        Vector2 velocity = new Vector2(-GameWorld.Speed, 0f);
        float posX, posY;

        for (int i = 0; i < powerups.size(); i++) {
            posX = regionStart + (i * regionWidth);
            posX += RANDOM.nextFloat() * regionWidth;

            posY = (regionHeight - PowerUp.height) * RANDOM.nextFloat()
                    + PowerUp.height / 2f;

            Vector2 position = new Vector2(posX, posY);

            powerups.get(i).initialize(position, velocity);
        }

        for (int i = 0; i < userPowerUps.size(); i++) {
            userPowerUps.get(i)
                    .initialize(userPowerUps.get(i).center, velocity);
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
            DyePack dye = new DyePack(randomColor);
            dyeList.add(dye);
        }
    }

    public static void registerDefaultPowerUps(int count) {
        powerUpCount = count;

        powerUpTypes.add(new Ghost());
        powerUpTypes.add(new Invincibility());
        powerUpTypes.add(new Magnetism());
        powerUpTypes.add(new Overload());
        powerUpTypes.add(new SlowDown());
        powerUpTypes.add(new SpeedUp());
        powerUpTypes.add(new Unarmed());
    }

    public static void registerPowerUp(PowerUp p) {
        PowerUp userPowerUp = p;
        userPowerUp.center.set(GameWorld.LEFT_EDGE + p.center.getX(),
                p.center.getY());
        userPowerUps.add(p);
    }
}
