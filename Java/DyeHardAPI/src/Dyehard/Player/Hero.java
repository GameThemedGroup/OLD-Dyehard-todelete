package dyehard.Player;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import Engine.BaseCode;
import Engine.KeyboardInput;
import Engine.Primitive;
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
import dyehard.World.GameWorld;
import dyehard.World.Space;

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
    public boolean isGhost;
    public boolean isInvincible;
    public boolean isOverloaded;
    public boolean isUnarmed;
    public boolean isMagnetic;
    public final float attractionDistance = 25f;

    public Hero(KeyboardInput keyboard) {
        // TODO: The position 20f, 20f is a temporary value.
        super(new Vector2(20f, 20f), 5f, 5f);
        collectedDyepacks = 0;
        collectedPowerups = 0;
        this.keyboard = keyboard;
        weaponRack = new ArrayList<Weapon>();
        createWeapons();
        weapon = weaponRack.get(0); // set initial weapon to first
        isGhost = false;
        isInvincible = false;
        isOverloaded = false;
        isUnarmed = false;
        isMagnetic = false;
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

        if (isMagnetic) {
            attract();
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
        jetSpeed = 5f;
    }

    public void normalizeSpeed() {
        jetSpeed = 2.5f;
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

    private void attract() {
        if (GameWorld.gameRegions.peek() instanceof Space) {
            // Gets the list of primitives from the current Space tile.
            List<Primitive> primitives = ((Space) GameWorld.gameRegions.peek())
                    .getPrimitives();

            for (Primitive p : primitives) {
                if (p instanceof DyePack || p instanceof PowerUp) {
                    // Finds the distance between the Hero and a
                    // DyePack/PowerUp. The distance is the
                    // hypotenuse of a 30, 60, 90 triangle.
                    float A = Math.abs(center.getX() - p.center.getX());
                    A = A * A;
                    float B = Math.abs(center.getY() - p.center.getY());
                    B = B * B;
                    float C = (float) Math.sqrt(A + B);

                    if (C <= attractionDistance) {
                        Vector2 direction = new Vector2(center.getX()
                                - p.center.getX(), center.getY()
                                - p.center.getY());
                        direction.normalize();
                        p.velocity = direction.mult(0.85f);
                    }
                }
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
}
