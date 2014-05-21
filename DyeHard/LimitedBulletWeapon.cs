
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class LimitedBulletWeapon : Weapon
    {
        private int maxBullet = 10;
        private int currentBulletNum;
        private XNACS1Rectangle tempTracker;


        public LimitedBulletWeapon(Hero hero)
            : base(hero)
        {
            this.hero = hero;
            bullets = new Queue<XNACS1Circle>();
            explosions = new List<Explosion>();
            currentBulletNum = 10;
            tempTracker = new XNACS1Rectangle(new Vector2(30, 55), 4, 4);
        }

        ~LimitedBulletWeapon()
        {
            foreach (XNACS1Circle b in bullets)
            {
                b.Visible = false;
                b.RemoveFromAutoDrawSet();
            }

        }

        public override void recharge()
        {
            currentBulletNum = maxBullet;
        }

        public override void update()
        {
            if (KeyboardDevice.isKeyTapped(Microsoft.Xna.Framework.Input.Keys.F) &&  
                currentBulletNum > 0)
            {
                fire();
                currentBulletNum--;
            }

            if (currentBulletNum <=0)
            {
                tempTracker.Color = Color.Red;
            }


            if (currentBulletNum > 0)
            {
                tempTracker.Color = Color.Green;

            }

            tempTracker.Label = currentBulletNum.ToString();
            tempTracker.TopOfAutoDrawSet();

            foreach (XNACS1Circle b in bullets)
            {
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

    }
}
