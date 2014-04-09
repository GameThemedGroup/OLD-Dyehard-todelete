using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class DistanceTracker
    {
        private Hero hero;
        private Vector2 startPoint;
        private float accumulatedDistance;
        private float factor;
        private XNACS1Rectangle label;  // separate label box to eliminate jumpy formatting
        private XNACS1Rectangle distance;

        public DistanceTracker(Hero hero)
        {
            this.hero = hero;
            this.startPoint = hero.getBox().Center;
            this.factor = hero.getBox().Width;
            float height = 1.5f;
            float width = 7f;
            Vector2 position = new Vector2(Game.leftEdge() + (width / 2f), Game.topEdge() - (height / 2f));
            this.label = new XNACS1Rectangle(position, width, height);
            this.label.Color = new Color(Color.Gray, 50);
            this.label.Label = "Distance";
            this.distance = new XNACS1Rectangle(position - new Vector2(0, 1.5f), width, height);
            this.distance.Color = this.label.Color;
            this.accumulatedDistance = 0.0f;
        }

        public void update()
        {
            // update distance
            accumulatedDistance += Background.Speed;
            float heroOffset = hero.getBox().CenterX - startPoint.X;

            // update textbox
            distance.Label = String.Format("{0:F1}", (accumulatedDistance + heroOffset) / factor);
            label.TopOfAutoDrawSet();
            distance.TopOfAutoDrawSet();

        }


    }
}
