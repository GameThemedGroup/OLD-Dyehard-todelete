package dyehard.World;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
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

public class Gate {
    private final StargatePath path;
    private final DeathGate deathGate;
    private final GatePreview preview;

    private static HashMap<Color, BufferedImage> dGates = new HashMap<Color, BufferedImage>();
    private static HashMap<Color, BufferedImage> gWaves = new HashMap<Color, BufferedImage>();
    private static HashMap<Color, BufferedImage> gPaths = new HashMap<Color, BufferedImage>();
    private static HashMap<Color, BufferedImage> gEdges = new HashMap<Color, BufferedImage>();

    static {
        BufferedImage dGate = BaseCode.resources
                .loadImage("Textures/Background/Warp_start_Anim.png");
        BufferedImage gWave = BaseCode.resources
                .loadImage("Textures/Background/Warp_Path_wave.png");
        BufferedImage gPath = BaseCode.resources
                .loadImage("Textures/Background/Warp_Path.png");
        BufferedImage gEdge = BaseCode.resources
                .loadImage("Textures/Background/Warp_wave_edge.png");
        // Fill the hashmaps with tinted images for later use
        for (int i = 0; i < 6; i++) {
            Color temp = Colors.colorPicker(i);

            dGates.put(temp, ImageTint.tintedImage(dGate, temp, 1f));
            gWaves.put(temp, ImageTint.tintedImage(gWave, temp, 1f));

            BufferedImage img = ImageTint.tintedImage(gPath, temp, 1f);
            // BufferedImage img2 = ImageTint.tintedImage(gWave, temp, 1f);

            Graphics2D g2 = img.createGraphics();
            g2.drawImage(img, 0, 0, null);
            // g2.drawImage(img2, 0, 0, null);
            // g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
            // RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            // g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            // RenderingHints.VALUE_ANTIALIAS_ON);
            // g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
            // RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            // g2.setRenderingHint(RenderingHints.KEY_DITHERING,
            // RenderingHints.VALUE_DITHER_ENABLE);
            // g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
            // RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            // g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            // RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            // g2.setRenderingHint(RenderingHints.KEY_RENDERING,
            // RenderingHints.VALUE_RENDER_QUALITY);
            // g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
            // RenderingHints.VALUE_STROKE_PURE);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN,
                    0.25f));
            g2.setColor(temp);

            g2.fillRect(0, 0, 512, 120);
            g2.dispose();
            gPaths.put(temp, img);
            gEdges.put(temp, ImageTint.tintedImage(gEdge, temp, 1f));
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
        path.setPanningSheet(gPaths.get(color), 512, 120, 32, 2, false);
        path.dyeColor = color;
        path.velocity = new Vector2(-GameWorld.Speed, 0f);
        path.shouldTravel = true;

        // gate is slightly set back from left edge to avoid killing when
        // adjacent but not overlapping
        deathGate = new DeathGate();
        deathGate.center = new Vector2(leftEdge, path.center.getY());
        deathGate.size.set(0.46875f * path.size.getY(), path.size.getY());
        // Texture premade in static function
        deathGate.setUsingSpriteSheet(true);
        deathGate.setSpriteSheet(dGates.get(color), 60, 128, 24, 2);
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
