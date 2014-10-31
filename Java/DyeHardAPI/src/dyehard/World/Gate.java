package dyehard.World;

import GameWorld;

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
import dyehard.Util.DyeHardSound;
import dyehard.Util.ImageTint;
import dyehard.Util.TextureTile;

public class Gate {
    private final StargatePath path;
    private final StargatePathFront pathFront;
    private final DeathGate deathGate;
    private final GatePreview preview;

    // private static BufferedImage gDoorBack;
    // private static BufferedImage gDoorFront;

    private static HashMap<Color, BufferedImage> dGates = new HashMap<Color, BufferedImage>();
    private static HashMap<Color, BufferedImage> gPathBack = new HashMap<Color, BufferedImage>();
    private static HashMap<Color, BufferedImage> gPathFront = new HashMap<Color, BufferedImage>();

    public static void setGatePathImages() {
        // gDoorFront = BaseCode.resources
        // .loadImage("Textures/Background/Entrance_front.png");
        // gDoorBack = BaseCode.resources
        // .loadImage("Textures/BackGround/Entrance_back.png");

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

            // cache the tiled portal images
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

        int pathTF = 10;
        int pathTick = 2;

        path = new StargatePath();
        path.center = new Vector2(position, drawOffset);
        path.size.set(width, drawHeight - (Platform.height * 2));
        path.setPanning(true);
        path.setPanningSheet(gPathBack.get(color), 200, 140, pathTF, pathTick,
                false);
        path.dyeColor = color;
        path.velocity = new Vector2(-GameWorld.Speed, 0f);
        path.shouldTravel = true;

        float gateHeight = path.size.getY() + 2f;

        // entrance back
        new GateDoor(new Vector2(leftEdge - 3.8f, path.center.getY()),
                new Vector2(0.852564f * gateHeight, gateHeight), false, false,
                color);
        // exit back
        new GateDoor(new Vector2(leftEdge + width - 3.8f, path.center.getY()),
                new Vector2(0.852564f * gateHeight, gateHeight), false, false,
                color);

        deathGate = new DeathGate();
        deathGate.center = new Vector2(leftEdge - 8f, path.center.getY());
        deathGate.size.set(0.9091f * (gateHeight + 2f), gateHeight + 2f);
        // Texture premade in static function
        deathGate.setUsingSpriteSheet(true);
        deathGate.setSpriteSheet(dGates.get(color), 200, 220, 24, 2);
        deathGate.dyeColor = color;
        deathGate.visible = true;
        deathGate.velocity = new Vector2(-GameWorld.Speed, 0f);
        deathGate.shouldTravel = true;

        // entrance front
        new GateDoor(new Vector2(leftEdge - 3.8f, path.center.getY()),
                new Vector2(0.852564f * gateHeight, gateHeight), true, true,
                color);
        // exit front
        new GateDoor(new Vector2(leftEdge + width - 3.8f, path.center.getY()),
                new Vector2(0.852564f * gateHeight, gateHeight), true, false,
                color);

        hero.drawOnTop();

        pathFront = new StargatePathFront();
        pathFront.center = new Vector2(position, drawOffset);
        pathFront.size.set(width, drawHeight - (Platform.height * 2));
        pathFront.setPanning(true);
        pathFront.setPanningSheet(gPathFront.get(color), 200, 140, pathTF,
                pathTick, false);
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

    public class DeathGate extends GameObject {
        public Color dyeColor;

        @Override
        public void update() {
            super.update();
            if (center.getX() < GameWorld.LEFT_EDGE - 10f) {
                destroy();
            }
        }
    }

    public class GateDoor extends Collidable {
        private final boolean death;
        public Color dyeColor;

        public GateDoor(Vector2 c, Vector2 s, boolean front, boolean death,
                Color color) {
            dyeColor = color;
            center = c;
            size.set(s);
            visible = true;
            if (front) {
                texture = BaseCode.resources
                        .loadImage("Textures/Background/Entrance_front.png");
                ;
                alwaysOnTop = true;
            } else {
                texture = BaseCode.resources
                        .loadImage("Textures/Background/Entrance_back.png");
                ;
            }
            this.death = death;
            velocity = new Vector2(-GameWorld.Speed, 0f);
            shouldTravel = true;
        }

        @Override
        public void handleCollision(Collidable other) {
            if (death) {
                if (other instanceof Actor) {
                    Actor target = (Actor) other;
                    if (target.center.getX() < center.getX()) {
                        if (target.getColor() != dyeColor) {
                            if (target instanceof Enemy) {
                                if (((Enemy) target).beenHit) {
                                    target.kill(this);
                                }
                            } else if (target instanceof Hero) {
                                target.kill(this);
                            }
                        }
                        if (target instanceof Hero) {
                            DyeHardSound.play(DyeHardSound.wormHoleSound);
                        }
                    }
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
