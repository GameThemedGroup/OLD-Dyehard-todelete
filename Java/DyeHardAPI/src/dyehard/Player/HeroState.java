package dyehard.Player;

import dyehard.Collidable;

public class HeroState {
    public interface HeroCollision {
        public abstract void collideWithHero(Collidable other);
    }

    public interface HeroDamage {
        public void damageHero();
    }

    public HeroCollision collisionHandler;
    public HeroDamage damageHandler;

    public HeroState() {
    }

    public HeroState(HeroState other) {
        collisionHandler = other.collisionHandler;
        damageHandler = other.damageHandler;
    }
}
