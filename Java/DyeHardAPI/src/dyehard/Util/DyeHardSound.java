package dyehard.Util;

import Engine.BaseCode;

public class DyeHardSound {
    private static boolean soundPlay = true;
    // Music/Sound path strings
    public final static String pickUpSound = "Audio/PickupSound.wav";
    public final static String powerUpSound = "Audio/Powerup.wav";
    public final static String paintSpraySound = "Audio/PaintSpraySound.wav";
    public final static String wormHoleSound = "Audio/WormHole.wav";

    static {
        // load sounds for later interactions
        BaseCode.resources.preloadSound(pickUpSound);
        BaseCode.resources.preloadSound(powerUpSound);
        BaseCode.resources.preloadSound(paintSpraySound);
        BaseCode.resources.preloadSound(wormHoleSound);
    }

    public static void play(String path) {
        if ((soundPlay) && (!BaseCode.resources.isSoundPlaying(path))) {
            BaseCode.resources.playSound(path);
        }
    }

    public static void setSound(boolean bool) {
        soundPlay = bool;
    }

    public static boolean getSound() {
        return soundPlay;
    }
}
