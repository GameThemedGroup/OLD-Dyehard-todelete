using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;


namespace Dyehard
{
    class InfoPanel
    {
        private XNACS1Rectangle background;

        private DistanceTracker distanceTracker;

        private List<PowerUpMeter> powerups;
        private static int powerupMeterCount = 4;

        public InfoPanel(Hero hero)
        {
            float centerY = GameWorld.topEdge + (GameWorld.panelSize / 2);
            float centerX = GameWorld.rightEdge / 2;
            Vector2 center = new Vector2(centerX, centerY);
            this.background = new XNACS1Rectangle(center, GameWorld.rightEdge, GameWorld.panelSize);
            this.background.Color = new Color(Color.Black, 175);


            this.distanceTracker = new DistanceTracker(hero);


            this.powerups = new List<PowerUpMeter>();
            for (int i = 0; i < powerupMeterCount; i++)
            {
                powerups.Add(new PowerUpMeter(i));
            }
        }

        public void draw()
        {
            background.TopOfAutoDrawSet();
            distanceTracker.draw();

            foreach (PowerUpMeter p in powerups)
            {
                p.draw();
            }
        }

        public void update()
        {
            distanceTracker.update();

            foreach (PowerUpMeter p in powerups)
            {
                p.update();
            }
        }
    }


    class PowerUpMeter
    {
        private XNACS1Rectangle box;

        public PowerUpMeter(int sequenceNumber)
        {
            float padding = 1f;
            float height = GameWorld.panelSize;
            float width = height;

            float offset = GameWorld.leftEdge + (sequenceNumber + 1) * (padding) + sequenceNumber * width + width / 2;

            this.box = new XNACS1Rectangle(new Vector2(offset, GameWorld.topEdge + (GameWorld.panelSize / 2)), width, height);
            this.box.Texture = "PowerUp_Box1";
        }

        public void update()
        {

        }

        public void draw()
        {
            box.TopOfAutoDrawSet();
        }
    }

}
