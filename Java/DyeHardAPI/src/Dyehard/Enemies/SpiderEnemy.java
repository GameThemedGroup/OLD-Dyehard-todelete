package Dyehard.Enemies;

import BaseTypes.Enemy;
import Dyehard.Player.Hero;
import Engine.BaseCode;
import Engine.Vector2;

public class SpiderEnemy extends Enemy {
    private static float widthToHeightRatio = 0.985f;

    public SpiderEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height * widthToHeightRatio, height, currentHero);
        texture = BaseCode.resources.loadImage("Textures/Spider Enemy.png");
    }
}