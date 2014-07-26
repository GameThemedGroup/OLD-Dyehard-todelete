package Dyehard.Enemies;

import BaseTypes.Enemy;
import Dyehard.Player.Hero;
import Engine.Vector2;

public class RedBeamEnemy extends Enemy {
    private static float widthToHeightRatio = 0.7f;

    public RedBeamEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height * widthToHeightRatio, height, currentHero,
                "Textures/Red Beam Enemy.png");
    }
}