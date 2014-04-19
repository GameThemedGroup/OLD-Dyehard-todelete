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
        private Hero hero;
        private List<Enemy> enemies;
        private XNACS1Rectangle box;

        public Debris(Hero hero, List<Enemy> enemies, float minX, float maxX)
        {
            this.hero = hero;
            this.enemies = enemies;

            float padding = hero.getPosition().Width * 2;

            float randomX = XNACS1Base.RandomFloat(minX + padding, maxX - padding);
            float randomY = XNACS1Base.RandomFloat(Game.bottomEdge() + padding, Game.topEdge() - padding);
            this.box = new XNACS1Rectangle(new Vector2(randomX, randomY), 10f, 10f);
        }

        ~Debris()
        {
            box.RemoveFromAutoDrawSet();
        }

        public void update()
        {
            this.box.CenterX -= Environment.Speed;
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
            if (box.Collided(hero.getNextPosition()))
            {
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
