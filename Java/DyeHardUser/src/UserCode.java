import dyehard.DyeHard;
import dyehard.Player.Hero;
import dyehard.World.GameWorldRegion;
import dyehard.World.Space;

public class UserCode extends DyeHard {

    @Override
    protected void initialize() {
        Hero hero = new Hero(keyboard);
        world.initialize(hero);

        GameWorldRegion startingSpace = new Space(hero);
        world.addRegion(startingSpace);
    }

    protected void update() {

    }

}
