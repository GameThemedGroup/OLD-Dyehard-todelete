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
        public static float width = Game.rightEdge() * 1.25f;
        public const int PIPE_COUNT = 4;
        Gate[] gates;
        Platform[] platforms;
        
        public Stargate(Hero hero, List<Enemy> enemies, float leftEdge) : base()
        {
            List<Color> colors = Game.randomColorSet(PIPE_COUNT);
            this.gates = new Gate[PIPE_COUNT];
            for (int i = 0; i < this.gates.Length; i++)
            {
                this.gates[i] = new Gate(i, hero, enemies, leftEdge, colors[i]);
            }

            this.platforms = new Platform[PIPE_COUNT + 1];
            for (int i = 0; i < this.platforms.Length; i++) {
                this.platforms[i]  = new Platform(i, hero, enemies, leftEdge);
            }
        }

        public override void move()
        {
            foreach (Gate g in gates)
            {
                g.move();
            }

            foreach (Platform p in platforms) {
                p.move();
            }
        }

        public override void draw()
        {
            foreach (Gate g in gates)
            {
                g.draw();
            }

            foreach (Platform p in platforms) {
                p.draw();
            }
        }

        public override void interact()
        {
            foreach (Gate g in gates)
            {
                g.interact();
            }

            foreach (Platform p in platforms) {
                p.interact();
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
