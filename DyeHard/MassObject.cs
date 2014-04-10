﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using XNACS1Lib;
using Microsoft.Xna.Framework;

namespace Dyehard
{
    class MassObject : XNACS1Rectangle
    {
        private Vector2 gravity;
        private float drag;
        private float horizonalSpeedLimit;
        private XNACS1Rectangle border;
        private XNACS1Circle boost;
        
        public MassObject(Vector2 position, float width, float height)
            : base(position, width, height)
        {
            this.gravity = new Vector2(0, -.05f);
            this.drag = 0.94f;
            this.horizonalSpeedLimit = 1f;

            // set object into motion;
            this.Velocity = new Vector2();
            this.border = new XNACS1Rectangle(this.Center, this.Width * 1.1f, this.Height * 1.1f);
            this.border.Color = Color.LightGray;

            this.boost = new XNACS1Circle(new Vector2(), width/2);
            this.boost.Color = new Color(Color.Orange, 200);
            this.boost.Visible = false;
        }

        public void push(Vector2 direction)
        {

            // add jetpack factor
            if (direction.Y > 0)
            {
                direction.Y *= 2f;
                boost.Visible = true;
            }
            else
            {
                boost.Visible = false;
            }

            // scale direction
            direction = direction / 8f;
            
            // update velocity
            Velocity = (Velocity + direction + gravity) * drag;

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
            if( (LowerLeft.X + Width) >= Game.rightEdge() && VelocityX > 0)
            {
                VelocityX = 0f;
            }

            Center += Velocity; // only move center when being pushed
            border.Center = Center;
            boost.Center = Center - new Vector2(0, Height / 2);
        }

        public override void TopOfAutoDrawSet()
        {
            boost.TopOfAutoDrawSet();
            border.TopOfAutoDrawSet();
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
