package dyehard;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import Engine.KeyboardInput;
import Engine.Text;
import Engine.Vector2;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.Ghost;
import dyehard.Collectibles.Gravity;
import dyehard.Collectibles.Invincibility;
import dyehard.Collectibles.Magnetism;
import dyehard.Collectibles.Overload;
import dyehard.Collectibles.PowerUpManager;
import dyehard.Collectibles.SlowDown;
import dyehard.Collectibles.SpeedUp;
import dyehard.Collectibles.Unarmed;
import dyehard.Enemies.BrainEnemy;
import dyehard.Enemies.EnemyManager;
import dyehard.Enemies.RedBeamEnemy;
import dyehard.Enemies.SpiderEnemy;
import dyehard.Player.Hero;
import dyehard.Util.Colors;
import dyehard.Util.Timer;
import dyehard.World.GameWorld;
import dyehard.World.GameWorldRegion;
import dyehard.World.Space;

public class DeveloperControls {
    private KeyboardInput keyboard;
    private Hero hero;
    private EnemyManager eManager;
    private LinkedList<GameWorldRegion> onscreen;
    private Timer timer;
    private Text ghostText;
    private Text invincibilityText;
    private Text overloadText;
    private Text unarmedText;
    private Text magnetismText;
    private Text gravityText;
    private Text enemySpeedText;
    private Text heroWeaponText;
    private Text recentPowerUpText;

    public DeveloperControls(GameWorld world, Space space, Hero hero,
            KeyboardInput keyboard, EnemyManager eManager,
            LinkedList<GameWorldRegion> onscreen) {
        this.keyboard = keyboard;
        this.hero = hero;
        this.eManager = eManager;
        this.onscreen = onscreen;
        timer = new Timer(500f);

        ghostText = new Text("", 3f, 57f);
        ghostText.setFrontColor(Color.white);
        ghostText.setBackColor(Color.black);
        ghostText.setFontSize(18);
        ghostText.setFontName("Arial");
        invincibilityText = new Text("", 3f, 55f);
        invincibilityText.setFrontColor(Color.white);
        invincibilityText.setBackColor(Color.black);
        invincibilityText.setFontSize(18);
        invincibilityText.setFontName("Arial");
        overloadText = new Text("", 3f, 53f);
        overloadText.setFrontColor(Color.white);
        overloadText.setBackColor(Color.black);
        overloadText.setFontSize(18);
        overloadText.setFontName("Arial");
        unarmedText = new Text("", 3f, 51f);
        unarmedText.setFrontColor(Color.white);
        unarmedText.setBackColor(Color.black);
        unarmedText.setFontSize(18);
        unarmedText.setFontName("Arial");
        magnetismText = new Text("", 3f, 49f);
        magnetismText.setFrontColor(Color.white);
        magnetismText.setBackColor(Color.black);
        magnetismText.setFontSize(18);
        magnetismText.setFontName("Arial");
        gravityText = new Text("", 3f, 47f);
        gravityText.setFrontColor(Color.white);
        gravityText.setBackColor(Color.black);
        gravityText.setFontSize(18);
        gravityText.setFontName("Arial");
        heroWeaponText = new Text("", 20f, 57f);
        heroWeaponText.setFrontColor(Color.white);
        heroWeaponText.setBackColor(Color.black);
        heroWeaponText.setFontSize(18);
        heroWeaponText.setFontName("Arial");
        enemySpeedText = new Text("", 3f, 45f);
        enemySpeedText.setFrontColor(Color.white);
        enemySpeedText.setBackColor(Color.black);
        enemySpeedText.setFontSize(18);
        enemySpeedText.setFontName("Arial");
        recentPowerUpText = new Text("", 20f, 55f);
        recentPowerUpText.setFrontColor(Color.white);
        recentPowerUpText.setBackColor(Color.black);
        recentPowerUpText.setFontSize(18);
        recentPowerUpText.setFontName("Arial");
    }

    public void update() {
        if (timer.isDone()) {
            if (onscreen.getFirst() instanceof Space) {
                // 'E' to add random enemies
                if (keyboard.isButtonDown(KeyEvent.VK_E)) {
                    Random rand = new Random();
                    float randomY = (50f - 5f - 0f + 5f) * rand.nextFloat()
                            + 0f + 5f;
                    Vector2 position = new Vector2(83.3f + 5, randomY);
                    switch (rand.nextInt(3)) {
                    case 1:
                        ((Space) onscreen.getFirst()).AddEnemy(new BrainEnemy(
                                position, 7.5f, hero));
                        break;
                    case 2:
                        ((Space) onscreen.getFirst())
                                .AddEnemy(new RedBeamEnemy(position, 7.5f, hero));
                        break;
                    default:
                        ((Space) onscreen.getFirst()).AddEnemy(new SpiderEnemy(
                                position, 7.5f, hero));
                        break;
                    }
                    timer.reset();
                }
                // '0' to add random dye pack
                if (keyboard.isButtonDown(KeyEvent.VK_0)) {
                    ((Space) onscreen.getFirst()).AddDyepack(new DyePack(hero,
                            70f, 70f, Colors.randomColor()));
                    timer.reset();
                }
                if (keyboard.isButtonDown(KeyEvent.VK_Z)) {
                    ((Space) onscreen.getFirst()).AddPowerup(new Ghost(hero,
                            70f, 70f));
                    timer.reset();
                }
                if (keyboard.isButtonDown(KeyEvent.VK_X)) {
                    ((Space) onscreen.getFirst()).AddPowerup(new Invincibility(
                            hero, 70f, 70f));
                    timer.reset();
                }
                if (keyboard.isButtonDown(KeyEvent.VK_C)) {
                    ((Space) onscreen.getFirst()).AddPowerup(new Overload(hero,
                            70f, 70f));
                    timer.reset();
                }
                if (keyboard.isButtonDown(KeyEvent.VK_V)) {
                    ((Space) onscreen.getFirst()).AddPowerup(new SpeedUp(hero,
                            eManager.getEnemies(), 70f, 70f));
                    timer.reset();
                }
                if (keyboard.isButtonDown(KeyEvent.VK_B)) {
                    ((Space) onscreen.getFirst()).AddPowerup(new SlowDown(hero,
                            eManager.getEnemies(), 70f, 70f));
                    timer.reset();
                }
                if (keyboard.isButtonDown(KeyEvent.VK_N)) {
                    ((Space) onscreen.getFirst()).AddPowerup(new Unarmed(hero,
                            70f, 70f));
                    timer.reset();
                }
                if (keyboard.isButtonDown(KeyEvent.VK_M)) {
                    ((Space) onscreen.getFirst()).AddPowerup(new Magnetism(
                            hero, 70f, 70f));
                    timer.reset();
                }
                if (keyboard.isButtonDown(KeyEvent.VK_COMMA)) {
                    ((Space) onscreen.getFirst()).AddPowerup(new Gravity(hero,
                            70f, 70f));
                    timer.reset();
                }
            }
            // 'K' to kill all the enemies on screen
            if (keyboard.isButtonDown(KeyEvent.VK_K)) {
                eManager.killAll();
                timer.reset();
            }
            if (keyboard.isButtonDown(KeyEvent.VK_R)) {
                hero.reloadLimitedAmmoWeapon();
            }
        }
        statusText();
    }

    private void statusText() {
        ghostText
                .setText("Ghost: "
                        + (int) Math.ceil(PowerUpManager.GhostTimer
                                .timeRemaining() / 1000));
        invincibilityText.setText("Invincibility: "
                + (int) Math.ceil(PowerUpManager.InvincibilityTimer
                        .timeRemaining() / 1000));
        overloadText
                .setText("Overload: "
                        + (int) Math.ceil(PowerUpManager.OverloadTimer
                                .timeRemaining() / 1000));
        unarmedText
                .setText("Unarmed: "
                        + (int) Math.ceil(PowerUpManager.UnarmedTimer
                                .timeRemaining() / 1000));
        magnetismText
                .setText("Magnetism: "
                        + (int) Math.ceil(PowerUpManager.MagnetismTimer
                                .timeRemaining() / 1000));
        gravityText
                .setText("Gravity: "
                        + (int) Math.ceil(PowerUpManager.GravityTimer
                                .timeRemaining() / 1000));
        enemySpeedText.setText("Enemy Speed is "
                + EnemyManager.enemySpeed
                + ": "
                + (int) Math.ceil(PowerUpManager.EnemySpeedTimer
                        .timeRemaining() / 1000));
        heroWeaponText.setText("Current Weapon: " + hero.currentWeapon);
        recentPowerUpText
                .setText("Most Recent Power Up: " + hero.newestPowerUp);
    }
}
