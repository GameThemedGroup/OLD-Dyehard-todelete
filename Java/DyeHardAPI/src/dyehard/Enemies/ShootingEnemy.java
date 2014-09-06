package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Player.Hero;

public class ShootingEnemy extends Enemy {

    public ShootingEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height, height, currentHero,
                "Textures/Enemies/minion_shooter.png");
    }

    @Override
    public String toString() {
        return "Shooting";
    }
}