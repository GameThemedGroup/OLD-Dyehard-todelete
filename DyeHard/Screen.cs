using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Screen
    {
        private XNACS1Rectangle window;

        public Screen(string label)
        {
            float width = XNACS1Base.World.WorldMax.X;
            float height = XNACS1Base.World.WorldMax.Y;
            Vector2 center = new Vector2(width/2, height/2);
            this.window = new XNACS1Rectangle(center, width, height);

            this.window.Texture = "StartScreen_Background";
            this.window.LabelColor = Color.White;
            this.window.Label = label;
        }

        ~Screen()
        {
            window.RemoveFromAutoDrawSet();
        }

        public void draw()
        {
            window.TopOfAutoDrawSet();
        }

    }
}
