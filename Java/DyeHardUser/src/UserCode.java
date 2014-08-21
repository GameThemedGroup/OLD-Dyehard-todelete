import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Engine.Vector2;
import dyehard.Actor;
import dyehard.DeveloperControls;
import dyehard.DyeHard;
import dyehard.Collectibles.Collectible;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.Ghost;
import dyehard.Collectibles.Gravity;
import dyehard.Collectibles.Invincibility;
import dyehard.Collectibles.Magnetism;
import dyehard.Collectibles.Overload;
import dyehard.Collectibles.PowerUp;
import dyehard.Collectibles.SlowDown;
import dyehard.Collectibles.SpeedUp;
import dyehard.Collectibles.Unarmed;
import dyehard.Enemies.BrainEnemy;
import dyehard.Enemies.Enemy;
import dyehard.Enemies.RedBeamEnemy;
import dyehard.Enemies.SpiderEnemy;
import dyehard.Obstacles.Debris;
import dyehard.Obstacles.Obstacle;
import dyehard.Player.Hero;
import dyehard.Util.Collision;
import dyehard.Util.Colors;
import dyehard.Util.Timer;
import dyehard.Weapons.LimitedAmmoWeapon;
import dyehard.Weapons.OverHeatWeapon;
import dyehard.Weapons.Projectile;
import dyehard.Weapons.SpreadFireWeapon;
import dyehard.Weapons.Weapon;
import dyehard.World.GameWorld;
import dyehard.World.GameWorldRegion;
import dyehard.World.Space;

public class UserCode extends DyeHard {
    private Timer enemySpawnTimer = new Timer(5000f);
    private List<Weapon> weaponRack;

    private static final int DyepackCount = 11;
    private static final int DebrisCount = 10;
    private static final int PowerupCount = 5;

    private static Random RANDOM = new Random();

    @Override
    protected void initialize() {
        Hero hero = GameWorld.getHero();
        weaponRack = new ArrayList<Weapon>();
        weaponRack.add(new Weapon(hero));
        weaponRack.add(new OverHeatWeapon(hero));
        weaponRack.add(new LimitedAmmoWeapon(hero));
        weaponRack.add(new SpreadFireWeapon(hero));

        new DeveloperControls(world, hero, keyboard);
    }

    @Override
    protected void update() {
        generateWorld();

        moveHero();
        selectWeapon();
        checkActorObstacleCollision();
        checkEnemyBulletCollision();

        if (enemySpawnTimer.isDone()) {
            generateEnemy();
            enemySpawnTimer.reset();
        }
    }

    public void generateEnemy() {
        Hero hero = GameWorld.getHero();

        float randomY = (GameWorld.TOP_EDGE - 5f - GameWorld.BOTTOM_EDGE + 5f)
                * RANDOM.nextFloat() + 0f + 5f;
        Vector2 position = new Vector2(GameWorld.RIGHT_EDGE + 5, randomY);

        switch (RANDOM.nextInt(3)) {
        case 1:
            new BrainEnemy(position, 7.5f, hero);
            break;
        case 2:
            new RedBeamEnemy(position, 7.5f, hero);
            break;
        default:
            new SpiderEnemy(position, 7.5f, hero);
            break;
        }
    }

    private void generateWorld() {
        GameWorldRegion lastRegion = GameWorld.getLastRegion();
        if (lastRegion == null || !(lastRegion instanceof Space)) {
            return;
        }

        Space space = (Space) lastRegion;
        if (!space.isInitialized()) {
            List<Collectible> collectibles = new ArrayList<Collectible>();
            collectibles.addAll(randomDyePacks(space, DyepackCount));
            collectibles.addAll(randomPowerups(space, PowerupCount));

            List<Debris> debris;
            debris = generateDebris(space, DebrisCount);
            space.initialize(collectibles, debris);
        }
    }

    private void moveHero() {
        Hero hero = GameWorld.getHero();

        float jetSpeed = 2.5f;
        Vector2 fakeGravity = new Vector2(0f, 0f);

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
        hero.velocity.add(totalThrust);

        if (keyboard.isButtonDown(KeyEvent.VK_F)) {
            hero.fireWeapon();
        }
    }

    private void checkEnemyBulletCollision() {
        Hero hero = GameWorld.getHero();
        List<Obstacle> obstacles = GameWorld.getObstacles();

        for (Obstacle obstacle : obstacles) {
            if (hero.collided(obstacle)) {
                handleCollision(hero, obstacle);
            }
        }

        List<Projectile> projectiles = GameWorld.getProjectiles();
        List<Enemy> enemies = GameWorld.getEnemies();

        for (Projectile p : projectiles) {
            for (Enemy e : enemies) {
                if (e.collided(p)) {
                    e.gotShot(p.color); // TODO replace with p.collideWith(e)
                    p.destroy();
                }
            }
        }
    }

    private void selectWeapon() {
        Hero hero = GameWorld.getHero();
        if (keyboard.isButtonDown(KeyEvent.VK_1)) {
            hero.setWeapon(weaponRack.get(0));
        } else if (keyboard.isButtonDown(KeyEvent.VK_2)) {
            hero.setWeapon(weaponRack.get(1));
        } else if (keyboard.isButtonDown(KeyEvent.VK_3)) {
            hero.setWeapon(weaponRack.get(2));
        } else if (keyboard.isButtonDown(KeyEvent.VK_4)) {
            hero.setWeapon(weaponRack.get(3));
        }
    }

    private void checkActorObstacleCollision() {
        Hero hero = GameWorld.getHero();
        List<Obstacle> obstacles = GameWorld.getObstacles();

        for (Obstacle obstacle : obstacles) {
            if (hero.collided(obstacle)) {
                handleCollision(hero, obstacle);
            }
        }

        List<Enemy> enemies = GameWorld.getEnemies();

        for (Enemy enemy : enemies) {
            for (Obstacle obstacle : obstacles) {
                if (enemy.collided(obstacle)) {
                    handleCollision(enemy, obstacle);
                }
            }
        }
    }

    private static void handleCollision(Actor actor, Obstacle obstacle) {
        // Check collisions with each character and push them out of the
        // Collidable. This causes the player and enemy units to glide along the
        // edges of the Collidable
        Vector2 out = new Vector2(0, 0);
        if (Collision.isOverlap(actor, obstacle, out)) {
            // Move the character so that it's no longer overlapping the
            // debris
            actor.center.add(out);

            // Stop the character from moving if they collide with the
            // Collidable
            if (Math.abs(out.getX()) > 0.01f) {
                if (Math.signum(out.getX()) != Math.signum(actor.velocity
                        .getX())) {
                    actor.velocity.setX(0f);
                }
            }

            if (Math.abs(out.getY()) > 0.01f) {
                if (Math.signum(out.getY()) != Math.signum(actor.velocity
                        .getY())) {
                    actor.velocity.setY(0f);
                }
            }
        }
    }

    private List<Debris> generateDebris(Space region, int count) {
        float regionWidth = region.getWidth() / count;
        float regionStart = region.leftEdge();
        float regionHeight = GameWorld.TOP_EDGE - GameWorld.BOTTOM_EDGE;

        List<Debris> debris = new ArrayList<Debris>();

        float posX, posY;

        for (int i = 0; i < count; ++i) {
            posX = regionStart + (i * regionWidth);
            posX += RANDOM.nextFloat() * regionWidth;

            posY = RANDOM.nextFloat() * (regionHeight - DyePack.height)
                    + DyePack.height / 2f;

            Debris d = new Debris();
            d.center = new Vector2(posX, posY);

            debris.add(d);
        }

        return debris;
    }

    private List<Collectible> randomDyePacks(Space region, int count) {
        float regionWidth = region.getWidth() / count;
        float regionStart = region.leftEdge();
        float regionHeight = GameWorld.TOP_EDGE - GameWorld.BOTTOM_EDGE;

        Hero hero = GameWorld.getHero();
        List<Collectible> randomDyes = new ArrayList<Collectible>();

        float posX, posY;

        for (int i = 0; i < count; ++i) {
            posX = regionStart + (i * regionWidth);
            posX += RANDOM.nextFloat() * regionWidth;

            posY = RANDOM.nextFloat() * (regionHeight - DyePack.height)
                    + DyePack.height / 2f;

            Color randomColor = Colors.randomColor();
            DyePack dye = new DyePack(hero, randomColor);
            dye.center = new Vector2(posX, posY);

            randomDyes.add(dye);
        }

        return randomDyes;
    }

    private List<Collectible> randomPowerups(Space region, int count) {
        float regionWidth = region.getWidth() / count;
        float regionStart = region.leftEdge();
        float regionHeight = GameWorld.TOP_EDGE - GameWorld.BOTTOM_EDGE;

        Hero hero = GameWorld.getHero();
        List<Collectible> collectibles = new ArrayList<Collectible>();

        float posX, posY;

        for (int i = 0; i < count; ++i) {
            posX = regionStart + (i * regionWidth);
            posX += RANDOM.nextFloat() * regionWidth;

            posY = (regionHeight - DyePack.height) * RANDOM.nextFloat()
                    + DyePack.height / 2f;

            PowerUp powerup = randomPowerup(hero);
            powerup.center = new Vector2(posX, posY);

            collectibles.add(powerup);
        }

        return collectibles;
    }

    private static PowerUp randomPowerup(Hero hero) {

        List<Enemy> enemies = GameWorld.getEnemies();

        switch (RANDOM.nextInt(8)) {
        case 0:
            return new SpeedUp(hero, enemies);
        case 1:
            return new SlowDown(hero, enemies);
        case 2:
            return new Ghost(hero);
        case 3:
            return new Invincibility(hero);
        case 4:
            return new Unarmed(hero);
        case 5:
            return new Magnetism(hero);
        case 6:
            return new Gravity(hero);
        default:
            return new Overload(hero);
        }
    }

}
