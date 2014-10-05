package dyehard.World;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Actor;
import dyehard.Collidable;
import dyehard.GameObject;
import dyehard.Enemies.Enemy;
import dyehard.Player.Hero;
import dyehard.Util.Colors;
import dyehard.Util.ImageTint;
import dyehard.Util.TextureTile;

public class Gate {
    private final StargatePath path;
    private final StargatePathFront pathFront;
    private final DeathGate deathGate;
    private final GatePreview preview;

    private static HashMap<Color, BufferedImage> dGates = new HashMap<Color, BufferedImage>();
    private static HashMap<Color, BufferedImage> gPathBack = new HashMap<Color, BufferedImage>();
    private static HashMap<Color, BufferedImage> gPathFront = new HashMap<Color, BufferedImage>();

    static {
        BufferedImage dGate = BaseCode.resources
                .loadImage("Textures/Background/Warp_start_Anim.png");
        TextureTile tile = new TextureTile();
        // Fill the hashmaps with tinted images for later use
        for (int i = 0; i < 6; i++) {
            Color temp = Colors.colorPicker(i);
            dGates.put(temp, ImageTint.tintedImage(dGate, temp, 0.25f));

            String colorString = "";
            switch (i) {
            case 0:
                colorString = "green";
                break;
            case 1:
                colorString = "red";
                break;
            case 2:
                colorString = "yellow";
                break;
            case 3:
                colorString = "teal";
                break;
            case 4:
                colorString = "pink";
                break;
            case 5:
                colorString = "blue";
                break;
            }

            gPathBack.put(temp, tile.setTiling(
                    BaseCode.resources.loadImage("Textures/Background/Warp_"
                            + colorString + "_back.png"), 10, false));
            gPathFront.put(temp, tile.setTiling(
                    BaseCode.resources.loadImage("Textures/Background/Warp_"
                            + colorString + "_front.png"), 10, false));
        }
    }

    public Gate(int offset, Hero hero, float leftEdge, Color color, float width) {
        // set up pipe
        float position = (width * 0.5f) + leftEdge;
        float drawHeight = GameWorld.TOP_EDGE / Stargate.GATE_COUNT;
        float drawOffset = drawHeight * (offset + 0.5f);

        path = new StargatePath();
        path.center = new Vector2(position, drawOffset);
        path.size.set(width, drawHeight - (Platform.height * 2));
        path.setPanning(true);
        path.setPanningSheet(gPathBack.get(color), 200, 140, 86, 1, false);
        path.dyeColor = color;
        path.velocity = new Vector2(-GameWorld.Speed, 0f);
        path.shouldTravel = true;
        // path.visible = false;

        // gate is slightly set back from left edge to avoid killing when
        // adjacent but not overlapping
        deathGate = new DeathGate();
        deathGate.center = new Vector2(leftEdge, path.center.getY());
        deathGate.size.set(0.9091f * path.size.getY(), path.size.getY());
        // Texture premade in static function
        deathGate.setUsingSpriteSheet(true);
        deathGate.setSpriteSheet(dGates.get(color), 200, 220, 24, 2);
        deathGate.dyeColor = color;
        deathGate.visible = true;
        deathGate.velocity = new Vector2(-GameWorld.Speed, 0f);
        deathGate.shouldTravel = true;

        hero.drawOnTop();

        pathFront = new StargatePathFront();
        pathFront.center = new Vector2(position, drawOffset);
        pathFront.size.set(width, drawHeight - (Platform.height * 2));
        pathFront.setPanning(true);
        pathFront
                .setPanningSheet(gPathFront.get(color), 200, 140, 86, 1, false);
        pathFront.velocity = new Vector2(-GameWorld.Speed, 0f);
        pathFront.shouldTravel = true;
        pathFront.reverse = true;

        preview = new GatePreview();
        preview.center = new Vector2(GameWorld.RIGHT_EDGE, drawOffset);
        preview.size.set(4f, 0f);
        preview.color = path.dyeColor;
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

    public class StargatePathFront extends Collidable {

        @Override
        public void handleCollision(Collidable other) {
            // don't do anything
        }

        @Override
        public void update() {
            // update() in collideable prematurely destroys gate, seperate
            // udpate function made.
            updateGate();
            removeFromAutoDrawSet();
            addToAutoDrawSet();

        }
    }
}
