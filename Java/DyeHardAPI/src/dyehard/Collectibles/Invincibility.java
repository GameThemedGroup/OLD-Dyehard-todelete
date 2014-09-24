package dyehard.Collectibles;

import Engine.BaseCode;
import Engine.Primitive;
import dyehard.Configuration;
import dyehard.Player.Hero;
import dyehard.Player.HeroInterfaces.HeroDamage;

public class Invincibility extends PowerUp {

    // public static PowerUpMeter meter = new PowerUpMeter(2, Game.Pink);

    public Invincibility() {
        super();
        duration = Configuration
                .getPowerUpData(Configuration.PowerUpType.INVINCIBILITY).duration * 1000;

        texture = BaseCode.resources
                .loadImage("Textures/PowerUp_Invincibility.png");
        applicationOrder = 100;
        // label.setText("Invin");
    }

    @Override
    public void apply(Hero hero) {
        hero.damageHandler = new HeroDamage() {
            @Override
            public void damageHero(Hero hero, Primitive who) {
                // The hero becomes immune to damage
                return;
            }
        };
        hero.texture = BaseCode.resources
                .loadImage("Textures/Hero/Dye_Invincible.png");
        hero.size.set(Configuration.heroWidth * 2,
                Configuration.heroHeight * 1.3333f);
        hero.isInvin = true;
    }

    @Override
    public void unapply(Hero hero) {
        hero.texture = BaseCode.resources.loadImage("Textures/Hero/Dye.png");
        hero.size.set(Configuration.heroWidth, Configuration.heroHeight);
        hero.isInvin = false;
        hero.damageHandler = null;
    }

    @Override
    public PowerUp clone() {
        return new Invincibility();
    }

    @Override
    public String toString() {
        return "Invincibility: " + super.toString();
    }
}
