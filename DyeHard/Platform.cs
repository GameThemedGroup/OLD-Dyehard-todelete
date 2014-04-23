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
        private const int SEGMENT_COUNT = 3;
        public static float height = 1.2f;
        private Hero hero;
        private List<Enemy> enemies;
        private List<Obstacle> obstacles;

        public Platform(int offset, Hero hero, List<Enemy> enemies, float leftEdge)
        {
            this.hero = hero;
            this.enemies = enemies;
            this.obstacles = new List<Obstacle>();
            // set up platform
;
            float Ypos = ((offset * 1f) / Stargate.GATE_COUNT) * Game.topEdge();
            
            float width = Stargate.width / ((SEGMENT_COUNT * 2) - 1);

            for (int i = 0; i < (SEGMENT_COUNT * 2); i += 2)
            {
                float Xpos = (width * 0.5f) + leftEdge + (i * width);
                Obstacle obstacle = new Obstacle(hero, enemies, new Vector2(Xpos, Ypos), width, height);
                obstacles.Add(obstacle);
            }
        }

        public void move()
        {
            foreach (Obstacle obstacle in obstacles)
            {
                obstacle.move();
            }
        }

        public void draw()
        {
            foreach (Obstacle obstacle in obstacles)
            {
                obstacle.draw();
            }
        }

        public void interact()
        {
            foreach (Obstacle obstacle in obstacles)
            {
                obstacle.interact();
            }
        }
    }
}
