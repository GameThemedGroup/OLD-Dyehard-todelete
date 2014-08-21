package dyehard.Enemies;

import java.awt.Color;
import java.util.Random;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.GameObject;
import dyehard.Player.Hero;
import dyehard.Util.Timer;

public class BlackHole extends GameObject {
    private static float widthToHeightRatio = 0.4f;
    private Hero hero;
    private Timer timer;

    public BlackHole(Vector2 center, float height, Hero hero) {
        this.center = center.clone();
        size.set(height * widthToHeightRatio, height * widthToHeightRatio);
        this.hero = hero;
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Box1.png");
        color = Color.black;
        this.hero = hero;
        timer = new Timer(5000f);
    }

    @Override
    public void update() {
        if (collided(hero)) {
            Random rand = new Random();
            hero.center.set(rand.nextInt(90) + 5, rand.nextInt(60));
            hero.velocity = new Vector2(0f, 0f);
        }
        if (timer.isDone()) {
            destroy();
        }
    }
}
