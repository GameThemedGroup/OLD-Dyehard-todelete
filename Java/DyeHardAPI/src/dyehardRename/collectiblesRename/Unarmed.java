package dyehard.Collectibles;

import java.awt.Color;

import dyehard.Player.Hero;

public class Unarmed extends PowerUp {
    public Unarmed(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        color = Color.cyan;
        label.setText("Unarmed");
    }

    @Override
    public void activate() {
        hero.unarmedOn();
        PowerUpManager.UnarmedTimer.reset();
        super.activate();
    }

    @Override
    public String toString() {
        return "Unarmed";
    }
}
