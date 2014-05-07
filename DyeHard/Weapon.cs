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
        
        private static float bulletSpeed = 1.2f;
        private static float bulletSize = 1f;
        private Hero hero;
        private Queue<XNACS1Circle> bullets;
        private List<Enemy> enemies;
        private List<Explosion> explosions;

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
                b.RemoveFromAutoDrawSet();
            }

        }

        public void update()
        {
            if (KeyboardDevice.isKeyTapped(Microsoft.Xna.Framework.Input.Keys.F))
            {
                fire();
            }

            foreach (XNACS1Circle b in bullets) {
                b.CenterX += bulletSpeed;
            }

            while (bullets.Count > 0 && (bullets.First().CenterX - bullets.First().Radius) > Game.rightEdge())
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
            explosions.RemoveAll(explosion => explosion.deletedOrNot()); 
            
        }

        // fire the weapon
        private void fire()
        {
            XNACS1Circle bullet = new XNACS1Circle(hero.getPosition().Center, bulletSize);
            bullet.Color = hero.getColor();
            bullets.Enqueue(bullet);
        }

        public void draw()
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
