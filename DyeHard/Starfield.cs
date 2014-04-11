using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

// A starfield is a collection of stars moving to the left at a given speed.
// Layers of starfields can be used to create a parallax effect.

namespace Dyehard
{
    class Starfield
    {
        private Queue<XNACS1Circle> stars;
        private Timer timer;
        private float speed;
        private float size;
        private float interval;

        public Starfield(float size, float speed, float interval)
        {
            this.size = size;
            this.speed = speed;
            this.interval = interval;
            this.timer = new Timer(interval);
            this.stars = new Queue<XNACS1Circle>();

            float delta = speed * interval * XNACS1Base.World.TicksInASecond;

            // pre-fill game
            for (float i = 0; i < Game.rightEdge(); i += delta)
            {
                stars.Enqueue(starAt(i));
            }
        }

        ~Starfield()
        {
            foreach (XNACS1Circle star in stars)
            {
                star.RemoveFromAutoDrawSet();
            }
        }

        public void update()
        {

            timer.update();

            if (timer.isDone())
            {
                timer.reset();
                stars.Enqueue(starAt(Game.rightEdge()));
            }


            // control star movement, and redraw
            foreach (XNACS1Circle star in stars)
            {
                star.CenterX -= speed;
                star.TopOfAutoDrawSet();
            }


            // clean up stars that are out of view
            if (stars.First().CenterX + stars.First().Radius <= Game.leftEdge())
            {
                stars.Dequeue().RemoveFromAutoDrawSet();
            }
        }

        private float randomPosition()
        {
            return XNACS1Base.RandomFloat(Game.topEdge());
        }

        private Color randomStarColor()
        {
            return new Color(XNACS1Base.RandomInt(200, 256), XNACS1Base.RandomInt(200, 256), XNACS1Base.RandomInt(200, 256));
        }

        private XNACS1Circle starAt(float xPos)
        {
            Vector2 position = new Vector2(xPos, randomPosition());
            XNACS1Circle star = new XNACS1Circle(position, size);
            star.Color = randomStarColor();
            return star;
        }

    }
}
