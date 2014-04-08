using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class GravityPowerUp : PowerUp
    {
        public GravityPowerUp(Hero hero, float minX, float maxX, Color color)
            : base(hero, minX, maxX, color)
        {
            this.circle.Label += "no\ngrav";
        }

        public override void interact()
        {
            if (circle.Collided(hero.getBox()))
            {
                hero.setColor(circle.Color);
                ((MassObject)hero.getBox()).disableGravity();
                circle.Visible = false;
            }
        }

    }
}
