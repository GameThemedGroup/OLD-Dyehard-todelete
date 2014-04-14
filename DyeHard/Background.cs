using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Background
    {
        private XNACS1Rectangle world;

        private Starfield foreground;
        private Starfield background;

        public Background()
        {
            float width = XNACS1Base.World.WorldMax.X;
            float height = XNACS1Base.World.WorldMax.Y;
            Vector2 center = new Vector2(width/2, height/2);
            this.world = new XNACS1Rectangle(center, width, height);

            this.world.Color = Color.Black;

            this.foreground = new Starfield(0.08f, 0.1f, 0.8f);
            this.background = new Starfield(0.04f, 0.05f, 0.2f);
        }

        public void update() {
            world.TopOfAutoDrawSet();
            background.update();
            foreground.update();
        }
    }
}
