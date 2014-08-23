package dyehard.Collectibles;

import Engine.BaseCode;
import Engine.Primitive;
import dyehard.Player.Hero;
import dyehard.Player.HeroInterfaces.HeroDamage;

public class Invincibility extends PowerUp {
    // public static PowerUpMeter meter = new PowerUpMeter(2, Game.Pink);
    public Invincibility() {
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Pink.png");
        applicationOrder = 100;
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
    }

    @Override
    public void unapply(Hero hero) {
        hero.damageHandler = null;
    }

    @Override
    public PowerUp clone() {
        return new Invincibility();
    }

    @Override
    public String toString() {
        return super.toString() + " Invincibility";
    }
}
