using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Background
    {
        private XNACS1Rectangle world;

        private Queue<XNACS1Circle> foreground;
        private Queue<XNACS1Circle> background;

        private Timer fgTimer;
        private Timer bgTimer;


        public Background()
        {
            float width = XNACS1Base.World.WorldMax.X;
            float height = XNACS1Base.World.WorldMax.Y;
            Vector2 center = new Vector2(width/2, height/2);
            this.world = new XNACS1Rectangle(center, width, height);

            this.world.Color = Color.Black;

            this.foreground = new Queue<XNACS1Circle>();
            this.fgTimer = new Timer(0.2f);

            this.background = new Queue<XNACS1Circle>();
            this.bgTimer = new Timer(0.1f);


            for (float i = 0; i < Game.rightEdge(); i += 2.4f)
            {
                Vector2 position = new Vector2(i, XNACS1Base.RandomFloat(Game.topEdge()));
                foreground.Enqueue(new XNACS1Circle(position, 0.08f));
                foreground.Last().Color = Color.White;
                foreground.Last().VelocityX = -0.3f;
            }

            for (float i = 0; i < Game.rightEdge(); i += 1.2f)
            {
                Vector2 position = new Vector2(i, XNACS1Base.RandomFloat(Game.topEdge()));
                background.Enqueue(new XNACS1Circle(position, 0.04f));
                background.Last().Color = Color.White;
                background.Last().VelocityX = -0.15f;
            }
        }
        ~Background()
        {
            foreach (XNACS1Circle star in foreground)
            {
                star.RemoveFromAutoDrawSet();
            }
            foreach (XNACS1Circle star in background)
            {
                star.RemoveFromAutoDrawSet();
            }
        }

        public void update() {
            world.TopOfAutoDrawSet();

            fgTimer.update();
            bgTimer.update();

            if (fgTimer.isDone())
            {
                fgTimer.reset();
                Vector2 position = new Vector2(Game.rightEdge(), XNACS1Base.RandomFloat(Game.topEdge()));
                foreground.Enqueue(new XNACS1Circle(position, 0.08f));
                foreground.Last().Color = Color.White;
                foreground.Last().VelocityX = -0.1f;
            }

            if (bgTimer.isDone())
            {
                bgTimer.reset();
                Vector2 position = new Vector2(Game.rightEdge(), XNACS1Base.RandomFloat(Game.topEdge()));
                background.Enqueue(new XNACS1Circle(position, 0.04f));
                background.Last().Color = Color.White;
                background.Last().VelocityX = -0.05f;
            }


            // control when stars are moved (only on update)
            foreach (XNACS1Circle star in foreground)
            {
                star.CenterX += star.VelocityX ;
                star.TopOfAutoDrawSet();
            }

            foreach (XNACS1Circle star in background)
            {
                star.CenterX += star.VelocityX;
                star.TopOfAutoDrawSet();
            }

            // clean up out of screen stars
            if (foreground.Count > 0 && foreground.First().CenterX + foreground.First().Radius < Game.leftEdge())
            {
               foreground.Dequeue().RemoveFromAutoDrawSet();
            }
            if (background.Count > 0 && background.First().CenterX + background.First().Radius < Game.leftEdge())
            {
                background.Dequeue().RemoveFromAutoDrawSet();
            }
        }
    }
}
