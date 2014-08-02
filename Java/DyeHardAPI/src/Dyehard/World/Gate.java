package dyehard.World;

import java.awt.Color;
import java.util.ArrayList;

import Engine.Rectangle;
import Engine.Vector2;
import dyehard.Enemies.Enemy;
import dyehard.Player.Hero;

public class Gate extends Rectangle {
    private Color color;
    private Rectangle path;
    private Rectangle deathGate;
    private Rectangle preview;
    private Hero hero;
    private ArrayList<Enemy> enemies;

    public Gate(int offset, Hero hero, ArrayList<Enemy> enemies,
            float leftEdge, Color color, float width) {
        this.hero = hero;
        this.enemies = enemies;
        this.color = color;
        // set up pipe
        float position = (width * 0.5f) + leftEdge;
        float drawHeight = GameWorld.TOP_EDGE / Stargate.GATE_COUNT;
        float drawOffset = drawHeight * (offset + 0.5f);
        path = new Rectangle();
        path.center = new Vector2(position, drawOffset);
        path.size.set(width, drawHeight - (Platform.height * 2));
        path.color = new Color(color.getRed(), color.getGreen(),
                color.getBlue(), 100);
        path.velocity = new Vector2(-GameWorld.Speed, 0f);
        path.shouldTravel = true;
        // gate is slightly set back from left edge to avoid killing when
        // adjacent but not overlapping
        deathGate = new Rectangle();
        deathGate.center = new Vector2(leftEdge + 0.3f, path.center.getY());
        deathGate.size.set(0.5f, path.size.getY());
        // This color is transparent
        deathGate.color = new Color(128, 0, 0, 0);
        deathGate.visible = true;
        deathGate.velocity = new Vector2(-GameWorld.Speed, 0f);
        deathGate.shouldTravel = true;
        preview = new Rectangle();
        preview.center = new Vector2(GameWorld.RIGHT_EDGE, drawOffset);
        preview.size.set(4f, 0f);
        preview.color = path.color;
        preview.visible = false;
        visible = false;
    }

    @Override
    public void update() {
        super.update();
        path.update();
        deathGate.update();
        // Was path.LowerLeft.X and preview.LowerLeft.X
        preview.visible = (path.center.getX() - (path.size.getX() / 2)) > ((preview.center
                .getX() - (preview.size.getX() / 2)) + preview.size.getX())
        // Was path.LowerLeft.X
                && (GameWorld.RIGHT_EDGE + (Space.WIDTH * 0.7f)) > (path.center
                        .getX() - (path.size.getX() / 2));
        if (preview.visible) {
            preview.size
                    // Was path.LowerLeft.X and preview.LowerLeft.X
                    .setY(((path.size.getY() + (Platform.height * 2)) * (1 - (((path.center
                            .getX() - (path.size.getX() / 2)) - ((preview.center
                            .getX() - (preview.size.getX() / 2)) + preview.size
                            .getX())) / (Space.WIDTH)))));
        }
        // kill the hero at the death wall
        if (hero.getColor() != color && deathGate.collided(hero)) {
            hero.kill();
        }
        // kill any enemies at the death wall
        for (Enemy e : enemies) {
            if (e.getColor() != color && deathGate.collided(e)) {
                e.kill();
            }
        }
        // dye the hero
        if (contains(hero)) {
            hero.setColor(color);
        }
        // dye any enemies
        for (Enemy e : enemies) {
            if (contains(e)) {
                // Changes enemy color when it moves to a different gate
                e.gotShot(color);
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        path.removeFromAutoDrawSet();
        deathGate.removeFromAutoDrawSet();
        preview.removeFromAutoDrawSet();
    }

    @Override
    public void draw() {
        super.draw();
        preview.draw();
        path.draw();
        deathGate.draw();
    }

    private boolean contains(Rectangle other) {
        // Was path.MaxBound.Y
        float topEdge = path.center.getY() + (path.size.getY() / 2);
        // Was path.MinBound.Y
        float bottomEdge = path.center.getY() - (path.size.getY() / 2);
        if (other.center.getY() < topEdge && other.center.getY() >= bottomEdge) {
            // Was path.LowerLeft.X
            float leftEdge = path.center.getX() - (path.size.getX() / 2);
            float rightEdge = leftEdge + path.size.getX();
            return other.center.getX() < rightEdge
                    && other.center.getX() > leftEdge;
        }
        return false;
    }
}
