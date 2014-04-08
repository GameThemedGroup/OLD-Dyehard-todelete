﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Rainbow : BackgroundElement
    {
        public const int PIPE_COUNT = 3;
        Pipe[] pipes;
        
        public Rainbow(Hero hero, float leftEdge) : base()
        {
            List<Color> colors = Game.randomColorSet(PIPE_COUNT);
            this.pipes = new Pipe[PIPE_COUNT];
            for (int i = 0; i < this.pipes.Length; i++)
            {
                this.pipes[i] = new Pipe(i, hero, leftEdge, colors[i]);
            }

        }

        public override void move()
        {
            foreach (Pipe p in pipes)
            {
                p.move();
            }
        }

        public override void interact()
        {
            foreach (Pipe p in pipes)
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
