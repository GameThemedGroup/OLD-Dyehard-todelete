package dyehard.World;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import Engine.Vector2;
import dyehard.GameObject;
import dyehard.Player.Hero;
import dyehard.Util.Colors;

public class Stargate extends GameWorldRegion {
    public static final int GATE_COUNT = 4;
    public static final float WIDTH = GameWorld.RIGHT_EDGE * 2.0f;
    private Gate[] gates;
    private Platform[] platforms;
    private GameObject backdrop;
    private static ArrayList<Color> userColors;

    public Hero hero;

    public Stargate(Hero hero) {
        this.hero = hero;
        width = Stargate.WIDTH;
        speed = -GameWorld.Speed;
    }

    static {
        userColors = new ArrayList<Color>();
    }

    @Override
    public void initialize(float leftEdge) {
        position = leftEdge + width / 2f;

        ArrayList<Color> colors = new ArrayList<Color>();
        colors.addAll(userColors);
        colors.addAll(Colors.randomColorSet(GATE_COUNT - userColors.size()));
        Collections.shuffle(colors);

        gates = new Gate[GATE_COUNT];
        for (int i = 0; i < gates.length; i++) {
            gates[i] = new Gate(i, hero, leftEdge, colors.get(i), width);
        }

        platforms = new Platform[GATE_COUNT + 1];
        for (int i = 0; i < platforms.length; i++) {
            boolean boundary = (i == 0) || (i == platforms.length - 1);
            platforms[i] = new Platform(i, leftEdge, boundary);
        }

        float height = GameWorld.TOP_EDGE;
        backdrop = new GameObject();
        backdrop.center = new Vector2(position, height / 2);
        backdrop.size.set(width, height);
        backdrop.color = new Color(0, 0, 0, 130);
        backdrop.velocity = new Vector2(-speed, 0f);
        backdrop.visible = true;
    }

    public static void addColor(Color c) {
        if (userColors.size() < GATE_COUNT) {
            userColors.add(c);
        } else {
            System.err.println("Cannot have more than " + GATE_COUNT
                    + " colors in a Stargate!");
        }
    }
}
