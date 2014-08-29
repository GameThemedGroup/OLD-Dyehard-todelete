package dyehard;

import java.awt.event.KeyEvent;

import Engine.LibraryCode;
import dyehard.World.GameState;
import dyehard.World.GameWorld;

public abstract class DyeHard extends LibraryCode {
    // TODO replace this with method that returns time passed since last frame
    // The amount of time that has elapsed since the last frame
    public static float DELTA_TIME = 1f / 40f;

    protected enum State {
        BEGIN, PAUSED, PLAYING, GAMEOVER
    }

    // Game state
    protected State state;
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
            }
            /*
             * if (keyboard.isButtonDown(KeyEvent.VK_Q)) { state = State.BEGIN;
             * world = new GameWorld(keyboard); // pauseScreen.remove(); }
             */
            break;
        case PLAYING:
            if (keyboard.isButtonTapped(KeyEvent.VK_A)) {
                state = State.PAUSED;
            } else if (world.gameOver()) {
                state = State.GAMEOVER;
            }
            break;
        case GAMEOVER:
            /*
             * if (keyboard.isButtonDown(KeyEvent.VK_A)) { state = State.BEGIN;
             * world = new GameWorld(keyboard); // } `
             */
            break;
        }
    }

    protected abstract void initialize();

    @Override
    public void initializeWorld() {
        super.initializeWorld();

        // Replace the default keyboard input with DyehardKeyboard
        window.removeKeyListener(keyboard);
        keyboard = new DyehardKeyboard();
        window.addKeyListener(keyboard);

        resources.setClassInJar(this);

        state = State.PLAYING;
        GameState.TargetDistance = 3000; // TODO magic number
        world = new GameWorld();

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

    protected abstract void update();
}
