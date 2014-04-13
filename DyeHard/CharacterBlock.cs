﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class CharacterBlock : XNACS1Rectangle
    {
        private Vector2 gravity;
        private float drag;
        private float horizonalSpeedLimit;
        
        public CharacterBlock(Vector2 position, float width, float height)
            : base(position, width, height)
        {
            this.gravity = new Vector2(0, -.03f);
            this.drag = 0.94f;
            this.horizonalSpeedLimit = 1f;

            // set object into motion;
            this.Velocity = new Vector2();
        }

        public void push(Vector2 direction)
        {
            // scale direction
            direction = direction / 10f;
            
            // update velocity
            Velocity = (Velocity + direction + gravity) * drag;


        }

        public void update()
        {
            // clamp horizontal speed
            if (VelocityX < 0)
            {
                VelocityX = Math.Max(VelocityX, -horizonalSpeedLimit);
            }
            else
            {
                VelocityX = Math.Min(VelocityX, horizonalSpeedLimit);
            }

            // clamp velocity
            if (LowerLeft.Y <= Game.bottomEdge() && VelocityY < 0)
            {
                VelocityY = 0f;
            }
            if ((LowerLeft.Y + Height) >= Game.topEdge() && VelocityY > 0)
            {
                VelocityY = 0f;
            }
            if (LowerLeft.X <= Game.leftEdge() && VelocityX < 0)
            {
                VelocityX = 0f;
            }
            if ((LowerLeft.X + Width) >= Game.rightEdge() && VelocityX > 0)
            {
                VelocityX = 0f;
            }

            // intepret velocity into motion
            Center += Velocity;
        }

        public override void TopOfAutoDrawSet()
        {
            base.TopOfAutoDrawSet();
        }

        public void disableGravity()
        {
            gravity = new Vector2();
        }

        public void enableGravity()
        {
            gravity = new Vector2(0, -.05f);
        }
    }
}
