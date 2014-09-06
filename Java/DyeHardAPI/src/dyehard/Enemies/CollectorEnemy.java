package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Player.Hero;

public class CollectorEnemy extends Enemy {

    public CollectorEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height, height, currentHero,
                "Textures/Enemies/minion_collector.png");
    }

    @Override
    public String toString() {
        return "Collector";
    }
}