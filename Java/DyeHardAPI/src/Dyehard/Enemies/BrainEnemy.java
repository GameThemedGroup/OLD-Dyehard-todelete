package Dyehard.Enemies;

import BaseTypes.Enemy;
import Dyehard.Player.Hero;
import Engine.BaseCode;
import Engine.Vector2;

public class BrainEnemy extends Enemy {
    private static float widthToHeightRatio = 0.59f;

    public BrainEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height * widthToHeightRatio, height, currentHero);
        texture = BaseCode.resources.loadImage("Textures/Brain Enemy.png");
    }
}