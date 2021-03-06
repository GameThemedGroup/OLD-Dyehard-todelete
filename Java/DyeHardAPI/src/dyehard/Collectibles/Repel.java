package dyehard.Collectibles;

import java.util.Set;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Collidable;
import dyehard.CollisionManager;
import dyehard.Configuration;
import dyehard.Player.Hero;
import dyehard.Player.Hero.CurPowerUp;

public class Repel extends PowerUp {

    protected float magnitude = Configuration
            .getPowerUpData(Configuration.PowerUpType.REPEL).magnitude;
    protected final float attractionDistance = magnitude;

    public Repel() {
        super();
        duration = Configuration
                .getPowerUpData(Configuration.PowerUpType.REPEL).duration * 1000;

        texture = BaseCode.resources.loadImage("Textures/PowerUp_Repel.png");
        applicationOrder = 40;
    }

    @Override
    public void apply(Hero hero) {
        Set<Collidable> collidables = CollisionManager.getCollidables();
        hero.curPowerUp = CurPowerUp.REPEL;
        hero.isRepel = true;

        for (Collidable c : collidables) {
            if (c instanceof DyePack || c instanceof PowerUp) {
                // Finds the distance between the Hero and the dye/powerup
                Vector2 toHero = new Vector2(hero.center).sub(c.center);
                float distanceSqrd = toHero.lengthSQRD();

                if (distanceSqrd <= attractionDistance * attractionDistance) {
                    toHero.normalize();
                    c.velocity = toHero.mult(-0.85f);
                }
            }
        }
    }

    @Override
    public void unapply(Hero hero) {
        hero.isRepel = false;
        hero.curPowerUp = CurPowerUp.NONE;
        return;
    }

    @Override
    public PowerUp clone() {
        return new Repel();
    }

    @Override
    public String toString() {
        return "Repel: " + super.toString();
    }

}
