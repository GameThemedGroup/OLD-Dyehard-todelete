package Dyehard.Obstacles;

import java.awt.Color;
import java.util.List;

import Dyehard.Enemies.Enemy;
import Dyehard.Player.Hero;
import Dyehard.World.GameWorld;
import Engine.Rectangle;
import Engine.Vector2;

public class Obstacle extends Rectangle {
    private Hero hero;

    public Obstacle(Hero hero, List<Enemy> enemies, Vector2 center,
            float width, float height) {
        // super(center, width, height);
        this.center = center;
        size = new Vector2(width, height);
        shouldTravel = true;
        velocity = new Vector2(-GameWorld.Speed, 0);
        color = Color.GREEN;
        this.hero = hero;
    }

    @Override
    public void update() {
        super.update();
        checkCollisions();
    }

    private void checkCollisions() {
        if (!isInsideWorldBound()) {
            destroy();
            return;
        }
        if (collided(hero.getPosition())) {
            pushOutCircle(hero.getPosition());
        }
    }
}
