using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class PauseScreen
    {
        private XNACS1Rectangle boxBorder;
        private XNACS1Rectangle box;

        public PauseScreen()
        {
            Vector2 centerScreen = new Vector2(Game.rightEdge() / 2, Game.topEdge() / 2);
            this.box = new XNACS1Rectangle(centerScreen, Game.rightEdge() / 2, Game.topEdge() / 2);
            this.box.Color = Color.White;
            this.box.Label = "Game is paused.\n\n\nPress 'Space' to resume...";

            this.boxBorder = new XNACS1Rectangle(box.Center, box.Width + 1f, box.Height + 1f);
            this.boxBorder.Color = Color.LightGray;
        }

        ~PauseScreen()
        {
            boxBorder.RemoveFromAutoDrawSet();
            box.RemoveFromAutoDrawSet();
        }

        public void update()
        {
            boxBorder.Center += XNACS1Base.GamePad.ThumbSticks.Right;
            XNACS1Base.World.ClampAtWorldBound(boxBorder);
            box.Center = boxBorder.Center;

            boxBorder.TopOfAutoDrawSet();
            box.TopOfAutoDrawSet();
        }


    }
}
