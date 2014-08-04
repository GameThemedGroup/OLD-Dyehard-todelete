import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import dyehard.DyeHard;
import dyehard.Collectibles.DyePack;
import dyehard.Player.Hero;
import dyehard.Util.Colors;
import dyehard.Weapons.LimitedAmmoWeapon;
import dyehard.Weapons.OverHeatWeapon;
import dyehard.Weapons.SpreadFireWeapon;
import dyehard.World.GameWorldRegion;
import dyehard.World.Space;

public class UserCode extends DyeHard {

    private Hero hero;

    @Override
    protected void initialize() {
        hero = new Hero(keyboard);
        hero.registerWeapon(new SpreadFireWeapon(hero));
        hero.registerWeapon(new OverHeatWeapon(hero));
        hero.registerWeapon(new LimitedAmmoWeapon(hero));

        world.initialize(hero);

        GameWorldRegion startingSpace = new Space(hero);
        ((Space) startingSpace).registerDyes(randomDyePacks(11));
        world.addRegion(startingSpace);
    }

    protected void update() {

    }

    private List<DyePack> randomDyePacks(int count) {
        List<DyePack> randomDyes = new ArrayList<DyePack>();

        for (int i = 0; i < count; ++i) {
            Color randomColor = Colors.randomColor();
            DyePack dye = new DyePack(hero, randomColor);
            randomDyes.add(dye);
        }

        return randomDyes;
    }
}
