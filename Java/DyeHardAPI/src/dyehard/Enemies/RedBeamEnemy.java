package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Player.Hero;

public class RedBeamEnemy extends Enemy {
    public RedBeamEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height, height, currentHero,
                "Textures/minion_collector.png");
    }
}