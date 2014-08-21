package dyehard;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import Engine.KeyboardInput;
import Engine.Text;
import Engine.Vector2;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.Ghost;
import dyehard.Collectibles.Gravity;
import dyehard.Collectibles.Invincibility;
import dyehard.Collectibles.Magnetism;
import dyehard.Collectibles.Overload;
import dyehard.Collectibles.PowerUp;
import dyehard.Collectibles.SlowDown;
import dyehard.Collectibles.SpeedUp;
import dyehard.Collectibles.Unarmed;
import dyehard.Enemies.BrainEnemy;
import dyehard.Enemies.Enemy;
import dyehard.Enemies.RedBeamEnemy;
import dyehard.Enemies.SpiderEnemy;
import dyehard.Player.Hero;
import dyehard.Util.Colors;
import dyehard.Weapons.LimitedAmmoWeapon;
import dyehard.World.GameWorld;

public class DeveloperControls extends UpdateObject {
    private KeyboardInput keyboard;
    private Hero hero;

    private Text weaponText;
    private List<Text> powerupText;

    private HashMap<Integer, Integer> generationHotkeys;

    private static Random RANDOM = new Random();

    public DeveloperControls(GameWorld world, Hero hero, KeyboardInput keyboard) {
        this.hero = hero;
        this.keyboard = keyboard;

        generationHotkeys = new HashMap<Integer, Integer>();
        generationHotkeys.put(KeyEvent.VK_0, 0);
        generationHotkeys.put(KeyEvent.VK_Z, 1);
        generationHotkeys.put(KeyEvent.VK_X, 2);
        generationHotkeys.put(KeyEvent.VK_C, 3);
        generationHotkeys.put(KeyEvent.VK_V, 4);
        generationHotkeys.put(KeyEvent.VK_B, 5);
        generationHotkeys.put(KeyEvent.VK_N, 6);
        generationHotkeys.put(KeyEvent.VK_M, 7);
        generationHotkeys.put(KeyEvent.VK_COMMA, 8);

        weaponText = createTextAt(3f, 1f);
        powerupText = new ArrayList<Text>();
    }

    @Override
    public void update() {
        weaponText.setText("Weapon: " + hero.currentWeapon.toString());

        updatePowerupText();

        if (keyboard.isButtonDown(KeyEvent.VK_E)) {
            generateEnemy();
        }

        // 'K' to kill all the enemies on screen
        if (keyboard.isButtonDown(KeyEvent.VK_K)) {
            for (Enemy e : GameWorld.getEnemies()) {
                e.destroy();
            }
        }

        for (int hotkey : generationHotkeys.keySet()) {
            if (keyboard.isButtonDown(hotkey)) {
                generateCollectible(generationHotkeys.get(hotkey));
            }
        }

        if (keyboard.isButtonDown(KeyEvent.VK_R)) {
            if (hero.currentWeapon instanceof LimitedAmmoWeapon) {
                ((LimitedAmmoWeapon) hero.currentWeapon).recharge();
            }
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

        // create additional text boxes if there are not enough to display all
        // of the powerups
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

    private void generateCollectible(int powerUp) {
        float minX = 70f;
        float maxX = 70f;

        switch (powerUp) {
        case 1:
            new Ghost(hero, minX, maxX);
            break;
        case 2:
            new Invincibility(hero, minX, maxX);
            break;
        case 3:
            new Overload(hero, minX, maxX);
            break;
        case 4:
            new SpeedUp(hero, GameWorld.getEnemies(), minX, maxX);
            break;
        case 5:
            new SlowDown(hero, GameWorld.getEnemies(), minX, maxX);
            break;
        case 6:
            new Unarmed(hero, minX, maxX);
            break;
        case 7:
            new Magnetism(hero, minX, maxX);
            break;
        case 8:
            new Gravity(hero, minX, maxX);
            break;
        default:
            new DyePack(hero, minX, maxX, Colors.randomColor());
        }
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
