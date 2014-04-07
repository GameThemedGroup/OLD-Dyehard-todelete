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
        bool alive;

        public Hero(string name)
        {
            this.alive = true;
            this.box = new XNACS1Rectangle(new Vector2(Game.rightEdge() / 3, Game.topEdge() / 2), 5f, 5f);
            this.box.Label = name;
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
            box.Center += (XNACS1Lib.XNACS1Base.GamePad.ThumbSticks.Right);
            box.TopOfAutoDrawSet();
            XNACS1Base.World.ClampAtWorldBound(box);
        }
    }
}
