using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace DyeHard
{
    class PauseScreen
    {
        private XNACS1Rectangle boxBorder;
        private XNACS1Rectangle box;

        public PauseScreen()
        {
            Vector2 centerScreen = new Vector2(Game.rightEdge() / 2, Game.topEdge() / 2);
            box = new XNACS1Rectangle(centerScreen, Game.rightEdge() / 2, Game.topEdge() / 2);
            box.Color = Color.White;
            box.Label = "Paused\n\n\nPress 'Space' to resume...";
            box.Visible = false;

            boxBorder = new XNACS1Rectangle(box.Center, box.Width + 1f, box.Height + 1f);
            boxBorder.Color = Color.LightGray;
        }

        public void show()
        {
            boxBorder.Visible = true;
            boxBorder.TopOfAutoDrawSet();
            box.Visible = true;
            box.TopOfAutoDrawSet();
        }

        public void hide()
        {
            box.Visible = false;
            boxBorder.Visible = false;
        }
    }
}
