
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
        private int reloadAmount = 10;
        private int ammo;
        private XNACS1Rectangle ammoTracker;

        public SpreadFireWeapon(Hero hero)
            : base(hero)
        {
            ammo = 10;
            ammoTracker = new XNACS1Rectangle(new Vector2(GameWorld.leftEdge + 12, GameWorld.topEdge - 4), 4, 4);
            ammoTracker.Color = Color.Blue;
        }

        ~SpreadFireWeapon()
        {
            ammoTracker.RemoveFromAutoDrawSet();
        }

        public override void recharge()
        {
            ammo = reloadAmount;
        }

        public override void update()
        {

            base.update();
        }

        public override void fire()
        {
            if (ammo > 0)
            {
                base.fire();
                ammo--;
            }
        }

        public override void draw()
        {
            ammoTracker.Label = "spread";
            ammoTracker.TopOfAutoDrawSet();
            base.draw();
        }

    }
}
