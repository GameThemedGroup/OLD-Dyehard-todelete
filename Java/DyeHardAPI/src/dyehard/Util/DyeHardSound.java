package dyehard.Util;

import Engine.BaseCode;

public class DyeHardSound {
    private static boolean soundPlay = true;
    // Music/Sound path strings
    public final static String pickUpSound = "Audio/PickupSound.wav";
    public final static String powerUpSound = "Audio/Powerup.wav";
    public final static String paintSpraySound = "Audio/PaintSpraySound.wav";
    public final static String enemySpaceship1 = "Audio/EnemySpaceship1.wav";
    public final static String enemySpaceship2 = "Audio/EnemySpaceship1.wav";
    public final static String loseSound = "Audio/DyeLose.wav";
    public final static String winSound = "Audio/DyeWin.wav";
    public final static String lifeLostSound = "Audio/LifeLost.wav";
    public final static String portalEnter = "Audio/PortalEnter.wav";
    public final static String portalExit = "Audio/PortalExit.wav";
    public final static String portalLoop = "Audio/PortalLoop.wav";

    static {
        // load sounds for later interactions
        BaseCode.resources.preloadSound(pickUpSound);
        BaseCode.resources.preloadSound(powerUpSound);
        BaseCode.resources.preloadSound(paintSpraySound);
        BaseCode.resources.preloadSound(enemySpaceship1);
        BaseCode.resources.preloadSound(enemySpaceship2);
        BaseCode.resources.preloadSound(loseSound);
        BaseCode.resources.preloadSound(winSound);
        BaseCode.resources.preloadSound(lifeLostSound);
        BaseCode.resources.preloadSound(portalEnter);
        BaseCode.resources.preloadSound(portalExit);
        BaseCode.resources.preloadSound(portalLoop);
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
