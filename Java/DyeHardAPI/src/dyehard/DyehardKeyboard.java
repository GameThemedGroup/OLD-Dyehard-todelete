package dyehard;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import Engine.KeyboardInput;

public class DyehardKeyboard extends KeyboardInput {
    Set<KeyEvent> keyPresses = new HashSet<KeyEvent>();
    Set<KeyEvent> keyReleases = new HashSet<KeyEvent>();

    protected String lastKeyPress;

    @Override
    public String getLastKey() {
        return lastKeyPress;
    }

    @Override
    public void keyPressed(KeyEvent key) {
        keyPresses.add(key);
        lastKeyPress = KeyEvent.getKeyText(key.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent key) {
        keyReleases.add(key);
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // do nothing
    }

    @Override
    public void update() {
        super.update();

        for (KeyEvent key : keyPresses) {
            pressButton(key.getKeyCode());
        }
        keyPresses.clear();

        for (KeyEvent key : keyReleases) {
            releaseButton(key.getKeyCode());
        }
        keyReleases.clear();
    }

}
