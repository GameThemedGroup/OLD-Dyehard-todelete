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

        public static List<PowerUpMeter> powerups;
        private static int powerupMeterCount = 4;

        public InfoPanel(Hero hero)
        {
            float centerY = GameWorld.topEdge + (GameWorld.panelSize / 2);
            float centerX = GameWorld.rightEdge / 2;
            Vector2 center = new Vector2(centerX, centerY);
            this.background = new XNACS1Rectangle(center, GameWorld.rightEdge, GameWorld.panelSize);
            this.background.Color = new Color(Color.Black, 175);

            this.distanceTracker = new DistanceTracker(hero);

            powerups = new List<PowerUpMeter>();

            powerups.Add(SpeedUp.meter);
            powerups.Add(Ghost.meter);
            powerups.Add(Invincibility.meter);
            powerups.Add(LowGrav.meter);
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
}
