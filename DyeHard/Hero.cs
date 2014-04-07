using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace DyeHard
{
    class Hero
    {
        private XNACS1Rectangle box;
        private XNACS1Rectangle boxBorder;
        bool alive;

        public Hero()
        {
            this.alive = true;
            this.box = new XNACS1Rectangle(new Vector2(Game.rightEdge() / 3, Game.topEdge() / 2), 5f, 5f);
            this.box.Label = "hero";

            this.boxBorder = new XNACS1Rectangle(this.box.Center, this.box.Width * 1.07f, this.box.Height * 1.07f);
            this.boxBorder.Color = Color.Black;
        }

        public XNACS1Rectangle getBox()
        {
            return box;
        }

        public bool isAlive()
        {
            return alive;
        }

        public void kill()
        {
            alive = false;
        }

        public void update()
        {
            box.Center += XNACS1Lib.XNACS1Base.GamePad.ThumbSticks.Right;
            XNACS1Base.World.ClampAtWorldBound(box);

            boxBorder.Center = box.Center;
            boxBorder.TopOfAutoDrawSet();
            box.TopOfAutoDrawSet();
        }
    }
}
