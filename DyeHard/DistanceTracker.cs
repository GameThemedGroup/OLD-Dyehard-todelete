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
        private XNACS1Rectangle distance;
        private BorderBox border;

        public DistanceTracker(Hero hero)
        {
            this.hero = hero;
            this.startPoint = hero.getBox().Center;
            this.factor = hero.getBox().Width;
            float height = 2f;
            float width = 5f;

            Vector2 position = new Vector2(Game.rightEdge() / 2, Game.topEdge() - height);
            
            this.distance = new XNACS1Rectangle(position, width, height);
            this.distance.Color = Color.Transparent;
            this.distance.LabelColor = Color.White;

            this.accumulatedDistance = 0.0f;

            border = new BorderBox(position, width, height, .2f, Color.Red);
        }

        ~DistanceTracker()
        {
            distance.RemoveFromAutoDrawSet();
        }

        public void update()
        {
            // update distance
            accumulatedDistance += Environment.Speed;
            float heroOffset = hero.getBox().CenterX - startPoint.X;

            // update textbox
            distance.Label = String.Format("{0:F1}", (accumulatedDistance + heroOffset) / factor);
        }

        public void draw()
        {
            distance.TopOfAutoDrawSet();
            border.draw();
        }


    }
}
