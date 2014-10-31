package dyehard;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import Engine.LibraryCode;

public abstract class DyeHard extends LibraryCode {
    public final static String bgMusicPath = "Audio/BgMusic.wav";

    public enum State {
        BEGIN, PAUSED, PLAYING, GAMEOVER, QUIT
    }

    // Game state
    protected static State state;

    protected abstract void initialize();

    @Override
    public void initializeWorld() {
        super.initializeWorld();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("resources/Beak.png");
        Cursor cursor = toolkit.createCustomCursor(image, new Point(0, 0),
                "cursor");
        window.setCursor(cursor);
        initialize(); // call user code Initialize()
    }

    @Override
    public void updateWorld() {
        update(); // call user code update()
    }

    public static State getState() {
        return state;
    }

    protected abstract void update();

}
