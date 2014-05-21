using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Weapon
    {
        
        protected static float bulletSpeed = 1.2f;
        protected static float bulletSize = 1f;
        protected Hero hero;
        protected Queue<XNACS1Circle> bullets;
        protected List<Enemy> enemies;
        protected List<Explosion> explosions;

        public Weapon(Hero hero) 
        {
            this.hero = hero;
            bullets = new Queue<XNACS1Circle>();
            explosions = new List<Explosion>();
        }

        ~Weapon()
        {
            foreach (XNACS1Circle b in bullets)
            {
                b.Visible = false;
                b.RemoveFromAutoDrawSet();
            }
        }

        // update bullets, create explosions
        public virtual void update()
        {
            foreach (XNACS1Circle b in bullets) {
                b.CenterX += bulletSpeed;
            }

            while (bullets.Count > 0 && (bullets.First().CenterX - bullets.First().Radius) > GameWorld.rightEdge)
            {
                bullets.Dequeue().RemoveFromAutoDrawSet();
            }
             
            foreach (XNACS1Circle b in bullets)
            {
                foreach (Enemy e in enemies)
                {
                    if (e.getPosition().Collided(b) && b.Visible)
                    {
                        e.gotShot(b.Color);
                        b.Visible = false;
                        explosions.Add(new Explosion(hero, e));
                    }
                    
                }
                
            }

            foreach (Explosion e in explosions)
            {
                e.update();
                e.interactEnemy(enemies);
            }

            explosions.RemoveAll(explosion => explosion.isDone()); 
            
        }

        // fire the weapon
        public virtual void fire()
        {
            XNACS1Circle bullet = new XNACS1Circle(hero.getPosition().Center, bulletSize);
            bullet.Color = hero.getColor();
            bullets.Enqueue(bullet);
        }

        // draw bullets
        public virtual void draw()
        {
            foreach (XNACS1Circle b in bullets)
            {
                b.TopOfAutoDrawSet();
            }
        }

        public void setEnemies(List<Enemy> enemies)
        {
            this.enemies = enemies;
        }
    }
}
