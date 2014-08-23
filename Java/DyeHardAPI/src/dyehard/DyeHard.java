package dyehard;

import java.awt.event.KeyEvent;

import Engine.LibraryCode;
import dyehard.Util.Timer;
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

        state = State.PLAYING;
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
