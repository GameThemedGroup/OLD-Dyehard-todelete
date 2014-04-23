using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

// A starfield is a collection of stars moving to the left at a given speed.
// Layers of starfields can be used to create a parallax effect.

namespace Dyehard
{
    class InfinityShip
    {
        public static float Speed = 0f;
        private Queue<ShipTile> shipTiles;
        private float speed;
        private float size;

        public InfinityShip(float speed)
        {
            Speed = speed;
            this.shipTiles = new Queue<ShipTile>();

            // pre-fill game
            shipTiles.Enqueue(new ShipTile(Game.leftEdge()));
            while (shipTiles.Last().rightEdge() < Game.rightEdge())
            {
                shipTiles.Enqueue(new ShipTile(shipTiles.Last().rightEdge()));
            }
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
                shipTiles.Dequeue();
            }

            if (shipTiles.Last().rightEdge() <= Game.rightEdge())
            {
                shipTiles.Enqueue(
                    new ShipTile(shipTiles.Last().rightEdge())
                );
            }

            foreach (ShipTile tile in shipTiles)
            {
                tile.draw();
            }
        }
    }
}
