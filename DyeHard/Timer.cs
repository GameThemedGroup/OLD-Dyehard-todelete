using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Timer
    {
        private int ticks;

        public Timer(int seconds)
        {
            ticks = seconds * XNACS1Base.World.TicksInASecond;
        }

        public void update()
        {
            ticks--;
        }

        public bool isDone()
        {
            return ticks <= 0;
        }
    }
}
