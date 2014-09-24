import java.awt.event.KeyEvent;

import dyehard.DyeHard;
import dyehard.Collectibles.DyePack;
import dyehard.Player.Hero;
import dyehard.Util.Colors;
import dyehard.Weapons.LimitedAmmoWeapon;
import dyehard.Weapons.OverHeatWeapon;
import dyehard.Weapons.SpreadFireWeapon;
import dyehard.World.Space;
import dyehard.World.Stargate;

public class UserCode extends DyeHard {

    private Hero hero;

    @Override
    protected void initialize() {

        hero = new Hero();
        hero.registerWeapon(new SpreadFireWeapon(hero));
        hero.registerWeapon(new OverHeatWeapon(hero));
        hero.registerWeapon(new LimitedAmmoWeapon(hero));

        // Ghost g = new Ghost();
        // g.center.set(30f, 20f);
        // Space.registerPowerUp(g);

        DyePack p = new DyePack(Colors.Yellow);
        p.center.set(60f, 30f);
        Space.registerDyePack(p);

        world.initialize(hero);

        new DeveloperControls(hero);

        Stargate.addColor(Colors.Yellow);
    }

    @Override
    protected void update() {
        if (keyboard.isButtonDown(KeyEvent.VK_UP)) {
            hero.moveUp();
        }
        if (keyboard.isButtonDown(KeyEvent.VK_DOWN)) {
            hero.moveDown();
        }
        if (keyboard.isButtonDown(KeyEvent.VK_LEFT)) {
            hero.moveLeft();
        }
        if (keyboard.isButtonDown(KeyEvent.VK_RIGHT)) {
            hero.moveRight();
        }
        if (keyboard.isButtonDown(KeyEvent.VK_F)) {
            hero.currentWeapon.fire();
        }

        if (world.nextRegionIsSpace()) {
            System.out.println("SPACE COMING UP");
        }
    }
}
