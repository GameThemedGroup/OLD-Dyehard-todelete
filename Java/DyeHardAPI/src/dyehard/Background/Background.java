package dyehard.Background;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.GameObject;
import dyehard.UpdateObject;
import dyehard.World.GameWorld;

public class Background extends UpdateObject {
    private Deque<Tile> ship;
    private List<BufferedImage> shipTextures;
    private String[] shipTexturePaths = {
            "Textures/background/Dyehard_ship_tile_01.png",
            "Textures/background/Dyehard_ship_tile_02.png",
            "Textures/background/Dyehard_ship_tile_03.png",
            "Textures/background/Dyehard_ship_tile_04.png", };

    private Deque<Tile> background;
    private List<BufferedImage> backgroundTextures;
    private String[] backgroundTexturePaths = {
            "Textures/background/Dyehard_starfield_01.png",
            "Textures/background/Dyehard_starfield_02.png", };

    public Background() {
        backgroundTextures = loadTextures(backgroundTexturePaths);
        background = createTiles(backgroundTextures, -0.04f);

        shipTextures = loadTextures(shipTexturePaths);
        ship = createTiles(shipTextures, -0.06f);

        // foreground = new Starfield(.8f, .2f, 30f);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void update() {
        updateTileQueue(ship);
        updateTileQueue(background);
    }

    private void updateTileQueue(Deque<Tile> tiles) {
        if (tiles.peek().isOffScreen()) {
            Tile first = tiles.poll();
            Tile last = tiles.peekLast();
            first.setLeftEdgeAt(last.rightEdge() - 5f);

            tiles.add(first);
        }
    }

    private List<BufferedImage> loadTextures(String[] paths) {
        List<BufferedImage> textures = new ArrayList<BufferedImage>();
        for (String path : paths) {
            BufferedImage texture = BaseCode.resources.loadImage(path);
            textures.add(texture);
        }

        return textures;
    }

    private Deque<Tile> createTiles(List<BufferedImage> textures, float speed) {
        Collections.shuffle(textures);

        Deque<Tile> tiles = new ArrayDeque<Tile>();
        for (int i = 0; i < textures.size(); ++i) {
            Tile tile = new Tile(i * Tile.width - 5f, speed);
            tile.texture = textures.get(i);
            tiles.add(tile);
        }
        return tiles;
    }

    private class Tile extends GameObject {
        public static final float width = 106;
        public static final float height = 60f;

        public Tile(float leftEdge, float speed) {
            float Xpos = leftEdge + (width * 0.5f);
            float Ypos = height / 2;
            Vector2 position = new Vector2(Xpos, Ypos);

            velocity = new Vector2(speed, 0f);
            center = position;
            size = new Vector2(width, height);
            color = Color.PINK;
            shouldTravel = true;
        }

        public void setLeftEdgeAt(float leftEdge) {
            center.setX(leftEdge + size.getX() / 2f);
        }

        public float rightEdge() {
            return center.getX() + size.getX() / 2f;
        }

        public boolean isOffScreen() {
            return rightEdge() < GameWorld.LEFT_EDGE;
        }
    }
}
