package dyehard.Collectibles;

import Engine.BaseCode;
import Engine.Primitive;
import dyehard.Collidable;
import dyehard.Player.Hero;
import dyehard.Player.HeroInterfaces.HeroCollision;
import dyehard.Player.HeroInterfaces.HeroDamage;
import dyehard.World.Gate.DeathGate;

public class Ghost extends PowerUp implements HeroCollision, HeroDamage {
    // public static PowerUpMeter meter = new PowerUpMeter(1, Game.Blue);
    protected float duration = 3000f;

    public Ghost() {
        super();
        timer.setInterval(duration);
        texture = BaseCode.resources.loadImage("Textures/PowerUp_Blue.png");
    }

    @Override
    public PowerUp clone() {
        return new Ghost();
    }

    @Override
    public void apply(Hero hero) {
        hero.collisionHandler = this;
        hero.damageHandler = this;
    }

    @Override
    public void unapply(Hero hero) {
        hero.collisionHandler = null;
        hero.damageHandler = null;
    }

    @Override
    public void collideWithHero(Hero hero, Collidable other) {
        // Hero becomes unable to collide with other objects
        return;
    }

    @Override
    public void damageHero(Hero hero, Primitive who) {
        if (!(who instanceof DeathGate)) {
            hero.isAlive = false;
        }
    }

    @Override
    public String toString() {
        return "Ghost";
    }
}
