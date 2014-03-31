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
        public const float PIPE_COUNT = 4f;
        DyePipe[] pipes;
        
        public Rainbow(Hero hero) : base()
        {
            this.pipes = new DyePipe[(int) PIPE_COUNT];
            for (int i = 0; i < this.pipes.Length; i++)
            {
                this.pipes[i] = new DyePipe(i, hero);
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

        public override void centerOnScreen()
        {
            throw new NotImplementedException();
        }

        public override bool isOffScreen()
        {
            return pipes[0].isOffScreen();
        }

        public override bool rightEdgeIsOnScreen()
        {
            return pipes[0].rightEdgeIsOnScreen();
        }
    }
}
