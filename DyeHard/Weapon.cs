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
        private HaloEmitter p;
        private static float bulletSpeed = 1.2f;
        private static float bulletSize = 1f;
        private Hero hero;
        private Queue<XNACS1Circle> bullets;
        private List<Enemy> enemies;

        public Weapon(Hero hero)
        {
            this.hero = hero;
            bullets = new Queue<XNACS1Circle>();
            p = new HaloEmitter(new Vector2(), 50, 0.75f, Color.Transparent, 1);
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
                        p = new HaloEmitter(b.Center, 60, 0.5f, b.Color, 1.5f);
                        p.DrawHalo(50);
                        
                        b.Visible = false;
                    }
                    
                }
                
            }
            p.TopOfAutoDrawSet();
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
