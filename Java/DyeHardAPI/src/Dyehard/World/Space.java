package dyehard.World;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Engine.Vector2;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.PowerUp;
import dyehard.Obstacles.Debris;
import dyehard.Player.Hero;
import dyehard.Util.Colors;

public class Space extends GameWorldRegion {
    public static float WIDTH = GameWorld.RIGHT_EDGE * 3f;

    public static int powerupCount = 5;
    public static int dyepackCount = 11;
    public static int debrisCount = 0; // 10; TODO

    private static Random RANDOM = new Random();

    // The list of powerups that can be randomly generated
    List<PowerUp> powerUpTypes;
    List<PowerUp> powerUpList;
    private int numPowerUps;

    // The list of dyes that can be randomly generated
    List<DyePack> dyeList;

    public Space(Hero hero) {
        width = WIDTH;
        speed = -GameWorld.Speed;
    }

    @Override
    public void initialize(float leftEdge) {
        position = leftEdge + width * 0.5f;
        generateCollectibles(leftEdge);
    }

    public void registerPowerUpTypes(List<PowerUp> powerups, int numPowerUps) {
        this.numPowerUps = numPowerUps;
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

        generatePowerUps(powerUpTypes, numPowerUps);
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

    private void generatePowerUps(List<PowerUp> powerupTypes, int count) {
        powerUpList = new ArrayList<PowerUp>();
        if (powerupTypes == null || powerupTypes.size() == 0) {
            return;
        }

        for (int i = 0; i < count; i++) {
            PowerUp randomPowerUp = powerupTypes.get(RANDOM
                    .nextInt(powerupTypes.size()));

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

        // primitives.addAll(powerUpList);
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

        // primitives.addAll(dyeList);
    }

    private void generateDefaultDyePacks(int count) {
        dyeList = new ArrayList<DyePack>();
        for (int i = 0; i < count; ++i) {
            Color randomColor = Colors.randomColor();
            DyePack dye = new DyePack(randomColor);
            dyeList.add(dye);
        }
    }
}
