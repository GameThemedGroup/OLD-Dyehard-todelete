﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class Character
    {
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
            position.Visible = false;
            nextPosition.Visible = false;
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
            position.TextureTintColor = color;
        }

        public Color getColor()
        {
            return position.Color;
        }

        public void setTexture(string resourceName)
        {
            position.Texture = resourceName;
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

            // convert velocity to actual movement
            position.Center += position.Velocity;
        }

        public void addCollision(XNACS1Rectangle box)
        {
            pendingCollisions.Add(box);
        }

        public void interpretCollisions()
        {
            foreach (XNACS1Rectangle obstacle in pendingCollisions)
            {
                collide(getNextPosition(), obstacle);
            }

            // all collisions handled, remove all boxes
            pendingCollisions.Clear();
        }

        private void collide(XNACS1Rectangle pending, XNACS1Rectangle obstacle)
        {
            if (pending.Collided(obstacle))
            {
                if (position.CenterY < obstacle.CenterY && position.CenterX < obstacle.CenterX)
                {
                    // character collided into lower left corner of box
                    // get the smaller of the two overlaps from the next position
                    float Xoverlap = Math.Max(0, pending.MaxBound.X - obstacle.MinBound.X);
                    float Yoverlap = Math.Max(0, pending.MaxBound.Y - obstacle.MinBound.Y);

                    if (Yoverlap < Xoverlap)
                    {
                        // adjust to the Y side (since it is less)
                        position.CenterY += (obstacle.MinBound.Y - position.MaxBound.Y);
                        position.VelocityY = Math.Min(0, position.VelocityY);
                    }
                    else
                    {
                        // adjust to the X side (since it is less)
                        position.CenterX += (obstacle.MinBound.X - position.MaxBound.X);
                        position.VelocityX = Math.Min(0, position.VelocityX);
                    }
                }
                else if (position.CenterY < obstacle.CenterY && position.CenterX >= obstacle.CenterX)
                {
                    // character collided into lower right corner of box
                    // get the smaller of the two overlaps from the next position
                    float Xoverlap = Math.Max(0, obstacle.MaxBound.X - pending.MinBound.X);
                    float Yoverlap = Math.Max(0, pending.MaxBound.Y - obstacle.MinBound.Y);

                    if (Yoverlap < Xoverlap)
                    {
                        // adjust to the Y side (since it is less)
                        position.CenterY += (obstacle.MinBound.Y - position.MaxBound.Y);
                        position.VelocityY = Math.Min(0, position.VelocityY);
                    }
                    else
                    {
                        // adjust to the X side (since it is less)
                        position.CenterX -= (position.MinBound.X - obstacle.MaxBound.X);
                        position.VelocityX = Math.Max(0, position.VelocityX);
                    }
                }
                else if (position.CenterY >= obstacle.CenterY && position.CenterX < obstacle.CenterX)
                {
                    // character collided into upper left corner of box
                    // get the smaller of the two overlaps from the next position
                    float Xoverlap = Math.Max(0, pending.MaxBound.X - obstacle.MinBound.X);
                    float Yoverlap = Math.Max(0, obstacle.MaxBound.Y - pending.MinBound.Y);

                    if (Yoverlap < Xoverlap)
                    {
                        // adjust to the Y side (since it is less)
                        position.CenterY -= (position.MinBound.Y - obstacle.MaxBound.Y);
                        position.VelocityY = Math.Max(0, position.VelocityY);
                    }
                    else
                    {
                        // adjust to the X side (since it is less)
                        position.CenterX += (obstacle.MinBound.X - position.MaxBound.X);
                        position.VelocityX = Math.Min(0, position.VelocityX);
                    }
                }
                else
                {
                    // character collided into upper right corner
                    float Xoverlap = Math.Max(0, obstacle.MaxBound.X - pending.MinBound.X);
                    float Yoverlap = Math.Max(0, obstacle.MaxBound.Y - pending.MinBound.Y);

                    if (Yoverlap < Xoverlap)
                    {
                        // adjust to the Y side (since it is less)
                        position.CenterY -= (position.MinBound.Y - obstacle.MaxBound.Y);
                        position.VelocityY = Math.Max(0, position.VelocityY);
                    }
                    else
                    {
                        // adjust to the X side (since it is less)
                        position.CenterX -= (position.MinBound.X - obstacle.MaxBound.X);
                        position.VelocityX = Math.Max(0, position.VelocityX);
                    }
                }
            }
        }
    }
}
