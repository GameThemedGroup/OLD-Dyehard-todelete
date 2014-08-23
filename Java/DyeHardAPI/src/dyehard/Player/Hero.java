package dyehard.Player;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import Engine.BaseCode;
import Engine.KeyboardInput;
import Engine.Primitive;
import Engine.Vector2;
import Engine.World.BoundCollidedStatus;
import dyehard.Actor;
import dyehard.Collidable;
import dyehard.DyeHard;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.PowerUp;
import dyehard.Player.HeroInterfaces.HeroCollision;
import dyehard.Player.HeroInterfaces.HeroDamage;
import dyehard.Weapons.Weapon;

public class Hero extends Actor implements HeroCollision, HeroDamage {
    public HeroCollision collisionHandler;
    public HeroDamage damageHandler;
    public boolean isAlive = true;
    public Weapon currentWeapon;
    public float currentJetSpeed;
    public Vector2 currentGravity;
    public Set<PowerUp> powerups;

    public final HeroCollision defaultCollisionHandler = this;
    public final HeroDamage defaultDamageHandler = this;
    public final Weapon defaultWeapon = new Weapon(this);
    public final float defaultJetSpeed = 2.5f;
    public final Vector2 defaultGravity = new Vector2(0f, -1.5f);

    private float speedLimitX = 50f;
    private static float drag = 0.97f; // smaller number means more reduction

    private int collectedDyepacks;
    private int collectedPowerups;

    private KeyboardInput keyboard;
    private ArrayList<Weapon> weaponRack;
    private HashMap<Integer, Integer> weaponHotkeys;

    public Hero(KeyboardInput keyboard) {
        super(new Vector2(20f, 20f), 5f, 5f); // TODO remove magic numbers
        this.keyboard = keyboard;

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
    }

    @Override
    public void draw() {
        super.draw();
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
        updateMovement();
        selectWeapon();
        clampToWorldBounds();
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

    private void handleInput() {
        Vector2 totalThrust = new Vector2();
        if (keyboard.isButtonDown(KeyEvent.VK_UP)) {
            // Upward speed needs to counter the effects of gravity
            totalThrust.add(new Vector2(0f, defaultJetSpeed
                    - currentGravity.getY()));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_LEFT)) {
            totalThrust.add(new Vector2(-defaultJetSpeed, 0f));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_DOWN)) {
            totalThrust.add(new Vector2(0f, -defaultJetSpeed));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_RIGHT)) {
            totalThrust.add(new Vector2(defaultJetSpeed, 0));
        }

        velocity.add(totalThrust);

        if (keyboard.isButtonDown(KeyEvent.VK_F)) {
            currentWeapon.fire();
        }
    }

    // Select a weapon in the weapon rack based on the input
    private void selectWeapon() {
        for (int hotkey : weaponHotkeys.keySet()) {
            if (keyboard.isButtonDown(hotkey)) {
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
        hero.isAlive = false;
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
}