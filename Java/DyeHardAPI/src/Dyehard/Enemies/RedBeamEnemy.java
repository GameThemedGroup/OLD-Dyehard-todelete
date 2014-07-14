package Dyehard.Enemies;

import Dyehard.Hero;
import Engine.Vector2;

public class RedBeamEnemy extends Enemy {
    private static float widthToHeightRatio = 0.7f;

    public RedBeamEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height * widthToHeightRatio, height, currentHero);
        setTexture("Red Beam Enemy");
    }
}