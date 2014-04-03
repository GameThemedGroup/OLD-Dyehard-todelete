﻿using System;
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
            this.box = new XNACS1Rectangle(new Vector2(2f, 1f), 1, 1);
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
            box.TopOfAutoDrawSet();
            box.Center += (XNACS1Lib.XNACS1Base.GamePad.ThumbSticks.Right / 3f);
            XNACS1Base.World.ClampAtWorldBound(box);
        }
    }
}
