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
    public static float width = GameWorld.RIGHT_EDGE * 2.0f;
    public static final int GATE_COUNT = 4;
    private Gate[] gates;
    private Platform[] platforms;
    private Rectangle backdrop;

    public Stargate(Hero hero, ArrayList<Enemy> enemies, float leftEdge) {
        ArrayList<Color> colors = DyeHard.randomColorSet(GATE_COUNT);
        gates = new Gate[GATE_COUNT];
        for (int i = 0; i < gates.length; i++) {
            gates[i] = new Gate(i, hero, enemies, leftEdge, colors.get(i));
        }
        platforms = new Platform[GATE_COUNT + 1];
        for (int i = 0; i < platforms.length; i++) {
            boolean boundary = (i == 0) || (i == platforms.length - 1);
            platforms[i] = new Platform(i, leftEdge + width, boundary);
        }
        float height = GameWorld.TOP_EDGE;
        float Xposition = (width * 0.5f) + leftEdge;
        backdrop = new Rectangle();
        backdrop.center = new Vector2(Xposition, height / 2);
        backdrop.size.set(width, height);
        // This color is black with an alpha of 130
        backdrop.color = new Color(0, 0, 0, 130);
        visible = false;
        backdrop.velocity = new Vector2(-GameWorld.Speed, 0f);
        shouldTravel = true;
    }

    @Override
    public void destroy() {
        super.destroy();
        backdrop.removeFromAutoDrawSet();
        for (Gate g : gates) {
            g.destroy();
        }
    }

    @Override
    public void draw() {
        super.draw();
        backdrop.draw();
        for (Gate g : gates) {
            g.draw();
        }
    }

    @Override
    public void update() {
        super.update();
        backdrop.update();
        for (Gate g : gates) {
            g.update();
        }
    }

    @Override
    public boolean isOffScreen() {
        // Was backdrop.MaxBound.X
        return backdrop.center.getX() + (backdrop.size.getX() / 2) <= GameWorld.LEFT_EDGE;
    }

    @Override
    public float rightEdge() {
        // Was backdrop.MaxBound.X
        return backdrop.center.getX() + (backdrop.size.getX() / 2);
    }
}
