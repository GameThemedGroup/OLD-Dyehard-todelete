

import java.util.Random;

import dyehard.World.PlatformSingle;
import Engine.BaseCode;
import Engine.Vector2;

public class Platform {
    public static float height = 1.25f;
    private final float width = 6.5f;
    private static Random RANDOM = new Random();

    public Platform(int offset, float leftEdge, boolean continuous) {
        fillPlatform(offset, leftEdge, continuous);
    }

    private void fillPlatform(int offset, float leftEdge, boolean continuous) {
        // set up platform
        float Ypos = ((offset * 1f) / Stargate.GATE_COUNT)
                * BaseCode.world.getHeight();
        int numPlat = (int) (Stargate.WIDTH / width);
        if (continuous) {
            for (int i = 0; i < numPlat; i++) {
                float Xpos = (width * i) + leftEdge + (width / 2);
                new PlatformSingle(new Vector2(Xpos, Ypos));
            }
        } else {
            // randomly fill platform
            int consecutiveChance = 10;
            boolean platform = true;
            for (int i = 0; i < numPlat; i++) {
                if (consecutiveChance <= 0
                        || RANDOM.nextInt(consecutiveChance) == 0) {
                    platform = !platform;
                    consecutiveChance = 10;
                }
                if (platform) {
                    float Xpos = (width * i) + leftEdge + (width / 2);
                    new PlatformSingle(new Vector2(Xpos, Ypos));
                }
                consecutiveChance -= 1;
            }
        }
    }
}
