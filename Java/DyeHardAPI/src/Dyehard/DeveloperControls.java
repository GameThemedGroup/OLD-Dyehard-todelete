package dyehard;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import Engine.KeyboardInput;
import Engine.Vector2;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.Ghost;
import dyehard.Collectibles.Invincibility;
import dyehard.Collectibles.Magnetism;
import dyehard.Collectibles.Overload;
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

    public DeveloperControls(GameWorld world, Space space, Hero hero,
            KeyboardInput keyboard, EnemyManager eManager,
            LinkedList<GameWorldRegion> onscreen) {
        this.keyboard = keyboard;
        this.hero = hero;
        this.eManager = eManager;
        this.onscreen = onscreen;
        timer = new Timer(500f);
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
            }
            // 'K' to kill all the enemies on screen
            if (keyboard.isButtonDown(KeyEvent.VK_K)) {
                eManager.killAll();
                timer.reset();
            }
        }
    }
}
