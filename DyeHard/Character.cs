using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Character
    {
        private const float horizontalSpeedLimit = 1;
        private static float drag = 0.93f;
        private bool alive;
        protected XNACS1Rectangle currentPosition;
        private XNACS1Rectangle nextPosition;
        
        public Character(Vector2 position, float width, float height)
        {
            this.currentPosition = new XNACS1Rectangle(position, width, height);
            this.currentPosition.Color = Game.randomColor();
            this.nextPosition = new XNACS1Rectangle(position, width, height);
            this.nextPosition.Visible = false;

            // set object into motion;
            this.currentPosition.Velocity = new Vector2(0, 0);
            this.alive = true;
        }

        ~Character()
        {
            currentPosition.RemoveFromAutoDrawSet();
            nextPosition.RemoveFromAutoDrawSet();
        }

        public XNACS1Rectangle getPosition()
        {
            return currentPosition;
        }

        public XNACS1Rectangle getNextPosition()
        {
            nextPosition.Center = currentPosition.Center + currentPosition.Velocity;
            return nextPosition;
        }

        public void push(Vector2 direction)
        {
            // scale direction
            direction = direction / 7f;
            
            // update velocity
            currentPosition.Velocity = (currentPosition.Velocity + direction) * drag;
        }

        public virtual void update()
        {
            // clamp horizontal speed
            if (currentPosition.VelocityX < 0)
            {
                currentPosition.VelocityX = Math.Max(currentPosition.VelocityX, -1 * horizontalSpeedLimit);
            }
            else
            {
                currentPosition.VelocityX = Math.Min(currentPosition.VelocityX, horizontalSpeedLimit);
            }

            // clamp velocity
            if (currentPosition.LowerLeft.Y <= Game.bottomEdge() && currentPosition.VelocityY < 0)
            {
                currentPosition.VelocityY = 0f;
            }
            if ((currentPosition.LowerLeft.Y + currentPosition.Height) >= Game.topEdge() && currentPosition.VelocityY > 0)
            {
                currentPosition.VelocityY = 0f;
            }
            if (currentPosition.LowerLeft.X <= Game.leftEdge() && currentPosition.VelocityX < 0)
            {
                currentPosition.VelocityX = 0f;
            }
            if ((currentPosition.LowerLeft.X + currentPosition.Width) >= Game.rightEdge() && currentPosition.VelocityX > 0)
            {
                currentPosition.VelocityX = 0f;
            }

            // convert velocity to actual movement
            currentPosition.Center += currentPosition.Velocity;
        }

        public virtual void draw()
        {
            currentPosition.TopOfAutoDrawSet();
        }

        public void setColor(Color color)
        {
            currentPosition.Color = color;
        }

        public Color getColor()
        {
            return currentPosition.Color;
        }

        public void setLabel(string label)
        {
            currentPosition.Label = label;
        }

        public bool isAlive()
        {
            return alive;
        }

        public void kill()
        {
            alive = false;
        }
    }
}
