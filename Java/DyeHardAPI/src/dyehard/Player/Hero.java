package dyehard.Player;

import java.awt.Color;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import Engine.BaseCode;
import Engine.Vector2;
import Engine.World.BoundCollidedStatus;
import dyehard.Actor;
import dyehard.DyeHard;
import dyehard.Collectibles.Collectible;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.PowerUp;
import dyehard.Weapons.LimitedAmmoWeapon;
import dyehard.Weapons.Weapon;
import dyehard.World.GameWorld;

public class Hero extends Actor {

    // private final float rightBoundaryLimit = 0.85f; // percentage of screen

    public boolean isAlive = true;
    public Weapon currentWeapon;
    public float currentJetSpeed;
    public Vector2 currentGravity;
    public Set<PowerUp> powerups;

    public final Weapon defaultWeapon = new Weapon(this);
    public final float defaultJetSpeed = 2.5f;
    public final Vector2 defaultGravity = new Vector2(0f, 0f);

    private float speedLimitX = 50f;
    private static float drag = 0.97f; // smaller number means more reduction

    public boolean isGhost;
    public boolean isInvincible;
    public boolean isOverloaded;
    public boolean isUnarmed;
    public boolean isMagnetic;

    public final float attractionDistance = 25f;

    public String newestPowerUp;
    private State directionState;

    private DynamicDyePack dd;
    Vector2 previousVelocity;
    Vector2 currentVelocity;

    public enum State {
        UP, DOWN, LEFT, RIGHT, TOPLEFT, TOPRIGHT, BOTTOMLEFT, BOTTOMRIGHT, NEUTRAL
    }

    public Hero() {
        // TODO: The position 20f, 20f is a temporary value.
        super(new Vector2(20f, 20f), 5f, 5f);
        currentWeapon = defaultWeapon;
        currentJetSpeed = defaultJetSpeed;
        currentGravity = defaultGravity;
        powerups = new TreeSet<PowerUp>();

        isGhost = false;
        isInvincible = false;
        isOverloaded = false;
        isUnarmed = false;
        isMagnetic = false;
        directionState = State.NEUTRAL;

        dd = new DynamicDyePack(this);
        previousVelocity = new Vector2(0f, 0f);
        currentVelocity = new Vector2(0f, 0f);
    }

    public void setWeapon(Weapon weapon) {
        currentWeapon = weapon;
    }

    public void fireWeapon() {
        currentWeapon.fire();
    }

    public void updateMovement() {
        // Clamp the horizontal speed to speedLimit
        float velX = velocity.getX();
        velX = Math.min(speedLimitX, velX);
        velX = Math.max(-speedLimitX, velX);
        velocity.setX(velX);
        velocity.add(currentGravity);
        velocity.mult(drag);
        // Scale the velocity to the frame rate
        Vector2 frameVelocity = velocity.clone();
        frameVelocity.mult(DyeHard.DELTA_TIME);
        center.add(frameVelocity);
    }

    @Override
    public void update() {
        applyPowerups();
        updateMovement();
        clampToWorldBounds();
        updateState();

        if (isMagnetic) {
            attract();
        }
    }

    private void applyPowerups() {
        Set<PowerUp> destroyed = new TreeSet<PowerUp>();
        for (PowerUp p : powerups) {
            if (p.isDone()) {
                p.unapply();
                destroyed.add(p);
            }
        }

        powerups.removeAll(destroyed);

        for (PowerUp p : powerups) {
            p.apply();
        }
    }

    private void clampToWorldBounds() {
        // restrict the hero's movement to the boundary
        BoundCollidedStatus collisionStatus = collideWorldBound();
        if (collisionStatus != BoundCollidedStatus.INSIDEBOUND) {
            if (collisionStatus == BoundCollidedStatus.LEFT
                    || collisionStatus == BoundCollidedStatus.RIGHT) {
                velocity.setX(0);
                acceleration.setX(0);
            } else if (collisionStatus == BoundCollidedStatus.TOP
                    || collisionStatus == BoundCollidedStatus.BOTTOM) {
                velocity.setY(0);
                acceleration.setY(0);
            }
        }
        BaseCode.world.clampAtWorldBound(this);
    }

    public void updateState() {
        previousVelocity = currentVelocity.clone();
        currentVelocity = velocity.clone();
        Vector2 tempVelocity = currentVelocity.clone().sub(
                previousVelocity.clone());

        if (tempVelocity.getY() > 1f && tempVelocity.getX() < -1f) {
            directionState = State.TOPLEFT;
        } else if (tempVelocity.getY() > 1f && tempVelocity.getX() > 1f) {
            directionState = State.TOPRIGHT;
        } else if (tempVelocity.getY() < -1f && tempVelocity.getX() < -1f) {
            directionState = State.BOTTOMLEFT;
        } else if (tempVelocity.getY() < -1f && tempVelocity.getX() > 1f) {
            directionState = State.BOTTOMRIGHT;
        } else if (tempVelocity.getY() > 1f) {
            directionState = State.UP;
        } else if (tempVelocity.getY() < -1f) {
            directionState = State.DOWN;
        } else if (tempVelocity.getX() < -1f) {
            directionState = State.LEFT;
        } else if (tempVelocity.getX() > 1f) {
            directionState = State.RIGHT;
        } else {
            directionState = State.NEUTRAL;
        }
    }

    public State getState() {
        return directionState;
    }

    public void collect(DyePack dye) {
        dye.activate();
    }

    public void collect(PowerUp powerup) {
        powerups.add(powerup);
        powerup.activate();
    }

    public void increaseSpeed() {
        currentJetSpeed = 5f;
    }

    public void normalizeSpeed() {
        currentJetSpeed = 2.5f;
    }

    public void ghostOn() {
        isGhost = true;
    }

    public void ghostOff() {
        isGhost = false;
    }

    public void invincibilityOn() {
        isInvincible = true;
    }

    public void invincibilityOff() {
        isInvincible = false;
    }

    public void overloadOn() {
        isOverloaded = true;
    }

    public void overloadOff() {
        isOverloaded = false;
    }

    public void unarmedOn() {
        isUnarmed = true;
    }

    public void unarmedOff() {
        isUnarmed = false;
    }

    public void magnetismOn() {
        isMagnetic = true;
    }

    public void magnetismOff() {
        isMagnetic = false;
    }

    public void gravityOn() {
        currentGravity = new Vector2(0f, -1.5f);
    }

    public void gravityOff() {
        currentGravity = defaultGravity;
    }

    public void reloadLimitedAmmoWeapon() {
        if (currentWeapon instanceof LimitedAmmoWeapon) {
            ((LimitedAmmoWeapon) currentWeapon).recharge();
        }
    }

    private void attract() {
        // Gets the list of primitives from the current Space tile.
        List<Collectible> collectibles = GameWorld.getCollectible();

        for (Collectible collectible : collectibles) {
            // Finds the distance between the Hero and a
            // DyePack/PowerUp. The distance is the
            // hypotenuse of a 30, 60, 90 triangle.
            float A = Math.abs(center.getX() - collectible.center.getX());
            A = A * A;
            float B = Math.abs(center.getY() - collectible.center.getY());
            B = B * B;
            float C = (float) Math.sqrt(A + B);

            if (C <= attractionDistance) {
                Vector2 direction = new Vector2(center.getX()
                        - collectible.center.getX(), center.getY()
                        - collectible.center.getY());
                direction.normalize();
                collectible.velocity = direction.mult(0.85f);
            }

        }
    }

    @Override
    public void kill() {
        if (isInvincible) {
            return;
        }
        super.kill();
    }

    @Override
    public void setColor(Color color) {
        dd.updateColor(color);
        super.setColor(color);
    }
}
