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
            this.startPoint = hero.getPosition().Center;
            this.factor = hero.getPosition().Width;
            this.accumulatedDistance = 0.0f;

            float height = 2.5f;
            float width = 5.5f;
            Vector2 position = new Vector2((Game.rightEdge() / 2) + width, Game.topEdge() - height);            
            this.distance = new XNACS1Rectangle(position, width, height);
            this.distance.Color = new Color(Color.Gray, 25);
            this.distance.LabelColor = Color.White;
            this.border = new BorderBox(position, width, height, .2f, Color.Red);
        }

        ~DistanceTracker()
        {
            distance.RemoveFromAutoDrawSet();
        }

        public void update()
        {
            // update distance
            accumulatedDistance += Environment.Speed;
            float heroOffset = hero.getPosition().CenterX - startPoint.X;

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
