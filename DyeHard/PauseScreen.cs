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
        private bool active;

        public PauseScreen()
        {
            this.active = false;
            Vector2 centerScreen = new Vector2(Game.rightEdge() / 2, Game.topEdge() / 2);
            this.box = new XNACS1Rectangle(centerScreen, Game.rightEdge() / 2, Game.topEdge() / 2);
            this.box.Color = Color.White;
            this.box.Label = "Paused\n\n\nPress 'Space' to resume...";
            this.box.Visible = false;

            this.boxBorder = new XNACS1Rectangle(box.Center, box.Width + 1f, box.Height + 1f);
            this.boxBorder.Color = Color.LightGray;
            this.boxBorder.Visible = this.box.Visible;
        }

        public void toggle()
        {
            active = !active;
            if (active)
            {
                boxBorder.Visible = true;
                boxBorder.TopOfAutoDrawSet();
                box.Visible = true;
                box.TopOfAutoDrawSet();
            }
            else
            {
                box.Visible = false;
                boxBorder.Visible = false;
            }
        }

        public bool isActive()
        {
            return active;
        }

    }
}
