package usercode;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import Engine.BaseCode;
import dyehard.DyeHard;
import dyehard.Player.Hero;
import dyehard.Util.Colors;
import dyehard.Weapons.LimitedAmmoWeapon;
import dyehard.Weapons.OverHeatWeapon;
import dyehard.Weapons.SpreadFireWeapon;
import dyehard.World.Stargate;

public class UserCode extends DyeHard {
    private boolean menuActive = false;

    private Hero hero;

    @Override
    protected void initialize() {
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

    @Override
    protected void update() {
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

        // not playing, activate menu
        else if (state == State.PAUSED || state == State.GAMEOVER) {
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
