using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;


namespace Dyehard
{
    class PowerUp
    {
        protected Hero hero;
        protected XNACS1Circle circle;

        public PowerUp(Hero hero, float minX, float maxX, Color color)
        {
            this.hero = hero;

            float padding = hero.getBox().Width * 2;

            float randomX = XNACS1Base.RandomFloat(minX + padding, maxX - padding);
            float randomY = XNACS1Base.RandomFloat(Game.bottomEdge() + padding, Game.topEdge() - padding);
            this.circle = new XNACS1Circle(new Vector2(randomX, randomY), 2.5f);
            this.circle.Color = color;
            this.circle.Label = "+";
        }

        ~PowerUp()
        {
            circle.RemoveFromAutoDrawSet();
        }

        public void move()
        {
            circle.CenterX -= Environment.Speed;
            circle.TopOfAutoDrawSet();
        }

        public virtual void interact()
        {
            if (circle.Collided(hero.getBox()) && circle.Visible)
            {
                hero.setColor(circle.Color);
                circle.Visible = false;
            }
        }

        public virtual void update()
        {
            throw new NotImplementedException();
        }

        public virtual bool expired()
        {
            throw new NotImplementedException();
        }
    }
}
