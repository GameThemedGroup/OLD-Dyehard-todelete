using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace DyeHard
{
    class DistanceTracker
    {
        private Hero hero;
        private Vector2 startPoint;
        private float accumulatedDistance;
        private XNACS1Rectangle textbox;

        public DistanceTracker(Hero hero)
        {
            this.hero = hero;
            this.startPoint = hero.getBox().Center;

            float height = 2.5f;
            float width = 6f;
            Vector2 position = new Vector2(Game.leftEdge() + (width / 2f), Game.topEdge() - (height / 2f));
            this.textbox = new XNACS1Rectangle(position, width, height);
            this.textbox.Color = Color.White;
            this.accumulatedDistance = 0.0f;
        }

        public void update()
        {
            // update distance
            accumulatedDistance += Background.Speed;
            float heroOffset = hero.getBox().CenterX - startPoint.X;

            // update textbox
            textbox.TopOfAutoDrawSet();
            textbox.Label = String.Format("{0:F1}", (accumulatedDistance + heroOffset) / hero.getBox().Width);
        }


    }
}
