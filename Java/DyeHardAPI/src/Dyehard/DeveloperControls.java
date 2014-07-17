package Dyehard;

import java.awt.event.KeyEvent;

import Dyehard.Player.Hero;
import Dyehard.Powerups.DyePack;
import Engine.KeyboardInput;

public class DeveloperControls {
    private KeyboardInput keyboard;
    private DyePack dye;
    private Hero hero;

    public DeveloperControls(KeyboardInput keyboard, Hero hero) {
        this.keyboard = keyboard;
        this.hero = hero;
    }

    public void update() {
        if (keyboard.isButtonDown(KeyEvent.VK_0)) {
            dye = new DyePack(hero, 0f, 480f, DyeHard.randomColor());
        }
    }
}
