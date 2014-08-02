package dyehard;

import java.awt.event.KeyEvent;

import Engine.LibraryCode;
import dyehard.Background.Background;
import dyehard.World.GameWorld;

//This is "Game.cs"
public abstract class DyeHard extends LibraryCode {
    private enum State {
        BEGIN, PAUSED, PLAYING, GAMEOVER
    }

    // game objects
    private Background background;
    private GameWorld world;
    // Game state
    private State state;

    private void checkControl() {
        keyboard.update();
        if (keyboard.isButtonDown(KeyEvent.VK_ESCAPE)) {
            window.close();
        }
        switch (state) {
        case BEGIN:
            if (keyboard.isButtonDown(KeyEvent.VK_A)) {
                state = State.PLAYING;
                // startScreen.remove();
            }
            break;
        case PAUSED:
            if (keyboard.isButtonDown(KeyEvent.VK_A)) {
                state = State.PLAYING;
                // pauseScreen.remove();
            }
            if (keyboard.isButtonDown(KeyEvent.VK_Q)) {
                state = State.BEGIN;
                world = new GameWorld(keyboard);
                // pauseScreen.remove();
            }
            break;
        case PLAYING:
            if (keyboard.isButtonDown(KeyEvent.VK_A)) {
                state = State.PAUSED;
            } else if (world.gameOver()) {
                state = State.GAMEOVER;
            }
            break;
        case GAMEOVER:
            if (keyboard.isButtonDown(KeyEvent.VK_A)) {
                state = State.BEGIN;
                world = new GameWorld(keyboard);
                // gameOverScreen.remove();
            }
            break;
        }
    }

    protected abstract void initialize();

    @Override
    public void initializeWorld() {
        super.initializeWorld();
        // Starting state should be begin
        // Using playing to test controls
        state = State.PLAYING;
        background = new Background();
        // I pass keyboard into GameWorld when creating it because
        // I need access to BaseCode for keyboard inputs.

        if (world == null) {
            world = new GameWorld(keyboard);
        }

        System.out.println("Width = " + getInitWidth() + " Height = "
                + getInitHeight());
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
