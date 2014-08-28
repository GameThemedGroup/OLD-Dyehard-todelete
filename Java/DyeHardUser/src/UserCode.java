import java.awt.event.KeyEvent;

import dyehard.CollisionManager;
import dyehard.DyeHard;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.JohnsBallinAssPowerUp;
import dyehard.Player.Hero;
import dyehard.Util.Colors;
import dyehard.Weapons.LimitedAmmoWeapon;
import dyehard.Weapons.OverHeatWeapon;
import dyehard.Weapons.SpreadFireWeapon;
import dyehard.World.Space;

public class UserCode extends DyeHard {

    private Hero hero;
    private DeveloperControls devControls;

    @Override
    protected void initialize() {
        hero = new Hero();
        hero.registerWeapon(new SpreadFireWeapon(hero));
        hero.registerWeapon(new OverHeatWeapon(hero));
        hero.registerWeapon(new LimitedAmmoWeapon(hero));

        Space.addDefaultPowerUps(10);

        JohnsBallinAssPowerUp john = new JohnsBallinAssPowerUp();
        john.center.set(50f, 30f);
        Space.registerPowerUp(john);

        Space.addDefaultDyePacks(10);

        DyePack p = new DyePack(Colors.Yellow);
        p.center.set(60f, 30f);
        Space.registerDyePack(p);

        Space.addDefaultDebris(15);

        world.initialize(hero);

        devControls = new DeveloperControls(hero, keyboard);
    }

    @Override
    protected void update() {
        switch (state) {
        case PAUSED:
            CollisionManager.update();
            hero.update();
            devControls.update();
            break;
        default:
            break;
        }

        if (keyboard.isButtonDown(KeyEvent.VK_U)) {
            JohnsBallinAssPowerUp john = new JohnsBallinAssPowerUp();
            john.center.set(hero.center.getX() + 10f, hero.center.getY());
            Space.registerPowerUp(john);
        }

        if (keyboard.isButtonDown(KeyEvent.VK_I)) {
            DyePack p = new DyePack(Colors.Yellow);
            p.center.set(hero.center.getX() + 10f, hero.center.getY());
            Space.registerDyePack(p);
        }
    }
}
