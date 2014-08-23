import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import dyehard.CollisionManager;
import dyehard.DyeHard;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.Ghost;
import dyehard.Collectibles.Invincibility;
import dyehard.Collectibles.Overload;
import dyehard.Collectibles.PowerUp;
import dyehard.Collectibles.SpeedUp;
import dyehard.Player.Hero;
import dyehard.Util.Colors;
import dyehard.Weapons.LimitedAmmoWeapon;
import dyehard.Weapons.OverHeatWeapon;
import dyehard.Weapons.SpreadFireWeapon;
import dyehard.World.GameWorldRegion;
import dyehard.World.Space;

public class UserCode extends DyeHard {

    private Hero hero;
    private DeveloperControls devControls;

    @Override
    protected void initialize() {
        hero = new Hero(keyboard);
        hero.registerWeapon(new SpreadFireWeapon(hero));
        hero.registerWeapon(new OverHeatWeapon(hero));
        hero.registerWeapon(new LimitedAmmoWeapon(hero));

        world.initialize(hero);

        GameWorldRegion startingSpace = new Space(hero);
        world.addRegion(startingSpace);

        devControls = new DeveloperControls(hero, keyboard);

        Space.registerDefaultPowerUps(2);
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
    }

    private List<PowerUp> allPowerUps() {
        List<PowerUp> powerUps = new ArrayList<PowerUp>();

        powerUps.add(new Ghost());
        powerUps.add(new Overload());
        powerUps.add(new Invincibility());
        powerUps.add(new SpeedUp());

        return powerUps;
    }

    private List<DyePack> randomDyePacks(int count) {
        List<DyePack> randomDyes = new ArrayList<DyePack>();

        for (int i = 0; i < count; ++i) {
            Color randomColor = Colors.randomColor();
            DyePack dye = new DyePack(randomColor);
            randomDyes.add(dye);
        }

        return randomDyes;
    }
}
