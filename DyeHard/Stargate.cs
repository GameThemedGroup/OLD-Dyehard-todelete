using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Stargate : EnvironmentElement
    {
        public const int PIPE_COUNT = 4;
        Gate[] gates;
        
        public Stargate(Hero hero, float leftEdge) : base()
        {
            List<Color> colors = Game.randomColorSet(PIPE_COUNT);
            this.gates = new Gate[PIPE_COUNT];
            for (int i = 0; i < this.gates.Length; i++)
            {
                this.gates[i] = new Gate(i, hero, leftEdge, colors[i]);
            }

        }

        public override void move()
        {
            foreach (Gate g in gates)
            {
                g.move();
            }
        }

        public override void draw()
        {
            foreach (Gate g in gates)
            {
                g.draw();
            }
        }

        public override void interact()
        {
            foreach (Gate g in gates)
            {
                g.interact();
            }
        }

        public override bool isOffScreen()
        {
            return gates[0].isOffScreen();
        }

        public override float rightEdge()
        {
            return gates[0].rightEdge();
        }
    }
}
