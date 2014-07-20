package Dyehard;

import java.awt.event.KeyEvent;

import BaseTypes.DyePack;
import BaseTypes.PowerUp;
import Dyehard.Enemies.EnemyManager;
import Dyehard.Player.Hero;
import Dyehard.Powerups.Ghost;
import Dyehard.Powerups.Invincibility;
import Dyehard.Powerups.Overload;
import Dyehard.Powerups.SpeedUp;
import Dyehard.World.GameWorld;
import Engine.KeyboardInput;

public class DeveloperControls {
    private KeyboardInput keyboard;
    private Hero hero;
    private Space space;
    private EnemyManager eManager;

    public DeveloperControls(GameWorld world, Space space, Hero hero,
            KeyboardInput keyboard, EnemyManager eManager) {
        this.space = space;
        this.keyboard = keyboard;
        this.hero = hero;
        this.eManager = eManager;
    }

    public void update() {
        // 'D' to generate debris
        if (keyboard.isButtonDown(KeyEvent.VK_D)) {
            space.generateDebris();
        }
        // 'E' to add enemies
        if (keyboard.isButtonDown(KeyEvent.VK_E)) {
            // space.AddEnemy(new Enemy());
        }
        // 'P' to add random powerups
        if (keyboard.isButtonDown(KeyEvent.VK_P)) {
            space.AddPowerup(PowerUp.randomPowerUp(hero, 0, 100));
        }
        // 'K' to kill all the enemies on screen
        if (keyboard.isButtonDown(KeyEvent.VK_K)) {
            eManager.killAll();
        }
        if (keyboard.isButtonDown(KeyEvent.VK_0)) {
            space.AddDyepack(new DyePack(hero, 0f, 100f, DyeHard.randomColor()));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_9)) {
            space.AddPowerup(new Ghost(hero, 0f, 100f));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_8)) {
            space.AddPowerup(new Invincibility(hero, 0f, 100f));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_7)) {
            space.AddPowerup(new Overload(hero, 0f, 100f));
        }
        if (keyboard.isButtonDown(KeyEvent.VK_6)) {
            space.AddPowerup(new SpeedUp(hero, 0f, 100f));
        }
    }
}
