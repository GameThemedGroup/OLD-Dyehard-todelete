import java.awt.event.KeyEvent;

import Engine.KeyboardInput;
import Engine.Vector2;

public class Player {

    private Hero hero;
    private KeyboardInput keyboard;

    public Player(Hero hero, KeyboardInput keyboard) {
        this.hero = hero;
        this.keyboard = keyboard;
    }

    public void update() {
        if (keyboard.isButtonDown(KeyEvent.VK_A)) {
            // The C# version uses joystick input to control the hero
            // I found 0.75 by printing out the value of the vector
            // being returned by the joy stick control
            hero.push(new Vector2(-0.75f, 0f));
        }

        if (keyboard.isButtonDown(KeyEvent.VK_D)) {
            hero.push(new Vector2(0.75f, 0f));
        }

        if (keyboard.isButtonDown(KeyEvent.VK_W)) {
            hero.push(new Vector2(0f, 0.75f));
        }

        if (keyboard.isButtonDown(KeyEvent.VK_S)) {
            hero.push(new Vector2(0f, -0.75f));
        }
    }
}
