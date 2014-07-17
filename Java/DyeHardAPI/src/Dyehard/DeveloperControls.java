package Dyehard;

import java.awt.event.KeyEvent;

import Dyehard.Player.Hero;
import Dyehard.Powerups.DyePack;
import Dyehard.Powerups.Ghost;
import Dyehard.Powerups.Invincibility;
import Dyehard.Powerups.Overload;
import Dyehard.Powerups.PowerUp;
import Dyehard.Powerups.SpeedUp;
import Engine.KeyboardInput;

public class DeveloperControls {
    private KeyboardInput keyboard;
    private DyePack dye;
    private Hero hero;
    private PowerUp power;

    public DeveloperControls(KeyboardInput keyboard, Hero hero) {
        this.keyboard = keyboard;
        this.hero = hero;
    }

    public void update() {
        if (keyboard.isButtonDown(KeyEvent.VK_0)) {
            dye = new DyePack(hero, 0f, 800f, DyeHard.randomColor());
        }
        if (keyboard.isButtonDown(KeyEvent.VK_9)) {
            power = new Ghost(hero, 0f, 800f);
        }
        if (keyboard.isButtonDown(KeyEvent.VK_8)) {
            power = new Invincibility(hero, 0f, 800f);
        }
        if (keyboard.isButtonDown(KeyEvent.VK_7)) {
            power = new Overload(hero, 0f, 800f);
        }
        if (keyboard.isButtonDown(KeyEvent.VK_6)) {
            power = new SpeedUp(hero, 0f, 800f);
        }
    }
}
