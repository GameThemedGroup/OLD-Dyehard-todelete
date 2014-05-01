using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;


namespace Dyehard
{
    class Debris
    {
        private static float size = 6f;
        private Obstacle obstacle;

        public Debris(Hero hero, List<Enemy> enemies, float minX, float maxX)
        {
            float padding = size;

            float randomX = XNACS1Base.RandomFloat(minX + padding, maxX - padding);
            float randomY = XNACS1Base.RandomFloat(Game.bottomEdge() + padding, Game.topEdge() - padding);
            this.obstacle = new Obstacle(hero, enemies, new Vector2(randomX, randomY), size, size);
        }

        public void move()
        {
            obstacle.move();
        }

        public void draw()
        {
            obstacle.draw();
        }

        public void interact()
        {
            obstacle.interact();
        }   
    }
}
