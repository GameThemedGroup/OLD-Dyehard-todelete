import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Engine.KeyboardInput;
import Engine.Text;
import Engine.Vector2;
import dyehard.UpdateManager;
import dyehard.UpdateManager.Updateable;
import dyehard.Collectibles.Ghost;
import dyehard.Collectibles.Invincibility;
import dyehard.Collectibles.Magnetism;
import dyehard.Collectibles.Overload;
import dyehard.Collectibles.PowerUp;
import dyehard.Collectibles.SlowDown;
import dyehard.Collectibles.SpeedUp;
import dyehard.Collectibles.Unarmed;
import dyehard.Enemies.Enemy;
import dyehard.Enemies.EnemyManager;
import dyehard.Player.Hero;
import dyehard.World.GameWorld;

public class DeveloperControls implements Updateable {
    private KeyboardInput keyboard;
    private Hero hero;

    Text weaponText;
    List<Text> powerupText;

    private HashMap<Integer, PowerUp> generationHotkeys;

    public DeveloperControls(Hero hero, KeyboardInput keyboard) {
        this.hero = hero;
        this.keyboard = keyboard;

        generationHotkeys = new HashMap<Integer, PowerUp>();
        // generationHotkeys.put(KeyEvent.VK_0, new DyePack());
        generationHotkeys.put(KeyEvent.VK_Z, new Ghost());
        generationHotkeys.put(KeyEvent.VK_X, new Invincibility());
        generationHotkeys.put(KeyEvent.VK_C, new Overload());
        generationHotkeys.put(KeyEvent.VK_V, new SpeedUp());
        generationHotkeys.put(KeyEvent.VK_B, new SlowDown());
        generationHotkeys.put(KeyEvent.VK_N, new Unarmed());
        generationHotkeys.put(KeyEvent.VK_M, new Magnetism());
        // generationHotkeys.put(KeyEvent.VK_COMMA, new Gravity());

        weaponText = createTextAt(1f, 1f);
        powerupText = new ArrayList<Text>();
        for (int i = 0; i < 8; ++i) {
            powerupText.add(createTextAt(1f, GameWorld.TOP_EDGE - 3 - i * 2));
        }

        UpdateManager.register(this);
    }

    @Override
    public void update() {
        weaponText.setText("Weapon: " + hero.currentWeapon.toString());

        int i = 0;
        for (PowerUp p : hero.powerups) {
            powerupText.get(i).setText(p.toString());
            i++;
        }

        for (; i < powerupText.size(); ++i) {
            powerupText.get(i).setText("");
        }

        if (keyboard.isButtonDown(KeyEvent.VK_E)) {
            EnemyManager.generateEnemy();
        }

        // 'K' to kill all the enemies on screen
        if (keyboard.isButtonDown(KeyEvent.VK_K)) {
            for (Enemy e : EnemyManager.getEnemies()) {
                e.kill(null);
            }
        }

        for (int hotkey : generationHotkeys.keySet()) {
            if (keyboard.isButtonDown(hotkey)) {
                generateCollectible(generationHotkeys.get(hotkey));
            }
        }

        if (keyboard.isButtonDown(KeyEvent.VK_R)) {
            // hero.currentWeapon.reload()
        }
    }

    private Text createTextAt(float x, float y) {
        Text text = new Text("", x, y);
        text.setFrontColor(Color.white);
        text.setBackColor(Color.black);
        text.setFontSize(18);
        text.setFontName("Arial");
        return text;
    }

    private void generateCollectible(PowerUp powerUp) {
        Vector2 position = new Vector2(60f, 35f);
        Vector2 velocity = new Vector2(-GameWorld.Speed, 0f);

        PowerUp p = powerUp.clone();
        p.initialize(position, velocity);
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
