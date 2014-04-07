using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace DyeHard
{
    class Rainbow : BackgroundElement
    {
        public const int PIPE_COUNT = 3;
        DyePipe[] pipes;
        
        public Rainbow(Hero hero, float leftEdge) : base()
        {
            this.pipes = new DyePipe[PIPE_COUNT];
            for (int i = 0; i < this.pipes.Length; i++)
            {
                this.pipes[i] = new DyePipe(i, hero, leftEdge);
            }
        }

        public override void move()
        {
            foreach (DyePipe p in pipes)
            {
                p.move();
            }
        }

        public override void interact()
        {
            foreach (DyePipe p in pipes)
            {
                p.interact();
            }
        }

        public override bool isOffScreen()
        {
            return pipes[0].isOffScreen();
        }

        public override float rightEdge()
        {
            return pipes[0].rightEdge();
        }
    }
}
