package dyehard.Enemies;

import java.util.ArrayList;
import java.util.Iterator;

import Engine.Vector2;
import dyehard.Player.Hero;
import dyehard.Util.Timer;

public class BrainEnemy extends Enemy {
    private static float widthToHeightRatio = 0.59f;
    private Timer timer;
    private ArrayList<BlackHole> blackHoles;

    public BrainEnemy(Vector2 center, float height, Hero currentHero) {
        super(center, height * widthToHeightRatio, height, currentHero,
                "Textures/Brain Enemy.png");
        timer = new Timer(2000f);
        blackHoles = new ArrayList<BlackHole>();
    }

    @Override
    public void update() {
        super.update();
        if (timer.isDone()) {
            blackHoles.add(new BlackHole(center.clone(), 7.5f, hero));
            timer.reset();
        }
        for (Iterator<BlackHole> bhIter = blackHoles.iterator(); bhIter
                .hasNext();) {
            BlackHole b = bhIter.next();
            if (!b.isAlive()) {
                bhIter.remove();
                System.out.println("Removed a black hole");
            }
        }
    }

    @Override
    public void destroy() {
        for (BlackHole b : blackHoles) {
            b.destroy();
        }
        super.destroy();
    }

}