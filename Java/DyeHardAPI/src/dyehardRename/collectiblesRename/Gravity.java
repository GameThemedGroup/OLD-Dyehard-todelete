package dyehard.Collectibles;

import java.awt.Color;

import dyehard.Player.Hero;

public class Gravity extends PowerUp {
    public Gravity(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        color = Color.red;
        label.setText("Gravity");
    }

    @Override
    public void activate() {
        hero.gravityOn();
        PowerUpManager.GravityTimer.reset();
        super.activate();
    }

    @Override
    public String toString() {
        return "Gravity";
    }
}
