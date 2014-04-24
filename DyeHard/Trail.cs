using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

// Shows up as a trail behind the specified character

namespace Dyehard
{
    class Trail
    {
        private Character character;
        private Queue<Footprint> trail;
        private Queue<Footprint> pool;
        private Timer timer;

        public Trail(Character character)
        {
            this.character = character;
            this.trail = new Queue<Footprint>();
            this.pool = new Queue<Footprint>();
            this.timer = new Timer(0.15f);
        }

        public void draw()
        {
            if (trail.Count > 0 && trail.First().faded())
            {
                pool.Enqueue(trail.Dequeue());
                pool.Last().remove();
            }

            foreach (Footprint t in trail)
            {
                t.draw();
            }
        }

        public void move()
        {
            foreach (Footprint t in trail)
            {
                t.move();
            }
        }

        public void interact()
        {
            timer.update();
            if (timer.isDone())
            {
                timer.reset();
                if (pool.Count > 0)
                {
                    trail.Enqueue(pool.Dequeue());
                    trail.Last().init(character);
                }
                else
                {
                    trail.Enqueue(new Footprint(character));
                }
            }
        }

        
    }

    class Footprint
    {
        private XNACS1Rectangle box;
        private Color color;

        public Footprint(Character character)
        {
            init(character);
        }

        public void init(Character character)
        {
            this.color = character.getColor();
            XNACS1Rectangle charBox = character.getPosition();
            this.box = new XNACS1Rectangle(charBox.Center, charBox.Width, charBox.Height);
            this.box.Color = this.color;
            box.AddToAutoDrawSet();
        }

        ~Footprint()
        {
            box.RemoveFromAutoDrawSet();
        }

        public void move()
        {
            box.CenterX -= Environment.Speed;
        }

        public void draw()
        {
            // fade the footprint
            color.A = (byte)Math.Max(0, color.A - 6);
            color.R = (byte)Math.Max(0, color.R - 3);
            color.B = (byte)Math.Max(0, color.B - 3);
            color.G = (byte)Math.Max(0, color.G - 3);
            box.Color = color;

            // shrink the footprint
            box.Height *= .98f;
            box.Width *= .98f;

            box.TopOfAutoDrawSet();
        }

        // return true if the footprint is sufficiently 'faded'
        public bool faded()
        {
            return box.Height < 0.3f;
        }

        public void remove()
        {
            box.RemoveFromAutoDrawSet();
        }
    }
}
