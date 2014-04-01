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
        private float distance;
        private XNACS1Rectangle textbox;

        public DistanceTracker(Hero hero)
        {
            this.hero = hero;
            this.startPoint = hero.getBox().Center;

            float height = 0.5f;
            float width = 1f;
            Vector2 position = new Vector2(XNACS1Base.World.WorldMax.X - (width / 2f), XNACS1Base.World.WorldMax.Y - (height / 2f));
            this.textbox = new XNACS1Rectangle(position, width, height);
            this.textbox.Color = Color.Transparent;
            this.accumulatedDistance = 0.0f;
        }

        public void update()
        {
            // update distance
            accumulatedDistance -= Game.Speed;
            float heroOffset = hero.getBox().CenterX - startPoint.X;
            distance = accumulatedDistance + heroOffset;

            // update textbox
            textbox.TopOfAutoDrawSet();
            textbox.Label = String.Format("{0:F1}", distance);
        }


    }
}
