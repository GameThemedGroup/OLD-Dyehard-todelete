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
        HaloEmitter p;
        Timer particleTimer;
        Explosion testEXP;

        private static float bulletSpeed = 1.2f;
        private static float bulletSize = 1f;
        private Hero hero;
        private Queue<XNACS1Circle> bullets;
        private List<Enemy> enemies;

        public Weapon(Hero hero)
        {
            this.hero = hero;
            bullets = new Queue<XNACS1Circle>();
            particleTimer = new Timer(3);
            p = new HaloEmitter(new Vector2(10,
                          10), 1, 1.0f, "Particle001", Color.White, 5);
            testEXP = new Explosion();

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

                testEXP.draw();
            
            

            foreach (XNACS1Circle b in bullets)
            {
                foreach (Enemy e in enemies)
                {
                    if (e.getPosition().Collided(b) && b.Visible)
                    {
                        e.gotShot(b.Color);
                        p = new HaloEmitter(new Vector2(e.getPosition().CenterX,
                          e.getPosition().CenterY), 100, 1.0f, "Particle001", e.getPosition().Color, 5);
                        p.DrawHalo(100);
                        
                        b.Visible = false;
                        particleTimer.reset();
                        testEXP.explod(e.getPosition().CenterX, e.getPosition().CenterY, e.getPosition().Color);
                        
                    }
                    
                }
                
            }
            
            particleTimer.update();
            //if (!particleTimer.isDone())
            //{
                p.TopOfAutoDrawSet();
              //  p.DrawHalo(10);
           // }

            
            
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
