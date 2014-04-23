using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class ShipTile
    {
        private const float width = 28f;
        private const float height = 28f;
        private XNACS1Rectangle tile;
        private XNACS1Rectangle window;

        public ShipTile(float leftEdge)
        {
            float Xpos = leftEdge + (width * 0.5f);
            float Ypos = (Game.topEdge() / 2);
            Vector2 position = new Vector2(Xpos, Ypos);
            this.tile = new XNACS1Rectangle(position, width - 3f, height); // - value to force a little gap between tiles
            this.tile.Color = Color.DimGray;

            this.window = new XNACS1Rectangle(position, width / 10, height / 10);
            this.window.Color = Color.LightGray;
        }

        ~ShipTile()
        {
            tile.RemoveFromAutoDrawSet();
            window.RemoveFromAutoDrawSet();
        }

        public void update()
        {
            tile.CenterX -= InfinityShip.Speed;
            window.CenterX -= InfinityShip.Speed;
        }

        public float rightEdge()
        {
            return tile.MaxBound.X + 1.5f; // + value to correct for temporary gap
        }

        public bool isOffScreen()
        {
            return rightEdge() < Game.leftEdge();
        }

        public void draw()
        {
            tile.TopOfAutoDrawSet();
            window.TopOfAutoDrawSet();
        }
    }
}
