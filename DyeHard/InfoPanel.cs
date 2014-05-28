using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;


namespace Dyehard
{
    class InfoPanel : GameObject
    {
        private XNACS1Rectangle background;
        private ScoreTracker scoreTracker;
        public static List<PowerUpMeter> meters;

        public InfoPanel(Hero hero)
        {
            float centerY = GameWorld.topEdge + (GameWorld.panelSize / 2);
            float centerX = GameWorld.rightEdge / 2;
            Vector2 center = new Vector2(centerX, centerY);
            this.background = new XNACS1Rectangle(center, GameWorld.rightEdge, GameWorld.panelSize);
            this.background.Color = new Color(Color.Black, 175);

            this.scoreTracker = new ScoreTracker(hero);

            meters = new List<PowerUpMeter>();

            meters.Add(SpeedUp.meter);
            meters.Add(Ghost.meter);
            meters.Add(Invincibility.meter);
            meters.Add(LowGrav.meter);

            foreach (PowerUpMeter meter in meters)
            {
                meter.reset(0f);
            }
        }

        public override void remove()
        {
            background.RemoveFromAutoDrawSet();
            scoreTracker.remove();
        }

        public override void draw()
        {
            background.TopOfAutoDrawSet();
            scoreTracker.draw();

            foreach (PowerUpMeter p in meters)
            {
                p.draw();
            }
        }

        public override void update()
        {
            scoreTracker.update();

            foreach (PowerUpMeter p in meters)
            {
                p.update();
            }
        }
    }
}
