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

        public DistanceTracker(Hero hero)
        {
            this.hero = hero;
            this.startPoint = hero.getPosition().Center;
            this.factor = hero.getPosition().Width;
            this.accumulatedDistance = 0.0f;

            float height = GameWorld.panelSize - 1;
            float width = 20f;
            Vector2 position = new Vector2((GameWorld.rightEdge / 2) + width, GameWorld.topEdge + (GameWorld.panelSize / 2));            
            this.distance = new XNACS1Rectangle(position, width, height);
            this.distance.Color = Color.Transparent;
            this.distance.LabelColor = Color.White;
        }

        ~DistanceTracker()
        {
            distance.Visible = false;
            distance.RemoveFromAutoDrawSet();
        }

        public void update()
        {
            // update distance
            accumulatedDistance += GameWorld.Speed;
            float heroOffset = hero.getPosition().CenterX - startPoint.X;

            // update textbox
            distance.Label = ((accumulatedDistance + heroOffset) / factor).ToString("00000000.0");
        }

        public void draw()
        {
            distance.TopOfAutoDrawSet();
        }


    }
}
