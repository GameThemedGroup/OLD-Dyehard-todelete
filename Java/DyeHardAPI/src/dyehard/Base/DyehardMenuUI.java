package dyehard.Base;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.DyeHard;
import dyehard.GameWorld;
import dyehard.DyeHard.State;
import dyehard.Util.DyeHardSound;

public class DyehardMenuUI {
    private final MenuSelect menuSelect;
    private final MenuSelect soundTog;
    private final MenuSelect musicTog;
    private final DyehardRectangle menuHud;

    private boolean music = true;

    // positions for selection texture
    private final Vector2 soundOn = new Vector2(46.328f, 36.111f);
    private final Vector2 soundOff = new Vector2(56.614f, 36.111f);
    private final Vector2 musicOn = new Vector2(46.328f, 27.345f);
    private final Vector2 musicOff = new Vector2(56.614f, 27.345f);
    private final Vector2 restartSelect = new Vector2(38.383f, 42.506f);
    private final Vector2 soundSelect = new Vector2(38.383f, 36.111f);
    private final Vector2 musicSelect = new Vector2(38.383f, 27.345f);
    private final Vector2 creditSelect = new Vector2(38.383f, 18.95f);
    private final Vector2 quitSelect = new Vector2(38.383f, 12.722f);

    public DyehardMenuUI() {
        menuSelect = new MenuSelect(restartSelect);
        soundTog = new MenuSelect(soundOn);
        musicTog = new MenuSelect(musicOn);

        menuHud = new DyehardRectangle();
        menuHud.size = new Vector2(40f, 40f);
        menuHud.center = new Vector2(GameWorld.RIGHT_EDGE / 2,
                GameWorld.TOP_EDGE / 2);
        menuHud.texture = BaseCode.resources
                .loadImage("Textures/UI/UI_Menu.png");
        menuHud.alwaysOnTop = true;
        menuHud.visible = false;
    }

    public void active(boolean active) {
        menuHud.visible = active;
        soundTog.active(active);
        musicTog.active(active);
        menuSelect.active(active);
    }

    public void select(float x, float y, boolean click) {
        // x value line up of buttons in game world
        if ((x > 35.45f) && (x < 59.68f)) {
            // hit button 1, restart
            if ((y > 42.75f) && (y < 47.125f)) {
                menuSelect.move(restartSelect);
                if (click) {
                    DyeHard.state = DyeHard.State.PLAYING;
                }
            }
            // hit button 2, sound toggle
            else if ((y > 36.125f) && (y < 40.75f)) {
                menuSelect.move(soundSelect);
                if (click) {
                    DyeHardSound.setSound(!DyeHardSound.getSound());
                    if (DyeHardSound.getSound()) {
                        soundTog.move(soundOn);
                    } else {
                        soundTog.move(soundOff);
                    }
                }
            }
            // hit button 3, music toggle
            else if ((y > 27.625f) && (y < 32.125f)) {
                menuSelect.move(musicSelect);
                if (click) {
                    music = !music;
                    if (music) {
                        BaseCode.resources
                                .playSoundLooping(DyeHard.bgMusicPath);
                        musicTog.move(musicOn);
                    } else {
                        BaseCode.resources.stopSound(DyeHard.bgMusicPath);
                        musicTog.move(musicOff);
                    }
                }
            }
            // hit button 4, credits
            else if ((y > 19.25f) && (y < 23.75f)) {
                menuSelect.move(creditSelect);
                // TODO add credits
            }
            // hit button 5, quit
            else if ((y > 13.125f) && (y < 17.5f)) {
                menuSelect.move(quitSelect);
                if (click) {
                    DyeHard.state = DyeHard.State.QUIT;
                }
            } else {
                menuSelect.active(false);
            }
        }
    }

    private class MenuSelect {
        private final DyehardRectangle sel;

        private MenuSelect(Vector2 pos) {
            sel = new DyehardRectangle();
            sel.size = new Vector2(2.8f, 2.8f);
            sel.center = new Vector2(pos);
            sel.texture = BaseCode.resources
                    .loadImage("Textures/UI/UI_Menu_Select.png");
            sel.alwaysOnTop = true;
            sel.visible = false;
        }

        private void active(boolean active) {
            sel.visible = active;
        }

        private void move(Vector2 pos) {
            if (pos != sel.center) {
                sel.center = pos;
                active(true);
            }
        }
    }
}
