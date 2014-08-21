package dyehard.Enemies;

import Engine.Vector2;
import dyehard.Player.Hero;
import dyehard.Util.Timer;

public class BrainEnemy extends Enemy {
    private Timer timer;

    public BrainEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height, height, currentHero, "Textures/minion_Portal.png");
        timer = new Timer(2000f);
    }

    @Override
    public void update() {
        super.update();
        if (timer.isDone()) {
            BlackHole blackHole = new BlackHole(center.clone(), 4f, hero);
            timer.reset();
        }
    }
}