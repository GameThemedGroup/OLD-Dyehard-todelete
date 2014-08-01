package dyehard.Background;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;

import dyehard.World.GameWorld;


import Engine.Rectangle;
import Engine.Vector2;

//TODO replace magic numbers
//TODO add comments
public class InfinityShip {

    private class ShipTile {
        private float width = 40;
        private float height = 25f;
        // force gap between ship tiles to show overlap
        private float gap = 1.5f;
        private Rectangle tile;
        private Rectangle window;

        public ShipTile(float leftEdge) {
            float Xpos = leftEdge + (width * 0.5f);
            float Ypos = 5f;// (Background.height / 2);
            Vector2 position = new Vector2(Xpos, Ypos);

            tile = new Rectangle();
            tile.velocity = new Vector2(-InfinityShip.Speed, 0f);
            tile.center = position;
            tile.size = new Vector2(width - gap, height);
            tile.color = Color.DARK_GRAY;

            window = new Rectangle();
            window.velocity = new Vector2(-InfinityShip.Speed, 0f);
            window.center = position;
            window.size = new Vector2(3, 3);
            window.color = Color.GRAY;
        }

        public void update() {
            tile.update();
            window.update();
        }

        public void setLeftEdgeAt(float leftEdge) {
            tile.center.setX(leftEdge + ((tile.size.getX() + gap) / 2f));
            window.center = tile.center;
        }

        public float RIGHT_EDGE() {
            return tile.center.getX() + (tile.size.getX() + gap) / 2f;
        }

        public boolean isOffScreen() {
            return RIGHT_EDGE() < GameWorld.LEFT_EDGE;
        }
    }

    public static float Speed = 0f;
    private Deque<ShipTile> displayedTiles;
    private Deque<ShipTile> upcomingTiles;

    public InfinityShip(float speed) {
        Speed = speed;
        displayedTiles = new ArrayDeque<ShipTile>();
        upcomingTiles = new ArrayDeque<ShipTile>();

        displayedTiles.add(new ShipTile(GameWorld.RIGHT_EDGE));

        upcomingTiles.add(new ShipTile(GameWorld.RIGHT_EDGE));
        upcomingTiles.add(new ShipTile(GameWorld.RIGHT_EDGE));
        upcomingTiles.add(new ShipTile(GameWorld.RIGHT_EDGE));
    }

    public void update() {
        for (ShipTile tile : displayedTiles) {
            tile.update();
        }

        // Save the tile to be reused at a later time
        if (displayedTiles.getFirst().isOffScreen()) {
            upcomingTiles.add(displayedTiles.pop());
        }

        if (upcomingTiles.size() == 0) {
            return;
        }

        ShipTile rightMostTile = displayedTiles.getLast();

        // Add an upcoming tile to the queue of displayed tiles
        if (rightMostTile.RIGHT_EDGE() <= GameWorld.RIGHT_EDGE) {
            ShipTile tile = upcomingTiles.pollFirst();
            tile.setLeftEdgeAt(displayedTiles.getLast().RIGHT_EDGE());
            displayedTiles.add(tile);
        }
    }
}
