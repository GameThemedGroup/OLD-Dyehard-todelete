import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Engine.Vector2;
import dyehard.Actor;
import dyehard.DeveloperControls;
import dyehard.DyeHard;
import dyehard.Enemies.BrainEnemy;
import dyehard.Enemies.Enemy;
import dyehard.Enemies.RedBeamEnemy;
import dyehard.Enemies.SpiderEnemy;
import dyehard.Obstacles.Obstacle;
import dyehard.Player.Hero;
import dyehard.Util.Collision;
import dyehard.Util.Timer;
import dyehard.Weapons.LimitedAmmoWeapon;
import dyehard.Weapons.OverHeatWeapon;
import dyehard.Weapons.Projectile;
import dyehard.Weapons.SpreadFireWeapon;
import dyehard.Weapons.Weapon;
import dyehard.World.GameWorld;

public class UserCode extends DyeHard {

    private Timer enemySpawnTimer = new Timer(5000f);
    private List<Weapon> weaponRack;

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

        float randomY = RANDOM.nextInt((int) GameWorld.TOP_EDGE - 8) + 5;
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

}
