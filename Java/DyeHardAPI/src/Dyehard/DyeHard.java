package dyehard;

import java.awt.event.KeyEvent;

import Engine.LibraryCode;
import dyehard.Background.Background;
import dyehard.Util.Timer;
import dyehard.World.GameWorld;

public abstract class DyeHard extends LibraryCode {
    // TODO replace this with method that returns time passed since last frame
    // The amount of time that has elapsed since the last frame
    public static float DELTA_TIME = 1f / 40f;

    private enum State {
        BEGIN, PAUSED, PLAYING, GAMEOVER
    }

    // game objects
    private Background background;
    private GameWorld world;
    // Game state
    private State state;

    Timer controlTimer = new Timer(500f);

    private void checkControl() {
        if (controlTimer.isDone()) {
            keyboard.update();
            if (keyboard.isButtonDown(KeyEvent.VK_ESCAPE)) {
                window.close();
            }

            if (keyboard.isButtonDown(KeyEvent.VK_ALT)
                    && keyboard.isButtonDown(KeyEvent.VK_ENTER)) {

                keyboard.releaseButton(KeyEvent.VK_ENTER);
                keyboard.releaseButton(KeyEvent.VK_ALT);
                window.toggleFullscreen();
                controlTimer.reset();
            }

            switch (state) {
            case BEGIN:
                if (keyboard.isButtonDown(KeyEvent.VK_A)) {
                    state = State.PLAYING;
                    controlTimer.reset();
                }
                break;
            case PAUSED:
                if (keyboard.isButtonDown(KeyEvent.VK_A)) {
                    state = State.PLAYING;
                    controlTimer.reset();
                }
                /*
                 * if (keyboard.isButtonDown(KeyEvent.VK_Q)) { state =
                 * State.BEGIN; world = new GameWorld(keyboard); //
                 * pauseScreen.remove(); }
                 */
                break;
            case PLAYING:
                if (keyboard.isButtonDown(KeyEvent.VK_A)) {
                    state = State.PAUSED;
                    controlTimer.reset();
                } else if (world.gameOver()) {
                    state = State.GAMEOVER;
                    controlTimer.reset();
                }
                break;
            case GAMEOVER:
                /*
                 * if (keyboard.isButtonDown(KeyEvent.VK_A)) { state =
                 * State.BEGIN; world = new GameWorld(keyboard); // } `
                 */
                break;
            }
        }
    }

    protected abstract void initialize();

    @Override
    public void initializeWorld() {
        super.initializeWorld();
        resources.setClassInJar(this);
        // Starting state should be begin
        // Using playing to test controls
        state = State.PLAYING;
        background = new Background();
        // I pass keyboard into GameWorld when creating it because
        // I need access to BaseCode for keyboard inputs.
        if (world == null) {
            world = new GameWorld(keyboard);
        }
    }

    @Override
    public void updateWorld() {
        checkControl();
        switch (state) {
        case PLAYING:
            background.update();
            world.update();
            break;
        default:
            break;
        }
    }
}
