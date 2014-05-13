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
        protected XNACS1Rectangle box;

        public PowerUp(Hero hero, float minX, float maxX, Color color)
        {
            this.hero = hero;

            float padding = hero.getPosition().Width * 2;

            float randomX = XNACS1Base.RandomFloat(minX + padding, maxX - padding);
            float randomY = XNACS1Base.RandomFloat(Game.bottomEdge() + padding, Game.topEdge() - padding);
            this.box = new XNACS1Rectangle(new Vector2(randomX, randomY), 3.46f, 4f);
            this.box.Color = color;

            this.box.Texture = getTexture(color);
        }

        ~PowerUp()
        {
            box.RemoveFromAutoDrawSet();
        }

        public void move()
        {
            box.CenterX -= GameWorld.Speed;
        }

        public virtual void interact()
        {
            if (box.Collided(hero.getPosition()) && box.Visible)
            {
                hero.collect(this);
                hero.setColor(box.Color);
                box.Visible = false;
            }
        }

        public void draw()
        {
            box.TopOfAutoDrawSet();
        }

        private static String getTexture(Color color)
        {
            if (color == GameWorld.Green) return "Dye_Green";
            if (color == GameWorld.Blue) return "Dye_Blue";
            if (color == GameWorld.Yellow) return "Dye_Yellow";
            if (color == GameWorld.Teal) return "Dye_Teal";
            if (color == GameWorld.Pink) return "Dye_Pink";
            if (color == GameWorld.Red) return "Dye_Red";
            return "";
        }
    }
}
