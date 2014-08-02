import dyehard.DyeHard;
import dyehard.Player.Hero;
import dyehard.Weapons.LimitedAmmoWeapon;
import dyehard.Weapons.OverHeatWeapon;
import dyehard.Weapons.SpreadFireWeapon;
import dyehard.World.GameWorldRegion;
import dyehard.World.Space;

public class UserCode extends DyeHard {

    @Override
    protected void initialize() {
        Hero hero = new Hero(keyboard);
        hero.registerWeapon(new SpreadFireWeapon(hero));
        hero.registerWeapon(new OverHeatWeapon(hero));
        hero.registerWeapon(new LimitedAmmoWeapon(hero));

        world.initialize(hero);

        GameWorldRegion startingSpace = new Space(hero);
        world.addRegion(startingSpace);
    }

    protected void update() {

    }

}
