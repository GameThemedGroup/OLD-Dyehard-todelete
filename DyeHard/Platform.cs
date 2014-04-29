﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Platform
    {
        private const int SEGMENT_COUNT = 20;
        public static float height = 1.2f;
        private Hero hero;
        private List<Enemy> enemies;
        private List<Obstacle> obstacles;

        public Platform(int offset, Hero hero, List<Enemy> enemies, float leftEdge, bool continuous = false)
        {
            this.hero = hero;
            this.enemies = enemies;
            this.obstacles = new List<Obstacle>();
            // set up platform
;
            float Ypos = ((offset * 1f) / Stargate.GATE_COUNT) * Game.topEdge();


            if (continuous)
            {
                float Xpos = leftEdge + (Stargate.width / 2);
                Obstacle obstacle = new Obstacle(hero, enemies, new Vector2(Xpos, Ypos), Stargate.width, height);
                obstacles.Add(obstacle);
            }
            else
            {
                // randomly fill platform
                float width = Stargate.width / SEGMENT_COUNT;
                Obstacle obstacle;
                int consecutiveChance = 10;
                bool platform = true;
                for (int i = 0; i < SEGMENT_COUNT; i++)
                {

                    if (platform)
                    {
                        float Xpos = (width * 0.5f) + leftEdge + (i * width);
                        obstacle = new Obstacle(hero, enemies, new Vector2(Xpos, Ypos), width, height);
                        obstacles.Add(obstacle);
                    }

                    consecutiveChance -= 2;
                    if (XNACS1Base.RandomInt(consecutiveChance) == 0) {
                        platform = !platform;
                        consecutiveChance = 10;    
                    }   
                }
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
