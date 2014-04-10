using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class StartScreen
    {
        private XNACS1Rectangle box;

        public StartScreen()
        {
            float width = XNACS1Base.World.WorldMax.X;
            float height = XNACS1Base.World.WorldMax.Y;
            Vector2 center = new Vector2(width/2, height/2);
            this.box = new XNACS1Rectangle(center, width, height);

            this.box.Color = Color.DarkGray;
            this.box.LabelColor = Color.WhiteSmoke;
            this.box.Label = "DYEHARD\n\nPress 'A' to begin";
        }

        public void update()
        {
            
        }

    }
}
