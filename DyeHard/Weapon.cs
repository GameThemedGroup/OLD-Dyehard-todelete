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
        private static float bulletSpeed = 1f;
        private static float bulletSize = 1.5f;
        private Hero hero;
        private Queue<XNACS1Circle> bullets;

        public Weapon(Hero hero)
        {
            this.hero = hero;
            bullets = new Queue<XNACS1Circle>();
        }

        public void update()
        {
            foreach (XNACS1Circle b in bullets) {
                b.CenterX += bulletSpeed;
            }

            while (bullets.Count > 0 && (bullets.First().CenterX - bullets.First().Radius) > Game.rightEdge())
            {
                Console.WriteLine("Removing bullet");
                bullets.Dequeue();
            }

            if (KeyboardDevice.isKeyTapped(Microsoft.Xna.Framework.Input.Keys.F))
            {
                fire();
            }
        }

        // fire the weapon
        private void fire()
        {
            XNACS1Circle bullet = new XNACS1Circle(hero.getBox().Center, bulletSize);
            bullet.Color = new Color(hero.getColor(), 210);
            bullet.Label = "pew";
            bullets.Enqueue(bullet);
        }
    }
}
