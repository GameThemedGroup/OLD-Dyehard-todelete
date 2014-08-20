package dyehard.Enemies;

import java.awt.Color;
import java.util.Random;

import Engine.Vector2;
import dyehard.Player.Hero;
import dyehard.Util.Timer;

public class BlackHole extends Enemy {
    private static float widthToHeightRatio = 0.4f;
    private Hero hero;
    private Timer timer;

    public BlackHole(Vector2 center, float height, Hero hero) {
        super(center, height * widthToHeightRatio, height * widthToHeightRatio,
                hero, "Textures/StartScreen_Background.png");
        color = Color.black;
        this.hero = hero;
        timer = new Timer(4000f);
    }

    @Override
    public void update() {
        if (collided(hero)) {
            Random rand = new Random();
            hero.center.set(rand.nextInt(90) + 5, rand.nextInt(60));
            hero.velocity = new Vector2(0f, 0f);
        }
        if (timer.isDone()) {
            kill();
            destroy();
        }
    }
}
