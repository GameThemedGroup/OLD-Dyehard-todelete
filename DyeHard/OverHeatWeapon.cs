
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class OverHeatWeapon : Weapon
    {
        private float overHeatRange  = 10.0f;
        private float coolDownSpeed = 0.05f;
        private bool overheatOrNot;
        private float currentHeat;
        private XNACS1Rectangle tempTracker;


        public OverHeatWeapon(Hero hero)
            : base(hero)
        {
            this.hero = hero;
            bullets = new Queue<XNACS1Circle>();
            explosions = new List<Explosion>();
            overheatOrNot = false;
            currentHeat = 0;
            tempTracker = new XNACS1Rectangle(new Vector2(25, 55), 4, 4);
        }

        ~OverHeatWeapon()
        {
            foreach (XNACS1Circle b in bullets)
            {
                b.Visible = false;
                b.RemoveFromAutoDrawSet();
            }

        }

        public override void update()
        {
            if (KeyboardDevice.isKeyTapped(Microsoft.Xna.Framework.Input.Keys.F)  && 
                overheatOrNot == false)
            {
                fire();
                currentHeat = currentHeat + 1.0f;
            }

            if (currentHeat > overHeatRange)
            {
                overheatOrNot = true;
                tempTracker.Color = Color.Red;
            }

            if (currentHeat >= 0)
            {
                currentHeat = currentHeat - coolDownSpeed;
            }

            if (currentHeat <= 0)
            {
                tempTracker.Color = Color.Green;
                overheatOrNot = false;
            }

            tempTracker.Label = currentHeat.ToString("0.0");
            tempTracker.TopOfAutoDrawSet();

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

    }
}
