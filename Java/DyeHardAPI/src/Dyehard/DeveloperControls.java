package dyehard;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import Engine.KeyboardInput;
import Engine.Vector2;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.Ghost;
import dyehard.Collectibles.Invincibility;
import dyehard.Collectibles.Overload;
import dyehard.Collectibles.PowerUp;
import dyehard.Collectibles.SpeedUp;
import dyehard.Enemies.BrainEnemy;
import dyehard.Enemies.EnemyManager;
import dyehard.Enemies.RedBeamEnemy;
import dyehard.Enemies.SpiderEnemy;
import dyehard.Player.Hero;
import dyehard.Util.Colors;
import dyehard.World.GameWorld;
import dyehard.World.GameWorldRegion;
import dyehard.World.Space;

public class DeveloperControls {
    private KeyboardInput keyboard;
    private Hero hero;
    private EnemyManager eManager;
    private LinkedList<GameWorldRegion> onscreen;

    public DeveloperControls(GameWorld world, Space space, Hero hero,
            KeyboardInput keyboard, EnemyManager eManager,
            LinkedList<GameWorldRegion> onscreen) {
        this.keyboard = keyboard;
        this.hero = hero;
        this.eManager = eManager;
        this.onscreen = onscreen;
    }

    public void update() {
        // 'D' to generate debris
        // if (keyboard.isButtonDown(KeyEvent.VK_D)) {
        // space.generateDebris();
        // }
        // 'E' to add random enemies
        if (keyboard.isButtonDown(KeyEvent.VK_E)) {
            Random rand = new Random();
            float randomY = (50f - 5f - 0f + 5f) * rand.nextFloat() + 0f + 5f;
            Vector2 position = new Vector2(83.3f + 5, randomY);
            switch (rand.nextInt(3)) {
            case 1:
                ((Space) onscreen.getFirst()).AddEnemy(new BrainEnemy(position,
                        7.5f, hero));
                break;
            case 2:
                ((Space) onscreen.getFirst()).AddEnemy(new RedBeamEnemy(
                        position, 7.5f, hero));
                break;
            default:
                ((Space) onscreen.getFirst()).AddEnemy(new SpiderEnemy(
                        position, 7.5f, hero));
                break;
            }
        }
        // 'P' to add random powerups
        if (keyboard.isButtonDown(KeyEvent.VK_P)) {
            ((Space) onscreen.getFirst()).AddPowerup(PowerUp.randomPowerUp(
                    hero, 0, 100));
        }
        // 'K' to kill all the enemies on screen
        if (keyboard.isButtonDown(KeyEvent.VK_K)) {
            eManager.killAll();
        }
        if (keyboard.isButtonDown(KeyEvent.VK_0)) {
            ((Space) onscreen.getFirst()).AddDyepack(new DyePack(hero, 0f,
                    100f, Colors.randomColor()));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_9)) {
            ((Space) onscreen.getFirst()).AddPowerup(new Ghost(hero, 0f, 100f));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_8)) {
            ((Space) onscreen.getFirst()).AddPowerup(new Invincibility(hero,
                    0f, 100f));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_7)) {
            ((Space) onscreen.getFirst()).AddPowerup(new Overload(hero, 0f,
                    100f));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_6)) {
            ((Space) onscreen.getFirst())
                    .AddPowerup(new SpeedUp(hero, 0f, 100f));
        }
    }
}
