package dyehard;

import java.awt.event.KeyEvent;

import Engine.BaseCode;
import Engine.LibraryCode;
import dyehard.World.GameState;
import dyehard.World.GameWorld;

public abstract class DyeHard extends LibraryCode {
    public final String bgMusicPath = "Audio/BgMusic.wav";

    public enum State {
        BEGIN, PAUSED, PLAYING, GAMEOVER
    }

    // Game state
    protected static State state;
    protected GameWorld world;

    private void checkControl() {
        keyboard.update();
        if (keyboard.isButtonTapped(KeyEvent.VK_ESCAPE)) {
            window.close();
        }

        if (keyboard.isButtonDown(KeyEvent.VK_ALT)
                && keyboard.isButtonTapped(KeyEvent.VK_ENTER)) {

            keyboard.releaseButton(KeyEvent.VK_ENTER);
            keyboard.releaseButton(KeyEvent.VK_ALT);
            window.toggleFullscreen();
            window.requestFocusInWindow();
        }

        switch (state) {
        case BEGIN:
            if (keyboard.isButtonTapped(KeyEvent.VK_A)) {
                state = State.PLAYING;
            }
            break;
        case PAUSED:
            if (keyboard.isButtonTapped(KeyEvent.VK_A)) {
                state = State.PLAYING;
                BaseCode.resources.resumeSound();
            }
            break;
        case PLAYING:
            if (keyboard.isButtonTapped(KeyEvent.VK_A)) {
                state = State.PAUSED;
                BaseCode.resources.pauseSound();
            } else if (world.gameOver()) {
                state = State.GAMEOVER;
                BaseCode.resources.pauseSound();
            }
            break;
        case GAMEOVER:
            if (keyboard.isButtonTapped(KeyEvent.VK_SPACE)) {
                state = State.PLAYING;
                GameState.RemainingLives = 4;
                BaseCode.resources.resumeSound();
            }
            break;
        }
    }

    protected abstract void initialize();

    @Override
    public void initializeWorld() {
        super.initializeWorld();
        window.requestFocusInWindow();

        // Replace the default keyboard input with DyehardKeyboard
        window.removeKeyListener(keyboard);
        keyboard = new DyehardKeyboard();
        window.addKeyListener(keyboard);

        window.addMouseListener(mouse);

        resources.setClassInJar(this);

        state = State.PLAYING;
        GameState.TargetDistance = Configuration.worldMapLength;
        world = new GameWorld();

        // preload sound/music, and play bg music
        BaseCode.resources.preloadSound(bgMusicPath);
        BaseCode.resources.playSoundLooping(bgMusicPath);

        initialize(); // call user code Initialize()
    }

    @Override
    public void updateWorld() {
        checkControl();
        switch (state) {
        case PLAYING:
            UpdateManager.update();
            CollisionManager.update();
            break;
        default:
            break;
        }

        update(); // call user code update()
    }

    public static State getState() {
        return state;
    }

    protected abstract void update();

}
