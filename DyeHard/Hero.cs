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
        XNACS1Rectangle box;
        XNACS1Particle p;

        bool alive;

        public Hero(string name)
        {
            this.alive = true;
            this.box = new XNACS1Rectangle(new Vector2(2f, 2f), 1, 1);
            this.box.Label = name;
        }

        public XNACS1Rectangle getBox()
        {
            return box;
        }

        public string getName()
        {
            return box.Label;
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
            this.box.TopOfAutoDrawSet();
            box.RotateAngle = (box.RotateAngle + 0.5f) % 360;
            box.Center += (XNACS1Lib.XNACS1Base.GamePad.ThumbSticks.Right / 3f);
            XNACS1Base.World.ClampAtWorldBound(box);
        }
    }
}
