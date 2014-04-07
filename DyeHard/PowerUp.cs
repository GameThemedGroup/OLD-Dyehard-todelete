using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;


namespace DyeHard
{
    class PowerUp
    {
        private const float padding = 3f;
        private Hero hero;
        private XNACS1Circle box;
        public PowerUp(Hero hero, float minX, float maxX, Color color)
        {
            this.hero = hero;

            float randomX = XNACS1Base.RandomFloat(minX + padding, maxX - padding);
            float randomY = XNACS1Base.RandomFloat(Game.bottomEdge() + padding, Game.topEdge() - padding);
            this.box = new XNACS1Circle(new Vector2(randomX, randomY), 2.5f);
            this.box.Color = color;
            this.box.Label = "powerup";
        }

        public void move()
        {
            box.CenterX -= Background.Speed;
        }

        public void interact()
        {
            XNACS1Rectangle heroBox = hero.getBox();
            if (box.Collided(heroBox))
            {
                hero.setColor(box.Color);
                box.Visible = false;
            }
        }

    }
}
