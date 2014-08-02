package dyehard.Player;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Engine.BaseCode;
import Engine.KeyboardInput;
import Engine.Vector2;
import Engine.World.BoundCollidedStatus;
import dyehard.Actor;
import dyehard.DyeHard;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.PowerUp;
import dyehard.Enemies.Enemy;
import dyehard.Weapons.LimitedAmmoWeapon;
import dyehard.Weapons.OverHeatWeapon;
import dyehard.Weapons.SpreadFireWeapon;
import dyehard.Weapons.Weapon;

public class Hero extends Actor {
    private float speedLimitX = 50f;
    private static float jetSpeed = 2.5f;
    private static Vector2 fakeGravity = new Vector2(0f, -1.5f);

    private static float drag = 0.97f; // smaller number means more reduction
    // private final float rightBoundaryLimit = 0.85f; // percentage of screen
    private int collectedDyepacks;
    private int collectedPowerups;
    private KeyboardInput keyboard;
    private Weapon weapon;
    private ArrayList<Weapon> weaponRack;

    public Hero(KeyboardInput keyboard) {
        // TODO: The position 20f, 20f is a temporary value.
        super(new Vector2(20f, 20f), 5f, 5f);
        collectedDyepacks = 0;
        collectedPowerups = 0;
        this.keyboard = keyboard;
        weaponRack = new ArrayList<Weapon>();
        createWeapons();
        weapon = weaponRack.get(0); // set initial weapon to first
    }

    @Override
    public void draw() {
        weapon.draw();
        super.draw();
    }

    public void updateMovement() {
        // Clamp the horizontal speed to speedLimit
        float velX = velocity.getX();
        velX = Math.min(speedLimitX, velX);
        velX = Math.max(-speedLimitX, velX);
        velocity.setX(velX);

        velocity.add(fakeGravity);
        velocity.mult(drag);

        // Scale the velocity to the frame rate
        Vector2 frameVelocity = velocity.clone();
        frameVelocity.mult(DyeHard.DELTA_TIME);
        center.add(frameVelocity);
    }

    @Override
    public void destroy() {
        for (Weapon w : weaponRack) {
            w.destroy();
        }
        super.destroy();
    }

    @Override
    public void update() {
        handleInput();
        updateMovement();
        selectWeapon();
        for (Weapon w : weaponRack) {
            w.update();
        }
        clampToWorldBounds();
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
            totalThrust.add(new Vector2(0f, jetSpeed - fakeGravity.getY()));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_LEFT)) {
            totalThrust.add(new Vector2(-jetSpeed, 0f));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_DOWN)) {
            totalThrust.add(new Vector2(0f, -jetSpeed));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_RIGHT)) {
            totalThrust.add(new Vector2(jetSpeed, 0));
        }

        velocity.add(totalThrust);

        if (keyboard.isButtonDown(KeyEvent.VK_F)) {
            weapon.fire();
        }
    }

    private void selectWeapon() {
        if (keyboard.isButtonDown(KeyEvent.VK_1)) {
            weapon = weaponRack.get(0);
        } else if (keyboard.isButtonDown(KeyEvent.VK_2)) {
            weapon = weaponRack.get(1);
        } else if (keyboard.isButtonDown(KeyEvent.VK_3)) {
            weapon = weaponRack.get(2);
        } else if (keyboard.isButtonDown(KeyEvent.VK_4)) {
            weapon = weaponRack.get(3);
        }
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        for (Weapon w : weaponRack) {
            w.setEnemies(enemies);
        }
    }

    public void collect(DyePack dye) {
        dye.activate();
        collectedDyepacks += 1;
    }

    public void collect(PowerUp powerup) {
        powerup.activate();
        collectedPowerups += 1;
    }

    private void createWeapons() {
        weaponRack.add(new Weapon(this));
        weaponRack.add(new OverHeatWeapon(this));
        weaponRack.add(new LimitedAmmoWeapon(this));
        weaponRack.add(new SpreadFireWeapon(this));
    }

    public int dyepacksCollected() {
        return collectedDyepacks;
    }

    // Powerups Functions
    public int powerupsCollected() {
        return collectedPowerups;
    }

    public void increaseSpeed() {
    }

    public void normalizeSpeed() {
    }

    public void setInvisible() {
    }

    public void setVisible() {
    }
}
