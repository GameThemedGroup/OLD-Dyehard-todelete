using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class PowerUpTracker
    {
        private Hero hero;
        private XNACS1Rectangle counter;
        private BorderBox border;

        public PowerUpTracker(Hero hero)
        {
            this.hero = hero;
            float height = 2.5f;
            float width = 5.5f;

            Vector2 position = new Vector2((Game.rightEdge() / 2) - width, Game.topEdge() - height);
            
            this.counter = new XNACS1Rectangle(position, width, height);
            this.counter.Color = new Color(Color.Gray, 25);
            this.counter.LabelColor = Color.White;

            this.border = new BorderBox(position, width, height, .2f, Color.Red);
        }

        ~PowerUpTracker()
        {
            counter.RemoveFromAutoDrawSet();
        }

        public void update()
        {
            // update textbox
            counter.Label = "" + hero.getPowerUpCount();
        }

        public void draw()
        {
            counter.TopOfAutoDrawSet();
            border.draw();
        }


    }
}
