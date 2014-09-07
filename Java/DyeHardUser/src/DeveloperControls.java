import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import Engine.Text;
import Engine.Vector2;
import dyehard.DyehardKeyboard;
import dyehard.UpdateManager;
import dyehard.UpdateManager.Updateable;
import dyehard.Collectibles.Ghost;
import dyehard.Collectibles.Gravity;
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
import dyehard.Weapons.LimitedAmmoWeapon;
import dyehard.World.GameWorld;

public class DeveloperControls implements Updateable {
    private Hero hero;

    Text weaponText;
    List<Text> powerupText;

    private HashMap<Integer, PowerUp> generationHotkeys;

    public DeveloperControls(Hero hero) {
        this.hero = hero;

        generationHotkeys = new HashMap<Integer, PowerUp>();
        // generationHotkeys.put(KeyEvent.VK_0, new DyePack());
        generationHotkeys.put(KeyEvent.VK_Z, new Ghost());
        generationHotkeys.put(KeyEvent.VK_X, new Invincibility());
        generationHotkeys.put(KeyEvent.VK_C, new Overload());
        generationHotkeys.put(KeyEvent.VK_V, new SpeedUp());
        generationHotkeys.put(KeyEvent.VK_B, new SlowDown());
        generationHotkeys.put(KeyEvent.VK_N, new Unarmed());
        generationHotkeys.put(KeyEvent.VK_M, new Magnetism());
        generationHotkeys.put(KeyEvent.VK_COMMA, new Gravity());

        weaponText = createTextAt(3f, 1f);
        powerupText = new ArrayList<Text>();

        UpdateManager.register(this);
    }

    @Override
    public void update() {
        weaponText.setText("Weapon: " + hero.currentWeapon.toString());

        updatePowerupText();

        if (DyehardKeyboard.isKeyTapped(KeyEvent.VK_E)) {
            EnemyManager.generateEnemy();
        }

        // 'K' to kill all the enemies on screen
        if (DyehardKeyboard.isKeyTapped(KeyEvent.VK_K)) {
            for (Enemy e : EnemyManager.getEnemies()) {
                e.kill(null);
            }
        }

        for (int hotkey : generationHotkeys.keySet()) {
            if (DyehardKeyboard.isKeyTapped(hotkey)) {
                generateCollectible(generationHotkeys.get(hotkey));
            }
        }

        if (DyehardKeyboard.isKeyTapped(KeyEvent.VK_R)) {
            if (hero.currentWeapon instanceof LimitedAmmoWeapon) {
                ((LimitedAmmoWeapon) hero.currentWeapon).reload();
            }
        }
    }

    private void updatePowerupText() {
        TreeSet<PowerUp> sortedPowerups = new TreeSet<PowerUp>(
                new Comparator<PowerUp>() {
                    @Override
                    public int compare(PowerUp o1, PowerUp o2) {
                        return (int) (o1.getRemainingTime() - o2
                                .getRemainingTime());
                    }
                });
        sortedPowerups.addAll(hero.powerups);

        if (sortedPowerups.size() > powerupText.size()) {
            for (int i = powerupText.size(); i < sortedPowerups.size(); ++i) {
                powerupText
                        .add(createTextAt(3f, GameWorld.TOP_EDGE - 3 - i * 2));
            }
        }

        int i = 0;
        for (PowerUp p : sortedPowerups) {
            powerupText.get(i).setText(p.toString());
            i++;
        }

        for (; i < powerupText.size(); ++i) {
            powerupText.get(i).setText("");
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
