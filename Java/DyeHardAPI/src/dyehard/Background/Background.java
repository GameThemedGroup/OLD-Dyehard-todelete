package dyehard.Background;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.DyehardRectangle;
import dyehard.GameObject;
import dyehard.UpdateObject;
import dyehard.Enemies.Enemy;
import dyehard.Enemies.EnemyManager;
import dyehard.Util.ImageTint;
import dyehard.World.GameWorld;

public class Background extends UpdateObject {
    private List<Tile> ship;
    private List<BufferedImage> shipTextures;
    private String[] shipTexturePaths = {
            "Textures/Background/Dyehard_ship_tile_01.png",
            "Textures/Background/Dyehard_ship_tile_02.png",
            "Textures/Background/Dyehard_ship_tile_03.png",
            "Textures/Background/Dyehard_ship_tile_04.png", };

    private List<Tile> background;
    private List<BufferedImage> backgroundTextures;
    private String[] backgroundTexturePaths = {
            "Textures/Background/Dyehard_starfield_01.png",
            "Textures/Background/Dyehard_starfield_02.png", };

    private List<Tile> foreground;
    private List<BufferedImage> foregroundTextures;
    private String[] foregroundTexturePaths = {
            "Textures/Background/Dyehard_starfield_stars.png",
            "Textures/Background/Dyehard_starfield_stars.png", };

    private static Random RANDOM = new Random();

    public Background() {
        backgroundTextures = loadTextures(backgroundTexturePaths);
        background = createTiles(backgroundTextures, -0.00390625f); // 1/256

        foregroundTextures = loadTextures(foregroundTexturePaths);
        foreground = createTiles(foregroundTextures, -0.0078125f); // 1/128

        shipTextures = loadTextures(shipTexturePaths);
        ship = createTiles(shipTextures, -0.03125f); // 1/32
    }

    @Override
    public void update() {
        updateTileQueue(ship);
        updateTileQueue(background);
        updateTileQueue(foreground);

        for (Enemy e : EnemyManager.getEnemies()) {
            drawShadow(e);
        }

        drawShadow(GameWorld.sHero);
    }

    private void updateTileQueue(List<Tile> tiles) {
        for (Tile tile : tiles) {
            tile.texture = tile.baseTexture;
        }

        if (tiles.get(0).isOffScreen()) {
            Tile first = tiles.get(0);
            Tile last = tiles.get(tiles.size() - 1);
            first.setLeftEdgeAt(last.rightEdge() - 5f);

            tiles.remove(first);
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

    public void drawShadow(DyehardRectangle graphic) {
        Tile t = ship.get(0);

        DyehardRectangle shadow;

        shadow = new DyehardRectangle(graphic);
        shadow.center.sub(new Vector2(7f, 7f));
        shadow.size = graphic.size.clone();
        shadow.texture = ImageTint.tintedImage(graphic.texture, Color.BLACK,
                0.75f);
        shadow.destroy();

        // grab the top-left corner of the shadow
        Vector2 shadowPosition = new Vector2();
        shadowPosition.setX(shadow.center.getX() - shadow.size.getX() / 2f);
        shadowPosition.setY(shadow.center.getY() + shadow.size.getY() / 2f);

        Vector2 shadowPixelPosition = positionOnTile(t, shadowPosition);

        t.texture = ImageTint.generatedShadow(shadow.texture, t.texture,
                (int) shadowPixelPosition.getX(),
                (int) shadowPixelPosition.getY());

        float textureWidth = t.texture.getWidth();
        if (shadowPixelPosition.getX() + shadow.texture.getWidth() > textureWidth) {
            Tile secondTile = ship.get(1);
            shadowPixelPosition = positionOnTile(secondTile, shadowPosition);
            secondTile.texture = ImageTint.generatedShadow(shadow.texture,
                    secondTile.texture, (int) shadowPixelPosition.getX(),
                    (int) shadowPixelPosition.getY());
        }
    }

    protected Vector2 positionOnTile(Tile tile, Vector2 position) {
        Vector2 textureDim = new Vector2(tile.texture.getWidth(),
                tile.texture.getHeight());
        Vector2 scaledPosition = scaleToScreenSize(textureDim, position);

        float screenX = scaledPosition.getX();
        float screenY = scaledPosition.getY();

        Vector2 scaledTile = scaleToScreenSize(textureDim,
                new Vector2(tile.leftEdge(), GameWorld.TOP_EDGE));

        float tileLeft = scaledTile.getX();
        float tileTop = scaledTile.getY();

        return new Vector2(screenX - tileLeft, tileTop - screenY);
    }

    public static Vector2 scaleToScreenSize(Vector2 screen, Vector2 size) {
        float widthRatio = screen.getX() / Tile.width;
        float heightRatio = screen.getY() / Tile.height;

        Vector2 scaledSize = new Vector2();
        scaledSize.setX(size.getX() * widthRatio);
        scaledSize.setY(size.getY() * heightRatio);

        return scaledSize;
    }

    private List<Tile> createTiles(List<BufferedImage> textures, float speed) {
        // Collections.shuffle(textures);

        float randomStart = RANDOM.nextFloat() * Tile.width;
        List<Tile> tiles = new ArrayList<Tile>();
        for (int i = 0; i < textures.size(); ++i) {
            Tile tile = new Tile(i * Tile.width - randomStart, speed);
            tile.baseTexture = textures.get(i);
            tile.texture = textures.get(i);
            tiles.add(tile);
        }
        return tiles;
    }

    private class Tile extends GameObject {
        public static final float width = 106;
        public static final float height = 60f;
        public BufferedImage baseTexture;

        public Tile(float leftEdge, float speed) {
            float Xpos = leftEdge + (width * 0.5f);
            float Ypos = height / 2;
            Vector2 position = new Vector2(Xpos, Ypos);

            velocity = new Vector2(speed, 0f);
            center = position;

            // slightly stretch the graphic to cover the gap where the tiles
            // overlap
            size = new Vector2(width + 0.5f, height);
            color = Color.PINK;
            shouldTravel = true;
        }

        public void setLeftEdgeAt(float leftEdge) {
            center.setX(leftEdge + size.getX() / 2f);
        }

        public float leftEdge() {
            return center.getX() - size.getX() / 2f;
        }

        public float rightEdge() {
            return center.getX() + size.getX() / 2f;
        }

        public boolean isOffScreen() {
            return rightEdge() < GameWorld.LEFT_EDGE;
        }
    }
}