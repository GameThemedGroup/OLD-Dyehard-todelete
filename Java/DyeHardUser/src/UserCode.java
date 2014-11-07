import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import Engine.BaseCode;
import dyehard.CollisionManager;
import dyehard.Configuration;
import dyehard.DyeHard;
import dyehard.DyehardKeyboard;
import dyehard.UpdateManager;
import dyehard.Player.Hero;
import dyehard.Util.Colors;
import dyehard.Weapons.LimitedAmmoWeapon;
import dyehard.Weapons.OverHeatWeapon;
import dyehard.Weapons.SpreadFireWeapon;
import dyehard.World.GameState;

public class UserCode extends DyeHard {
    private boolean menuActive = false;

    private Hero hero;
    protected GameWorld world;

    private void checkControl() {
        keyboard.update();

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
            if (keyboard.isButtonTapped(KeyEvent.VK_ESCAPE)) {
                state = State.MENU;
            }
            break;
        case PAUSED:
            if (keyboard.isButtonTapped(KeyEvent.VK_A)) {
                state = State.PLAYING;
            }
            if (keyboard.isButtonTapped(KeyEvent.VK_ESCAPE)) {
                state = State.MENU;
            }
            break;
        case MENU:
            if (keyboard.isButtonTapped(KeyEvent.VK_A)) {
                state = State.PLAYING;
            }
            if (keyboard.isButtonTapped(KeyEvent.VK_ESCAPE)) {
                state = State.PLAYING;
            }
            break;
        case PLAYING:
            if (keyboard.isButtonTapped(KeyEvent.VK_A)) {
                state = State.PAUSED;
            } else if (keyboard.isButtonTapped(KeyEvent.VK_ESCAPE)) {
                state = State.MENU;
            } else if (world.gameOver()) {
                state = State.GAMEOVER;
            }
            break;
        case GAMEOVER:
            if (keyboard.isButtonTapped(KeyEvent.VK_SPACE)) {
                restart();
            }
            break;
        case QUIT:
            window.close();
            break;
        }
    }

    @Override
    protected void initialize() {
        window.requestFocusInWindow();

        // Replace the default keyboard input with DyehardKeyboard
        window.removeKeyListener(keyboard);
        keyboard = new DyehardKeyboard();
        window.addKeyListener(keyboard);

        window.addMouseListener(mouse);

        resources.setClassInJar(this);

        state = State.BEGIN;
        GameState.TargetDistance = Configuration.worldMapLength;
        world = new GameWorld();

        // preload sound/music, and play bg music
        BaseCode.resources.preloadSound(bgMusicPath);
        BaseCode.resources.playSoundLooping(bgMusicPath);

        hero = new Hero();

        // move mouse to where center of hero is
        try {
            Robot robot = new Robot();

            robot.mouseMove(
                    window.getLocationOnScreen().x
                            + (int) (hero.center.getX() * window.getWidth() / BaseCode.world
                                    .getWidth()),
                    window.getLocationOnScreen().y
                            + window.getHeight()
                            - (int) (hero.center.getX() * window.getWidth() / BaseCode.world
                                    .getWidth()));
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        hero.registerWeapon(new SpreadFireWeapon(hero));
        hero.registerWeapon(new OverHeatWeapon(hero));
        hero.registerWeapon(new LimitedAmmoWeapon(hero));

        world.initialize(hero);

        new DeveloperControls(hero);

        Stargate.addColor(Colors.Yellow);
    }

    protected void restart() {
        GameState.RemainingLives = 4;
        state = State.BEGIN;
        world.restartWorld(true);
    }

    @Override
    protected void update() {
        // update world, run managers
        checkControl();
        switch (state) {
        case PLAYING:
            UpdateManager.update();
            CollisionManager.update();
            break;
        default:
            break;
        }

        // playing, control hero
        if (state == State.PLAYING) {
            if (menuActive) {
                world.menu.active(false);
                menuActive = false;
            }
            hero.moveTo(mouse.getWorldX(), mouse.getWorldY());
            // System.out.println(MouseInfo.getPointerInfo().getLocation());

            if ((keyboard.isButtonDown(KeyEvent.VK_F))
                    || (mouse.isButtonDown(1))) {
                hero.currentWeapon.fire();
                // System.out.println(mouse.getWorldX() + " " +
                // mouse.getWorldY());
            }
        }

        // state menu or gameover, activate menu
        else if (state == State.MENU || state == State.GAMEOVER) {
            if (!menuActive) {
                world.menu.active(true);
                menuActive = true;
            } else {
                if (mouse.isButtonTapped(1)) {
                    world.menu.select(mouse.getWorldX(), mouse.getWorldY(),
                            true);
                } else {
                    world.menu.select(mouse.getWorldX(), mouse.getWorldY(),
                            false);
                }
            }
        }
    }
}
