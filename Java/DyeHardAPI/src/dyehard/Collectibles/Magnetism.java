package dyehard.Collectibles;

import java.util.Set;

import Engine.Vector2;
import dyehard.Collidable;
import dyehard.CollisionManager;
import dyehard.DHR;
import dyehard.Player.Hero;

public class Magnetism extends PowerUp {
	
	protected float duration = DHR.getPowerupData(DHR.PowerupID.PU_MAGNETISM).duration;
	protected float magnitude = DHR.getPowerupData(DHR.PowerupID.PU_MAGNETISM).magnitude;

    public Magnetism() {
        applicationOrder = 40;
        label.setText("Magnet");
    }

    protected final float attractionDistance = magnitude;

    @Override
    public void apply(Hero hero) {
        Set<Collidable> collidables = CollisionManager.getCollidables();

        for (Collidable c : collidables) {
            if (c instanceof DyePack || c instanceof PowerUp) {
                // Finds the distance between the Hero and the dye/powerup
                Vector2 toHero = new Vector2(hero.center).sub(c.center);
                float distanceSqrd = toHero.lengthSQRD();

                if (distanceSqrd <= attractionDistance * attractionDistance) {
                    toHero.normalize();
                    c.velocity = toHero.mult(0.85f);
                }
            }
        }
    }

    @Override
    public void unapply(Hero hero) {
        // does not affect hero
        return;
    }

    @Override
    public PowerUp clone() {
        return new Magnetism();
    }

    @Override
    public String toString() {
        return super.toString() + " Magnetism";
    }

}
