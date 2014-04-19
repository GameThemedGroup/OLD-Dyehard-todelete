using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Platform
    {
        public static float height = 1.2f;
        private Hero hero;
        private List<Enemy> enemies;
        private XNACS1Rectangle box;

        public Platform(int offset, Hero hero, List<Enemy> enemies, float leftEdge)
        {
            this.hero = hero;
            this.enemies = enemies;

            // set up platform
            float position = (Stargate.width * 0.5f) + leftEdge;
;
            float drawOffset = ((offset * 1f) / Stargate.PIPE_COUNT) * Game.topEdge();
            
            this.box = new XNACS1Rectangle(new Vector2(position, drawOffset), Stargate.width, height);
            this.box.Color = Color.SlateGray;
        }

        ~Platform()
        {
            box.RemoveFromAutoDrawSet();
        }

        public void move()
        {
            box.CenterX -= Environment.Speed;
        }

        public void draw()
        {
            box.TopOfAutoDrawSet();
        }

        public void interact()
        {
            // let the hero know if it is about to collide with the platform
            if (box.Collided(hero.getNextPosition())) {
                hero.addCollision(box);
            }

            foreach (Enemy e in enemies)
            {
                if (box.Collided(e.getNextPosition()))
                {
                    e.addCollision(box);
                }
            }
        }
    }
}
