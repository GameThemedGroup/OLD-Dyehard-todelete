using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class ZeroGravityPowerUp : PowerUp
    {
        private Timer timer;

        public ZeroGravityPowerUp(Hero hero, float minX, float maxX, Color color)
            : base(hero, minX, maxX, color)
        {
            this.circle.Label += "no\ngrav";
            this.timer = new Timer(4);
        }

        public override void interact()
        {
            if (circle.Collided(hero.getBox()) && circle.Visible)
            {
                hero.collect(this);
                hero.setColor(circle.Color);
                ((MassObject)hero.getBox()).disableGravity();
                circle.Visible = false;
            }
        }

        public override void update()
        {
            timer.update();

            if (timer.isDone())
            {
                ((MassObject)hero.getBox()).enableGravity();
            }
        }

        public override bool expired()
        {
            return timer.isDone();
        }
    }
}
