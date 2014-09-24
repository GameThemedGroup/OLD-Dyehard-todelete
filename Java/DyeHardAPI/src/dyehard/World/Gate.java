package dyehard.World;

import java.awt.Color;

import Engine.Vector2;
import dyehard.Actor;
import dyehard.Collidable;
import dyehard.GameObject;
import dyehard.Enemies.Enemy;
import dyehard.Player.Hero;

public class Gate {
    private final StargatePath path;
    private final DeathGate deathGate;
    private final GatePreview preview;

    public Gate(int offset, Hero hero, float leftEdge, Color color, float width) {
        // set up pipe
        float position = (width * 0.5f) + leftEdge;
        float drawHeight = GameWorld.TOP_EDGE / Stargate.GATE_COUNT;
        float drawOffset = drawHeight * (offset + 0.5f);

        path = new StargatePath();
        path.center = new Vector2(position, drawOffset);
        path.size.set(width, drawHeight - (Platform.height * 2));
        path.color = new Color(color.getRed(), color.getGreen(),
                color.getBlue(), 100);
        path.dyeColor = color;
        path.velocity = new Vector2(-GameWorld.Speed, 0f);
        path.shouldTravel = true;

        // gate is slightly set back from left edge to avoid killing when
        // adjacent but not overlapping
        deathGate = new DeathGate();
        deathGate.center = new Vector2(leftEdge, path.center.getY());
        deathGate.size.set(0.5f, path.size.getY());
        // This color is transparent
        deathGate.color = new Color(128, 0, 0, 0);
        deathGate.dyeColor = color;
        deathGate.visible = true;
        deathGate.velocity = new Vector2(-GameWorld.Speed, 0f);
        deathGate.shouldTravel = true;

        preview = new GatePreview();
        preview.center = new Vector2(GameWorld.RIGHT_EDGE, drawOffset);
        preview.size.set(4f, 0f);
        preview.color = path.color;
        preview.visible = true;
    }

    public class GatePreview extends GameObject {
        @Override
        public void update() {
            super.update();

            visible = (path.center.getX() - (path.size.getX() / 2)) > ((preview.center
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
        }
    }

    public class DeathGate extends Collidable {
        public Color dyeColor;

        @Override
        public void handleCollision(Collidable other) {
            if (other instanceof Actor) {
                Actor target = (Actor) other;
                if (target.getColor() != dyeColor) {
                    if (target instanceof Enemy) {
                        if (((Enemy) target).beenHit) {
                            target.kill(this);
                        }
                    }
                    target.kill(this);
                }
            }
        }

    }

    public class StargatePath extends Collidable {
        public Color dyeColor;

        @Override
        public void handleCollision(Collidable other) {
            if (other instanceof Actor) {
                Actor target = (Actor) other;
                target.setColor(dyeColor);
            }
        }

        @Override
        public void update() {
            // update() in collideable prematurely destroys gate, seperate
            // udpate function made.
            updateGate();
        }
    }
}
