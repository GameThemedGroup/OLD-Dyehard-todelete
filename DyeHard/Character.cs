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
        private bool alive;
        protected XNACS1Rectangle position;
        private XNACS1Rectangle nextPosition;
        private List<XNACS1Rectangle> pendingCollisions;
        
        public Character(Vector2 position, float width, float height)
        {
            this.position = new XNACS1Rectangle(position, width, height);
            this.position.Color = Game.randomColor();
            this.nextPosition = new XNACS1Rectangle(position, width, height);
            this.nextPosition.Visible = false;

            // set object into motion;
            this.position.Velocity = new Vector2(0, 0);
            this.alive = true;
            this.pendingCollisions = new List<XNACS1Rectangle>();
        }

        ~Character()
        {
            position.RemoveFromAutoDrawSet();
            nextPosition.RemoveFromAutoDrawSet();
        }

        public virtual void draw()
        {
            position.TopOfAutoDrawSet();
        }

        public void setColor(Color color)
        {
            position.Color = color;
        }

        public Color getColor()
        {
            return position.Color;
        }

        public void setLabel(string label)
        {
            position.Label = label;
        }

        public bool isAlive()
        {
            return alive;
        }

        public void kill()
        {
            alive = false;
        }

        public XNACS1Rectangle getPosition()
        {
            return position;
        }

        public XNACS1Rectangle getNextPosition()
        {
            nextPosition.Center = position.Center + position.Velocity;
            return nextPosition;
        }

        public virtual void update()
        {
            interpretCollisions();
            limitSpeed();

            // clamp velocity
            if (position.LowerLeft.Y <= Game.bottomEdge() && position.VelocityY < 0)
            {
                position.VelocityY = 0f;
            }
            if ((position.LowerLeft.Y + position.Height) >= Game.topEdge() && position.VelocityY > 0)
            {
                position.VelocityY = 0f;
            }
            if (position.LowerLeft.X <= Game.leftEdge() && position.VelocityX < 0)
            {
                position.VelocityX = 0f;
            }
            if ((position.LowerLeft.X + position.Width) >= Game.rightEdge() && position.VelocityX > 0)
            {
                position.VelocityX = 0f;
            }

            // convert velocity to actual movement
            position.Center += position.Velocity;
        }

        private void limitSpeed()
        {
            // clamp horizontal speed to the speed limit
            if (position.VelocityX < 0)
            {
                position.VelocityX = Math.Max(position.VelocityX, -1 * horizontalSpeedLimit);
            }
            else
            {
                position.VelocityX = Math.Min(position.VelocityX, horizontalSpeedLimit);
            }
        }

        public void interpretCollisions()
        {
            foreach (XNACS1Rectangle box in pendingCollisions)
            {
                XNACS1Rectangle next = getNextPosition();

                // make sure its still going to collide after other adjustments
                if (next.Collided(box))
                {
                    if (position.CenterY < box.CenterY && position.CenterX < box.CenterX)
                    {
                        // character collided into lower left corner of box
                        // get the smaller of the two overlaps from the next position
                        float Xoverlap = Math.Max(0, next.MaxBound.X - box.MinBound.X);
                        float Yoverlap = Math.Max(0, next.MaxBound.Y - box.MinBound.Y);

                        if (Yoverlap < Xoverlap)
                        {
                            // adjust to the Y side (since it is less)
                            position.CenterY += (box.MinBound.Y - position.MaxBound.Y);
                            position.VelocityY = 0;
                        }
                        else
                        {
                            // adjust to the X side (since it is less)
                            position.CenterX += (box.MinBound.X - position.MaxBound.X);
                            position.VelocityX = 0;
                        }
                    }
                    else if (position.CenterY < box.CenterY && position.CenterX >= box.CenterX)
                    {
                        // character collided into lower right corner of box
                        // get the smaller of the two overlaps from the next position
                        float Xoverlap = Math.Max(0, box.MaxBound.X - next.MinBound.X);
                        float Yoverlap = Math.Max(0, next.MaxBound.Y - box.MinBound.Y);

                        if (Yoverlap < Xoverlap)
                        {
                            // adjust to the Y side (since it is less)
                            position.CenterY += (box.MinBound.Y - position.MaxBound.Y);
                            position.VelocityY = 0;
                        }
                        else
                        {
                            // adjust to the X side (since it is less)
                            position.CenterX -= (position.MinBound.X - box.MaxBound.X);
                            position.VelocityX = 0;
                        }
                    }
                    else if (position.CenterY >= box.CenterY && position.CenterX < box.CenterX)
                    {
                        // character collided into upper left corner of box
                        // get the smaller of the two overlaps from the next position
                        float Xoverlap = Math.Max(0, next.MaxBound.X - box.MinBound.X);
                        float Yoverlap = Math.Max(0, box.MaxBound.Y - next.MinBound.Y);

                        if (Yoverlap < Xoverlap)
                        {
                            // adjust to the Y side (since it is less)
                            position.CenterY -= (position.MinBound.Y - box.MaxBound.Y);
                            position.VelocityY = 0;
                        }
                        else
                        {
                            // adjust to the X side (since it is less)
                            position.CenterX += (box.MinBound.X - position.MaxBound.X);
                            position.VelocityX = 0;
                        }
                    }
                    else {
                        // character collided into upper right corner
                        float Xoverlap = Math.Max(0, box.MaxBound.X - next.MinBound.X);
                        float Yoverlap = Math.Max(0, box.MaxBound.Y - next.MinBound.Y);

                        if (Yoverlap < Xoverlap)
                        {
                            // adjust to the Y side (since it is less)
                            position.CenterY -= (position.MinBound.Y - box.MaxBound.Y);
                            position.VelocityY = 0;
                        }
                        else
                        {
                            // adjust to the X side (since it is less)
                            position.CenterX -= (position.MinBound.X - box.MaxBound.X);
                            position.VelocityX = 0;
                        }
                    }
                    
                }
            }

            // all collisions handled
            pendingCollisions.Clear();
        }


        public void addCollision(XNACS1Rectangle box)
        {
            pendingCollisions.Add(box);
        }


        /*
         if (box.CenterY < position.CenterY)
                {
                    position.CenterY += (box.MaxBound.Y - position.MinBound.Y);
                    position.VelocityY = 0;
                }
                else
                {
                    position.CenterY += (box.MinBound.Y - position.MaxBound.Y);
                    position.VelocityY = 0;
                } 
         * 
         * 
         */
    }
}
