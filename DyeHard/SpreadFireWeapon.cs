
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class SpreadFireWeapon : Weapon
    {
        private XNACS1Rectangle info;
        private Queue<XNACS1Circle> bulletsUp;
        private Queue<XNACS1Circle> bulletsDown;

        public SpreadFireWeapon(Hero hero)
            : base(hero)
        {
            bulletsUp = new Queue<XNACS1Circle>();
            bulletsDown = new Queue<XNACS1Circle>();

            info = new XNACS1Rectangle(new Vector2(GameWorld.leftEdge + 12, GameWorld.topEdge - 4), 4, 4);
            info.Color = Color.Blue;
        }

        ~SpreadFireWeapon()
        {
            info.RemoveFromAutoDrawSet();

            foreach (XNACS1Circle b in bulletsDown)
            {
                b.RemoveFromAutoDrawSet();
            }

            foreach (XNACS1Circle b in bulletsUp)
            {
                b.RemoveFromAutoDrawSet();
            }
        }

        public override void update()
        {
            foreach (XNACS1Circle b in bulletsUp)
            {
                b.CenterX += bulletSpeed;
                b.CenterY += bulletSpeed / 2;
            }

            foreach (XNACS1Circle b in bulletsDown)
            {
                b.CenterX += bulletSpeed;
                b.CenterY -= bulletSpeed / 2;
            }


            while (bulletsUp.Count > 0 && (bulletsUp.First().CenterX - bulletsUp.First().Radius) > GameWorld.rightEdge)
            {
                bulletsUp.Dequeue().RemoveFromAutoDrawSet();
            }

            while (bulletsDown.Count > 0 && (bulletsDown.First().CenterX - bulletsDown.First().Radius) > GameWorld.rightEdge)
            {
                bulletsDown.Dequeue().RemoveFromAutoDrawSet();
            }

            foreach (XNACS1Circle b in bulletsUp)
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

            foreach (XNACS1Circle b in bulletsDown)
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

            base.update();
        }

        public override void fire()
        {
            base.fire();

            XNACS1Circle bullet = new XNACS1Circle(hero.getPosition().Center, bulletSize);
            bullet.Color = hero.getColor();
            bulletsDown.Enqueue(bullet);

            bullet = new XNACS1Circle(hero.getPosition().Center, bulletSize);
            bullet.Color = hero.getColor();
            bulletsUp.Enqueue(bullet);
        }

        public override void draw()
        {
            info.Label = "spread";
            info.TopOfAutoDrawSet();

            foreach (XNACS1Circle b in bulletsDown)
            {
                b.TopOfAutoDrawSet();
            }

            foreach (XNACS1Circle b in bulletsUp)
            {
                b.TopOfAutoDrawSet();
            }

            base.draw();
        }

    }
}
