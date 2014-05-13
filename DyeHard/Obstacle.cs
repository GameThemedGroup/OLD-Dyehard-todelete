using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;


namespace Dyehard
{
    class Obstacle
    {
        private Hero hero;
        private List<Enemy> enemies;
        private XNACS1Rectangle box;

        public Obstacle(Hero hero, List<Enemy> enemies, Vector2 center, float width, float height)
        {
            this.hero = hero;
            this.enemies = enemies;
            this.box = new XNACS1Rectangle(center, width, height);
            this.box.Color = Color.SlateGray;
        }

        ~Obstacle()
        {
            box.RemoveFromAutoDrawSet();
        }

        public void move()
        {
            box.CenterX -= GameWorld.Speed;
        }

        public void draw()
        {
            box.TopOfAutoDrawSet();
        }

        public void interact()
        {
            // kill the hero if its crushed
            XNACS1Rectangle heroPosition = hero.getPosition();
            if (box.Collided(heroPosition))
            {
                // make sure box is still completely on screen
                if (box.MinBound.X > GameWorld.leftEdge) {
                    // kill hero if squished between box and left edge of game
                    if (heroPosition.MinBound.X <= GameWorld.leftEdge)
                    {
                        if ((heroPosition.MinBound.Y < box.MaxBound.Y && heroPosition.MinBound.Y > box.MinBound.Y) ||
                            (heroPosition.MaxBound.Y < box.MaxBound.Y && heroPosition.MaxBound.Y > box.MinBound.Y))
                        {
                            hero.kill();
                        }
                    }
                }

            }

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
