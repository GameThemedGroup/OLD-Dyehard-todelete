package dyehard.Player;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import Engine.BaseCode;
import Engine.Primitive;
import Engine.Rectangle;
import Engine.Vector2;
import Engine.World.BoundCollidedStatus;
import dyehard.Actor;
import dyehard.Collidable;
import dyehard.Configuration;
import dyehard.DHR;
import dyehard.DyeHard;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.Invincibility;
import dyehard.Collectibles.PowerUp;
import dyehard.Player.HeroInterfaces.HeroCollision;
import dyehard.Player.HeroInterfaces.HeroDamage;
import dyehard.Util.Colors;
import dyehard.Util.ImageTint;
import dyehard.Weapons.OverHeatWeapon;
import dyehard.Weapons.Weapon;
import dyehard.World.GameState;
import dyehard.World.Gate.DeathGate;

public class Hero extends Actor implements HeroCollision, HeroDamage {
    public static HashMap<Color, BufferedImage> chargerIdleTextures = new HashMap<Color, BufferedImage>();
    public static HashMap<Color, BufferedImage> chargerAttackTextures = new HashMap<Color, BufferedImage>();
    public static HashMap<Color, BufferedImage> regularLeftTextures = new HashMap<Color, BufferedImage>();
    public static HashMap<Color, BufferedImage> regularRightTextures = new HashMap<Color, BufferedImage>();
    public static HashMap<Color, BufferedImage> portalEnemyTextures = new HashMap<Color, BufferedImage>();
    private static HashMap<Direction, BufferedImage> dyeTextures = new HashMap<Direction, BufferedImage>();
    private static HashMap<Direction, BufferedImage> dyeFireTextures = new HashMap<Direction, BufferedImage>();

    public boolean collisionOn = true;
    public boolean damageOn = true;
    public Weapon currentWeapon;
    public float currentJetSpeed;
    public Vector2 currentGravity;
    public Set<PowerUp> powerups;
    public CurPowerUp curPowerUp;
    public boolean debugInvincibility;
    public boolean isInvin;
    public boolean isFiring;

    public final Weapon defaultWeapon = new OverHeatWeapon(this);
    public final float defaultJetSpeed = Configuration.heroJetSpeed;
    public final Vector2 defaultGravity = new Vector2(0f, 0f);
    public Vector2 totalThrust = new Vector2();
    public HashMap<Color, BufferedImage> bulletTextures = new HashMap<Color, BufferedImage>();
    public Vector2 bulletSize;

    private final float speedLimitX = Configuration.heroSpeedLimit;
    private static float drag = Configuration.heroDrag;

    private int collectedDyepacks;
    private int collectedPowerups;
    private final float sizeScale;

    protected Direction directionState;
    protected DynamicDyePack dynamicDyepack;
    protected Vector2 previousVelocity;
    protected Vector2 currentVelocity;
    protected final static Vector2 startingLocation = new Vector2(20f, 20f);
    protected Rectangle r;

    private final ArrayList<Weapon> weaponRack;
    private final HashMap<Integer, Integer> weaponHotkeys;

    public enum Direction {
        UP, DOWN, LEFT, NEUTRAL
    }

    public enum CurPowerUp {
        GHOST, INVIN, MAGNET, OVERLOAD, SLOW, SPEED, UNARMED, GRAVITY, REPEL, NONE
    }

    static {
        BufferedImage idle = BaseCode.resources
                .loadImage("Textures/Enemies/Charger_AnimSheet_Idle.png");
        BufferedImage attack = BaseCode.resources
                .loadImage("Textures/Enemies/Charger_AnimSheet_Attack.png");
        BufferedImage regularLeft = BaseCode.resources
                .loadImage("Textures/Enemies/Regular_AnimSheet_Left.png");
        BufferedImage regularRight = BaseCode.resources
                .loadImage("Textures/Enemies/Regular_AnimSheet_Right.png");
        BufferedImage portalEnemy = BaseCode.resources
                .loadImage("Textures/Enemies/PortalMinion_AnimSheet_Left.png");

        // Fill the hashmap with tinted images for later use
        for (int i = 0; i < 6; i++) {
            Color temp = Colors.colorPicker(i);
            chargerIdleTextures.put(temp,
                    ImageTint.tintedImage(idle, temp, 0.25f));
            chargerAttackTextures.put(temp,
                    ImageTint.tintedImage(attack, temp, 0.25f));
            regularLeftTextures.put(temp,
                    ImageTint.tintedImage(regularLeft, temp, 0.25f));
            regularRightTextures.put(temp,
                    ImageTint.tintedImage(regularRight, temp, 0.25f));
            portalEnemyTextures.put(temp,
                    ImageTint.tintedImage(portalEnemy, temp, 0.25f));
        }

        for (Direction dir : Direction.values()) {
            dyeTextures.put(
                    dir,
                    BaseCode.resources.loadImage("Textures/Hero/Dye_"
                            + dir.toString() + ".png"));
            dyeFireTextures.put(
                    dir,
                    BaseCode.resources.loadImage("Textures/Hero/Dye_"
                            + dir.toString() + "_Fire.png"));
        }
    }

    public Hero() {
        super(startingLocation.clone(), Configuration.heroWidth,
                Configuration.heroHeight); // TODO remove magic numbers

        sizeScale = size.getY() / 9f;

        // TODO magic numbers
        r = DHR.getScaledRectangle(new Vector2(1920, 1080), new Vector2(590,
                120), "Textures/dye_attack_muzzle_flash_animation.png");

        curPowerUp = CurPowerUp.NONE;
        color = Colors.randomColor();
        if (!bulletTextures.containsKey(color)) {
            bulletTextures.put(color,
                    ImageTint.tintedImage(r.texture, color, 1f));
        }
        directionState = Direction.NEUTRAL;
        dynamicDyepack = new DynamicDyePack(this);
        texture = BaseCode.resources.loadImage("Textures/Hero/Dye_NEUTRAL.png");

        collectedDyepacks = 0;
        collectedPowerups = 0;

        bulletSize = r.size;

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
        isFiring = false;
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
        // frameVelocity.mult(DyeHard.DELTA_TIME);
        center.add(frameVelocity);
    }

    @Override
    public void update() {
        applyPowerups();
        handleInput();
        // updateDirectionState();
        // updateMovement();
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

    public void moveTo(float x, float y) {
        if (DyeHard.getState() == DyeHard.State.PLAYING) {
            float xOffset = x - center.getX();
            float yOffset = y - center.getY();

            if (Math.abs(xOffset) + Math.abs(yOffset) < 0.2f) {
                directionState = Direction.NEUTRAL;
            } else if ((xOffset * xOffset) > (yOffset * yOffset)) {
                if (xOffset > 0) {
                    directionState = Direction.NEUTRAL; // TODO change to Right
                                                        // when
                                                        // texture comes
                } else {
                    directionState = Direction.LEFT;
                }
            } else {
                if (yOffset > 0) {
                    directionState = Direction.UP;
                } else {
                    directionState = Direction.DOWN;
                }
            }

            setTexture();

            if (xOffset > 0) {
                center.setX(center.getX() + Math.min((xOffset * 0.2f), 2f));
            } else if (xOffset < 0) {
                center.setX(center.getX() + Math.max((xOffset * 0.2f), -2f));
            }

            if (yOffset > 0) {
                center.setY(center.getY() + Math.min((yOffset * 0.2f), 2f));
            } else if (yOffset < 0) {
                center.setY(center.getY() + Math.max((yOffset * 0.2f), -2f));
            }

            // if ((!collideRight) && (xOffset > 0)) {
            // center.setX(center.getX() + (xOffset * 0.2f));
            // } else if ((!collideLeft) && (xOffset < 0)) {
            // center.setX(center.getX() + (xOffset * 0.2f));
            // }
            //
            // if ((!collideUp) && (yOffset > 0)) {
            // center.setY(center.getY() + (yOffset * 0.2f));
            // } else if ((!collideDown) && (yOffset < 0)) {
            // center.setY(center.getY() + (yOffset * 0.2f));
            // }
        }
    }

    private void setTexture() {
        if (isFiring) {
            switch (directionState) {
            case NEUTRAL:
                size.set(new Vector2(3.35f * sizeScale, 8.4f * sizeScale));
                break;
            case UP:
                size.set(new Vector2(5.2f * sizeScale, 6.9f * sizeScale));
                break;
            case DOWN:
                size.set(new Vector2(3.4f * sizeScale, 5.8f * sizeScale));
                break;
            case LEFT:
                size.set(new Vector2(3.35f * sizeScale, 6.2f * sizeScale));
                break;
            }
            texture = dyeFireTextures.get(directionState);
        } else {
            switch (directionState) {
            case NEUTRAL:
                size.set(new Vector2(Configuration.heroWidth,
                        Configuration.heroHeight));
                break;
            case UP:
                size.set(new Vector2(4f * sizeScale, 7.25f * sizeScale));
                break;
            case DOWN:
                size.set(new Vector2(3.55f * sizeScale, 6.05f * sizeScale));
                break;
            case LEFT:
                size.set(new Vector2(3.9f * sizeScale, 5.2f * sizeScale));
                break;
            }
            texture = dyeTextures.get(directionState);
        }
    }

    private void handleInput() {
        velocity.add(totalThrust);
        totalThrust.set(0f, 0f);
    }

    // public void updateDirectionState() {
    // previousVelocity = currentVelocity.clone();
    // currentVelocity = velocity.clone();
    // Vector2 tempVelocity = currentVelocity.clone().sub(
    // previousVelocity.clone());
    //
    // if (tempVelocity.getY() > 1f && tempVelocity.getX() < -1f) {
    // directionState = Direction.TOPLEFT;
    // } else if (tempVelocity.getY() > 1f && tempVelocity.getX() > 1f) {
    // directionState = Direction.TOPRIGHT;
    // } else if (tempVelocity.getY() < -1f && tempVelocity.getX() < -1f) {
    // directionState = Direction.BOTTOMLEFT;
    // } else if (tempVelocity.getY() < -1f && tempVelocity.getX() > 1f) {
    // directionState = Direction.BOTTOMRIGHT;
    // } else if (tempVelocity.getY() > 1f) {
    // directionState = Direction.UP;
    // } else if (tempVelocity.getY() < -1f) {
    // directionState = Direction.DOWN;
    // } else if (tempVelocity.getX() < -1f) {
    // directionState = Direction.LEFT;
    // } else if (tempVelocity.getX() > 1f) {
    // directionState = Direction.RIGHT;
    // } else {
    // directionState = Direction.NEUTRAL;
    // }
    // }

    // Select a weapon in the weapon rack based on the input
    // private void selectWeapon() {
    // for (int hotkey : weaponHotkeys.keySet()) {
    // if (DyehardKeyboard.isKeyDown(hotkey)) {
    // int weaponIndex = weaponHotkeys.get(hotkey);
    // if (weaponIndex < weaponRack.size() && weaponIndex >= 0) {
    // currentWeapon = weaponRack.get(weaponIndex);
    // }
    // }
    // }
    // }

    public void alwaysOnTop() {
        alwaysOnTop = true;
        dynamicDyepack.alwaysOnTop = true;
    }

    public void drawOnTop() {
        removeFromAutoDrawSet();
        addToAutoDrawSet();
        dynamicDyepack.removeFromAutoDrawSet();
        dynamicDyepack.addToAutoDrawSet();
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
        if ((damageOn)
                || ((who instanceof DeathGate) && curPowerUp == CurPowerUp.GHOST)) {

            damageHero(this, who);
        }
    }

    @Override
    public void damageHero(Hero hero, Primitive who) {
        // Only one powerup can be active at a time
        for (PowerUp p : powerups) {
            p.unapply(this);
        }
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
        if (collisionOn) {
            super.handleCollision(other);
        }
    }

    @Override
    public void collideWithHero(Hero hero, Collidable other) {
        super.handleCollision(other);
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
        if (!bulletTextures.containsKey(color)) {
            bulletTextures.put(color,
                    ImageTint.tintedImage(r.texture, color, 1f));
        }
    }

    @Override
    public void destroy() {
        return;
    }
}
