package dyehard.Player;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import Engine.BaseCode;
import Engine.Primitive;
import Engine.Vector2;
import Engine.World.BoundCollidedStatus;
import dyehard.Actor;
import dyehard.Collidable;
import dyehard.Configuration;
import dyehard.DyeHard;
import dyehard.DyehardKeyboard;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.Invincibility;
import dyehard.Collectibles.PowerUp;
import dyehard.Player.HeroInterfaces.HeroCollision;
import dyehard.Player.HeroInterfaces.HeroDamage;
import dyehard.Util.Colors;
import dyehard.Weapons.OverHeatWeapon;
import dyehard.Weapons.Weapon;
import dyehard.World.GameState;

public class Hero extends Actor implements HeroCollision, HeroDamage {
    public HeroCollision collisionHandler;
    public HeroDamage damageHandler;
    public Weapon currentWeapon;
    public float currentJetSpeed;
    public Vector2 currentGravity;
    public Set<PowerUp> powerups;
    public CurPowerUp curPowerUp;
    public boolean debugInvincibility;
    public boolean isInvin;

    public final HeroCollision defaultCollisionHandler = this;
    public final HeroDamage defaultDamageHandler = this;
    public final Weapon defaultWeapon = new OverHeatWeapon(this);
    public final float defaultJetSpeed = Configuration.heroJetSpeed;
    public final Vector2 defaultGravity = new Vector2(0f, 0f);
    public Vector2 totalThrust = new Vector2();

    private final float speedLimitX = Configuration.heroSpeedLimit;
    private static float drag = Configuration.heroDrag;

    private int collectedDyepacks;
    private int collectedPowerups;

    protected Direction directionState;
    protected DynamicDyePack dynamicDyepack;
    protected Vector2 previousVelocity;
    protected Vector2 currentVelocity;
    protected final static Vector2 startingLocation = new Vector2(20f, 20f);

    private final ArrayList<Weapon> weaponRack;
    private final HashMap<Integer, Integer> weaponHotkeys;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, TOPLEFT, TOPRIGHT, BOTTOMLEFT, BOTTOMRIGHT, NEUTRAL
    }

    public enum CurPowerUp {
        GHOST, INVIN, MAGNET, OVERLOAD, SLOW, SPEED, UNARMED, GRAVITY, NONE
    }

    public Hero() {
        super(startingLocation.clone(), Configuration.heroWidth,
                Configuration.heroHeight); // TODO remove magic numbers

        curPowerUp = CurPowerUp.NONE;
        color = Colors.randomColor();
        directionState = Direction.NEUTRAL;
        dynamicDyepack = new DynamicDyePack(this);
        texture = BaseCode.resources.loadImage("Textures/Hero/Dye.png");

        collectedDyepacks = 0;
        collectedPowerups = 0;

        powerups = new TreeSet<PowerUp>();

        currentJetSpeed = defaultJetSpeed;
        currentGravity = defaultGravity;

        weaponRack = new ArrayList<Weapon>();
        weaponRack.add(defaultWeapon); // add default weapon
        currentWeapon = defaultWeapon;

        // Maps number keys to weaponRack index
        weaponHotkeys = new HashMap<Integer, Integer>();
        weaponHotkeys.put(KeyEvent.VK_1, 0);
        weaponHotkeys.put(KeyEvent.VK_2, 1);
        weaponHotkeys.put(KeyEvent.VK_3, 2);
        weaponHotkeys.put(KeyEvent.VK_4, 3);

        previousVelocity = new Vector2(0f, 0f);
        currentVelocity = new Vector2(0f, 0f);

        isInvin = false;
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
        handleInput();
        updateDirectionState();
        updateMovement();
        // selectWeapon();
        clampToWorldBounds();

        dynamicDyepack.update();
    }

    private void applyPowerups() {
        Set<PowerUp> destroyed = new TreeSet<PowerUp>();
        for (PowerUp p : powerups) {
            if (p.isDone()) {
                p.unapply(this);
                destroyed.add(p);
            }
        }

        powerups.removeAll(destroyed);

        for (PowerUp p : powerups) {
            p.apply(this);
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

    public void moveUp() {
        // Upward speed needs to counter the effects of gravity
        totalThrust
                .add(new Vector2(0f, defaultJetSpeed - currentGravity.getY()));
    }

    public void moveDown() {
        totalThrust.add(new Vector2(0f, -defaultJetSpeed));
    }

    public void moveLeft() {
        totalThrust.add(new Vector2(-defaultJetSpeed, 0f));
    }

    public void moveRight() {
        totalThrust.add(new Vector2(defaultJetSpeed, 0));
    }

    private void handleInput() {
        velocity.add(totalThrust);
        totalThrust.set(0f, 0f);
    }

    public void updateDirectionState() {
        previousVelocity = currentVelocity.clone();
        currentVelocity = velocity.clone();
        Vector2 tempVelocity = currentVelocity.clone().sub(
                previousVelocity.clone());

        if (tempVelocity.getY() > 1f && tempVelocity.getX() < -1f) {
            directionState = Direction.TOPLEFT;
        } else if (tempVelocity.getY() > 1f && tempVelocity.getX() > 1f) {
            directionState = Direction.TOPRIGHT;
        } else if (tempVelocity.getY() < -1f && tempVelocity.getX() < -1f) {
            directionState = Direction.BOTTOMLEFT;
        } else if (tempVelocity.getY() < -1f && tempVelocity.getX() > 1f) {
            directionState = Direction.BOTTOMRIGHT;
        } else if (tempVelocity.getY() > 1f) {
            directionState = Direction.UP;
        } else if (tempVelocity.getY() < -1f) {
            directionState = Direction.DOWN;
        } else if (tempVelocity.getX() < -1f) {
            directionState = Direction.LEFT;
        } else if (tempVelocity.getX() > 1f) {
            directionState = Direction.RIGHT;
        } else {
            directionState = Direction.NEUTRAL;
        }
    }

    // Select a weapon in the weapon rack based on the input
    private void selectWeapon() {
        for (int hotkey : weaponHotkeys.keySet()) {
            if (DyehardKeyboard.isKeyDown(hotkey)) {
                int weaponIndex = weaponHotkeys.get(hotkey);
                if (weaponIndex < weaponRack.size() && weaponIndex >= 0) {
                    currentWeapon = weaponRack.get(weaponIndex);
                }
            }
        }
    }

    public void collect(DyePack dye) {
        dye.activate(this);
        collectedDyepacks += 1;
    }

    public void collect(PowerUp powerup) {
        // Only one powerup can be active at a time
        for (PowerUp p : powerups) {
            p.unapply(this);
        }
        powerups.clear();

        powerups.add(powerup);
        powerup.activate(this);
        collectedPowerups += 1;
    }

    public void registerWeapon(Weapon weapon) {
        weaponRack.add(weapon);
    }

    public int dyepacksCollected() {
        return collectedDyepacks;
    }

    // Powerups Functions
    public int powerupsCollected() {
        return collectedPowerups;
    }

    @Override
    public void kill(Primitive who) {
        if (damageHandler != null) {
            damageHandler.damageHero(this, who);
        } else {
            damageHero(this, who);
        }
    }

    @Override
    public void damageHero(Hero hero, Primitive who) {
        powerups.clear();
        powerups.add(new Invincibility());
        applyPowerups();

        if (!debugInvincibility) {
            GameState.RemainingLives--;
        }

        if (GameState.RemainingLives <= 0) {
            alive = false;
        } else {
            hero.center.set(startingLocation.clone());
        }
    }

    @Override
    public void handleCollision(Collidable other) {
        if (collisionHandler != null) {
            collisionHandler.collideWithHero(this, other);
        } else {
            collideWithHero(this, other);
        }
    }

    @Override
    public void collideWithHero(Hero hero, Collidable other) {
        super.handleCollision(other);
    }

    @Override
    public void destroy() {
        return;
    }
}
