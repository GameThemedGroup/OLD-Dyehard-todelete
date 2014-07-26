package Dyehard.Obstacles;

import java.awt.Color;
import java.util.ArrayList;

import BaseTypes.Enemy;
import Dyehard.DyeHard;
import Dyehard.Player.Hero;
import Dyehard.World.GameWorld;
import Dyehard.World.GameWorldRegion;
import Engine.Rectangle;
import Engine.Vector2;

public class Stargate extends GameWorldRegion {
    public static final int GATE_COUNT = 4;
    public static final float WIDTH = GameWorld.RIGHT_EDGE * 2.0f;
    private Gate[] gates;
    private Platform[] platforms;
    private Rectangle backdrop;

    public Stargate(Hero hero, ArrayList<Enemy> enemies, float leftEdge) {
        width = Stargate.WIDTH;
        position = leftEdge + width / 2f;
        speed = -GameWorld.Speed;

        ArrayList<Color> colors = DyeHard.randomColorSet(GATE_COUNT);

        gates = new Gate[GATE_COUNT];
        for (int i = 0; i < gates.length; i++) {
            gates[i] = new Gate(i, hero, enemies, leftEdge, colors.get(i),
                    width);
        }

        platforms = new Platform[GATE_COUNT + 1];
        for (int i = 0; i < platforms.length; i++) {
            boolean boundary = (i == 0) || (i == platforms.length - 1);
            platforms[i] = new Platform(i, leftEdge, boundary);
        }

        float height = GameWorld.TOP_EDGE;
        backdrop = new Rectangle();
        backdrop.center = new Vector2(position, height / 2);
        backdrop.size.set(width, height);
        backdrop.color = new Color(0, 0, 0, 130);
        backdrop.velocity = new Vector2(-speed, 0f);
    }

    @Override
    public void destroy() {
        backdrop.destroy();
        for (Gate g : gates) {
            g.destroy();
        }
    }

    public void draw() {
        backdrop.draw();
        for (Gate g : gates) {
            g.draw();
        }
    }

    @Override
    public void update() {
        position += speed;
        backdrop.update();
        for (Gate g : gates) {
            g.update();
        }
    }
}
