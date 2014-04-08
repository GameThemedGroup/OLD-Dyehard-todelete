using System;
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
        
        public MassObject(Vector2 position, float width, float height)
            : base(position, width, height)
        {
            this.gravity = new Vector2(0, -.05f);
            this.drag = 0.94f;
            this.horizonalSpeedLimit = 1f;

            // set object into motion;
            this.Velocity = new Vector2();
            this.ShouldTravel = true;
        }

        public void push(Vector2 direction)
        {
            // scale direction
            direction = direction / 8f;
            
            Velocity = (Velocity + direction + gravity) * drag;

            if (VelocityX < 0)
            {
                VelocityX = Math.Max(VelocityX, -horizonalSpeedLimit);
            }
            else
            {
                VelocityX = Math.Min(VelocityX, horizonalSpeedLimit);
            }

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
        }
    }
}
