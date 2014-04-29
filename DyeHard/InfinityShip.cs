using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class InfinityShip
    {
        public static float Speed = 0f;
        private Queue<ShipTile> shipTiles;
        private Queue<ShipTile> pool;

        public InfinityShip(float speed)
        {
            Speed = speed;
            this.shipTiles = new Queue<ShipTile>();
            this.pool = new Queue<ShipTile>();

            shipTiles.Enqueue(new ShipTile(Game.rightEdge()));
        }

        public void update()
        {
            // move stars, and redraw
            foreach (ShipTile tile in shipTiles)
            {
                tile.update();
            }

            if (shipTiles.First().isOffScreen())
            {
                pool.Enqueue(shipTiles.Dequeue());
            }

            if (shipTiles.Last().rightEdge() <= Game.rightEdge())
            {
                if (pool.Count > 0)
                {
                    // draw a tile from the pool
                    ShipTile tile = pool.Dequeue();
                    tile.setLeftEdgeAt(shipTiles.Last().rightEdge());
                    shipTiles.Enqueue(tile);
                }
                else
                {
                    // pool is empty, create new ship tile
                    shipTiles.Enqueue(
                        new ShipTile(shipTiles.Last().rightEdge())
                    );
                }
            }

            foreach (ShipTile tile in shipTiles)
            {
                tile.draw();
            }
        }
    }
}
