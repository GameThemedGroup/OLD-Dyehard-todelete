package dyehard.Collectibles;

import java.awt.Color;

import dyehard.Player.Hero;

public class Unarmed extends PowerUp {
    public Unarmed(Hero hero, float minX, float maxX) {
        super(hero, minX, maxX);
        color = Color.cyan;
        // texture = BaseCode.resources.loadImage("Textures/PowerUp_Blue.png");
    }

    @Override
    public void activate() {
        hero.unarmedOn();
        PowerUpManager.UnarmedTimer.reset();
        super.activate();
        System.out.println("Picked up Unarmed");
    }
}
